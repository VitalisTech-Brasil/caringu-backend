package tech.vitalis.caringu.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.vitalis.caringu.dtos.Avaliacao.AvaliacaoRequestDTO;
import tech.vitalis.caringu.dtos.Avaliacao.AvaliacaoResponseDTO;
import tech.vitalis.caringu.service.AvaliacaoService;

import java.util.List;

@RestController
@RequestMapping("/avaliacoes")
public class AvaliacaoController {
    @Autowired
    private AvaliacaoService avaliacaoService;

    @GetMapping("/personal/{idPersonal}")
    public ResponseEntity<List<AvaliacaoResponseDTO>> listarAvaliacoesPorPersonal(@PathVariable Integer idPersonal) {
        return avaliacaoService.listarAvaliacoesPorPersonal(idPersonal);
    }

    @PostMapping()
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<AvaliacaoResponseDTO> criarAvaliacao(@RequestBody AvaliacaoRequestDTO avaliacaoRequestDTO) {
        // Implementar a lógica para criar uma nova avaliação
        return avaliacaoService.cadastrarAvaliacao(avaliacaoRequestDTO);
    }
}
