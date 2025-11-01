package tech.vitalis.caringu.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpServletResponse;
import tech.vitalis.caringu.config.GerenciadorTokenJwt;
import tech.vitalis.caringu.config.CookieJwtUtil;
import tech.vitalis.caringu.dtos.Pessoa.security.PessoaLoginDTO;
import tech.vitalis.caringu.dtos.Pessoa.security.PessoaTokenDTO;
import tech.vitalis.caringu.entity.Aluno;
import tech.vitalis.caringu.entity.Pessoa;
import tech.vitalis.caringu.enums.Pessoa.GeneroEnum;
import tech.vitalis.caringu.mapper.PessoaMapper;
import tech.vitalis.caringu.repository.AlunoRepository;
import tech.vitalis.caringu.repository.PessoaRepository;
import tech.vitalis.caringu.service.PessoaService;
import tech.vitalis.caringu.service.SingleSignOn.GoogleTokenVerifierService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AutenticacaoController {

    @Autowired
    private GoogleTokenVerifierService tokenVerifier;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private GerenciadorTokenJwt jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PessoaService pessoaService;

    @Autowired
    private AlunoRepository alunoRepository;

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

        final Pessoa pessoa = PessoaMapper.of(pessoaLoginDto);
        PessoaTokenDTO pessoaTokenDto = this.pessoaService.autenticar(pessoa);

        // Define o cookie JWT (mais seguro que localStorage)
        cookieJwtUtil.createJwtCookie(response, pessoaTokenDto.getToken());

        return ResponseEntity.status(200).body(pessoaTokenDto);
    }

    @PostMapping("/login/google")
    public ResponseEntity<?> loginGoogle(@RequestBody Map<String, String> request, HttpServletResponse response) {
        String code = request.get("code");

        return tokenVerifier.exchangeCodeForPayload(code)
                .map(payload -> {
                    String email = payload.getEmail();
                    String nome = (String) payload.get("name");
                    String fotoPerfil = (String) payload.get("picture");

                    Pessoa pessoa = pessoaRepository.findByEmail(email)
                            .orElseGet(() -> {
                                Aluno aluno = new Aluno();
                                aluno.setNome(nome);
                                aluno.setEmail(email);
                                aluno.setGenero(GeneroEnum.HOMEM_CISGENERO);
                                aluno.setDataCadastro(LocalDateTime.now());
                                aluno.setUrlFotoPerfil(fotoPerfil);

                                String senhaCriptografada = passwordEncoder.encode("123Ab@");
                                aluno.setSenha(senhaCriptografada);

                                alunoRepository.save(aluno);

                                return aluno;
                            });

                    Authentication authentication = new UsernamePasswordAuthenticationToken(
                            pessoa.getEmail(), null, List.of()
                    );

                    String appToken = jwtService.generateToken(authentication);

                    // Define o cookie JWT (mais seguro que localStorage)
                    cookieJwtUtil.createJwtCookie(response, appToken);

                    return ResponseEntity.ok(Map.of(
                            "token", appToken,
                            "pessoaId", pessoa.getId(),
                            "nome", pessoa.getNome(),
                            "email", pessoa.getEmail(),
                            "urlFotoPerfil", pessoa.getUrlFotoPerfil(),
                            "tipo", "ALUNO"
                    ));
                })
                .orElse(ResponseEntity.status(401).body(Map.of("erro", "Token inválido do Google.")));
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
