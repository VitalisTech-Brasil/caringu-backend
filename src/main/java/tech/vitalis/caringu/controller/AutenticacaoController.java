package tech.vitalis.caringu.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.vitalis.caringu.dtos.Pessoa.security.PessoaLoginDTO;
import tech.vitalis.caringu.dtos.Pessoa.security.PessoaTokenDTO;
import tech.vitalis.caringu.entity.Pessoa;
import tech.vitalis.caringu.mapper.PessoaMapper;
import tech.vitalis.caringu.service.PessoaService;

@RestController
@RequestMapping("/auth")
public class AutenticacaoController {

    @Autowired
    private PessoaService pessoaService;

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
    public ResponseEntity<PessoaTokenDTO> login(@RequestBody PessoaLoginDTO pessoaLoginDto) {

        final Pessoa pessoa = PessoaMapper.of(pessoaLoginDto);
        PessoaTokenDTO pessoaTokenDto = this.pessoaService.autenticar(pessoa);

        return ResponseEntity.status(200).body(pessoaTokenDto);
    }

}
