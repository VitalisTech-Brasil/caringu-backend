package tech.vitalis.caringu.controller.EsqueciSenha;


import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.vitalis.caringu.dtos.EsqueciSenha.EsqueciSenhaEmailDto;
import tech.vitalis.caringu.service.EsqueciSenha.EsqueciSenhaService;

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
        return ResponseEntity.ok(response);
    }
}
