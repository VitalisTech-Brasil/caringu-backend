package tech.vitalis.caringu.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.vitalis.caringu.dtos.FaleConosco.FaleConoscoDTO;
import tech.vitalis.caringu.service.FaleConoscoService;

@RestController
@RequestMapping("/fale-conosco")
public class FaleConoscoController {

    private final FaleConoscoService faleConoscoService;

    public FaleConoscoController(FaleConoscoService faleConoscoService) {
        this.faleConoscoService = faleConoscoService;
    }

    @PostMapping
    public ResponseEntity<String> enviarMensagem(@RequestBody @Valid FaleConoscoDTO dto) {
        faleConoscoService.processarMensagem(dto);
        return ResponseEntity.ok("Mensagem recebida! Em breve responderemos no seu e-mail.");
    }
}
