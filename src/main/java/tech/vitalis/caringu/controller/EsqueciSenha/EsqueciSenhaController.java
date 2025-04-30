package tech.vitalis.caringu.controller.EsqueciSenha;


import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.vitalis.caringu.dtos.EsqueciSenha.EsqueciSenhaCodigoDto;
import tech.vitalis.caringu.dtos.EsqueciSenha.EsqueciSenhaEmailDto;
import tech.vitalis.caringu.dtos.EsqueciSenha.EsqueciSenhaNovaSenhaDto;
import tech.vitalis.caringu.service.EsqueciSenha.EsqueciSenhaService;

import java.util.Objects;

@RestController
@RequestMapping("/esqueci-senha")
public class EsqueciSenhaController {

    private final EsqueciSenhaService esquecisenhaservice;

    public EsqueciSenhaController(EsqueciSenhaService esquecisenhaservice) {
        this.esquecisenhaservice = esquecisenhaservice;
    }

    @PostMapping
    @Operation(
            summary = "Solicitar token de recuperação de senha",
            description = "Envia um token de verificação para o e-mail informado, caso ele esteja cadastrado. "
                    + "Não requer autenticação. Deve ser informado um e-mail válido no corpo da requisição."
    )
    public ResponseEntity<String> enviarToken(@RequestBody @Valid EsqueciSenhaEmailDto dto) {
        String response = esquecisenhaservice.validarEmail(dto);

        if (response.equals("E-mail inválido.")){
            return ResponseEntity.status(400).body(response);
        } else if (response.equals("E-mail não encontrado.")) {
            return ResponseEntity.status(404).body(response);
        }

        return ResponseEntity.status(200).body(response);
    }

    @PostMapping("/validacao-token")
    @Operation(
            summary = "Validar token de recuperação",
            description = "Verifica se o token enviado por e-mail é válido. "
                    + "O usuário deve informar o e-mail e o token recebido. Não requer autenticação."
    )
    public ResponseEntity<Boolean> validarToken(@RequestBody @Valid EsqueciSenhaCodigoDto dto){
        Boolean response = esquecisenhaservice.vaidarToken(dto);
        return ResponseEntity.status(200).body(response);
    }

    @PatchMapping("/nova-senha")
    @Operation(
            summary = "Cadastrar nova senha",
            description = "Permite redefinir a senha de um usuário após a validação do token. "
                    + "É necessário informar o e-mail e a nova senha."
    )
    public ResponseEntity<Boolean> cadastrarNovaSenha(@RequestBody @Valid EsqueciSenhaNovaSenhaDto dto){
        Boolean response = esquecisenhaservice.cadastrarNovaSenha(dto);

        return ResponseEntity.status(200).body(response);
    }
}
