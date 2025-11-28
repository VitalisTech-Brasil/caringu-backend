package tech.vitalis.caringu.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.vitalis.caringu.config.CookieJwtUtil;
import tech.vitalis.caringu.dtos.Pessoa.security.PessoaLoginDTO;
import tech.vitalis.caringu.dtos.Pessoa.security.PessoaTokenDTO;
import tech.vitalis.caringu.dtos.auth.GoogleCodeRequestDTO;
import tech.vitalis.caringu.dtos.auth.GoogleLoginResponseDTO;
import tech.vitalis.caringu.service.AuthService;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AutenticacaoController {

    @Autowired
    private AuthService authService;

    @Autowired
    private CookieJwtUtil cookieJwtUtil;

    @PostMapping("/login")
    @Operation(
            summary = "Autentica o usuário e retorna o token JWT",
            description = "Este endpoint permite que o usuário faça login no sistema utilizando suas credenciais (e-mail e senha). "
                    + "Ao fornecer as credenciais corretas, o sistema retornará um token JWT que deve ser usado para acessar rotas protegidas.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Login bem-sucedido, token JWT gerado",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PessoaTokenDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Credenciais inválidas, não autorizado"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Requisição malformada, dados de login ausentes ou incorretos"
                    )
            }
    )
    public ResponseEntity<PessoaTokenDTO> login(@RequestBody PessoaLoginDTO pessoaLoginDto, HttpServletResponse response) {

        PessoaTokenDTO pessoaTokenDto = authService.loginComSenha(pessoaLoginDto);

        // Define o cookie JWT (mais seguro que localStorage)
        cookieJwtUtil.createJwtCookie(response, pessoaTokenDto.getToken());

        return ResponseEntity.status(200).body(pessoaTokenDto);
    }

    @PostMapping("/login/google")
    public ResponseEntity<GoogleLoginResponseDTO> loginGoogle(@RequestBody GoogleCodeRequestDTO request,
                                                              HttpServletResponse response) {
        GoogleLoginResponseDTO resultado = authService.loginComGoogle(request.codigo());

        // Se houve erro de token Google, retorna 401 com o código esperado
        if ("INVALID_GOOGLE_TOKEN".equals(resultado.erro())) {
            return ResponseEntity.status(401).body(resultado);
        }

        // Quando houver token de aplicação gerado, grava em cookie HttpOnly
        if (Boolean.TRUE.equals(resultado.sucesso()) && resultado.token() != null) {
            cookieJwtUtil.createJwtCookie(response, resultado.token());
        }

        return ResponseEntity.ok(resultado);
    }

    @PostMapping("/logout")
    @Operation(
            summary = "Logout do usuário",
            description = "Este endpoint remove o cookie JWT do usuário, efetivando o logout seguro.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Logout realizado com sucesso"
                    )
            }
    )
    public ResponseEntity<Map<String, String>> logout(HttpServletResponse response) {
        // Remove o cookie JWT
        cookieJwtUtil.removeJwtCookie(response);

        return ResponseEntity.ok(Map.of("message", "Logout realizado com sucesso"));
    }

}
