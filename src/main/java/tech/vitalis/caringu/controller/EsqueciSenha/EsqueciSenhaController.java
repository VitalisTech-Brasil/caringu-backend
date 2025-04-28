package tech.vitalis.caringu.controller.EsqueciSenha;


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
    public ResponseEntity<Boolean> validarToken(@RequestBody @Valid EsqueciSenhaCodigoDto dto){
        Boolean response = esquecisenhaservice.vaidarToken(dto);
        return ResponseEntity.status(200).body(response);
    }

    @PatchMapping("/nova-senha")
    public ResponseEntity<Boolean> cadastrarNovaSenha(@RequestBody @Valid EsqueciSenhaNovaSenhaDto dto){
        Boolean response = esquecisenhaservice.cadastrarNovaSenha(dto);

        return ResponseEntity.status(200).body(response);
    }
}
