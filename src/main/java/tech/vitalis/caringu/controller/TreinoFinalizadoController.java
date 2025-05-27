package tech.vitalis.caringu.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.vitalis.caringu.dtos.TreinoFinalizado.AtualizarDataFimDTO;
import tech.vitalis.caringu.dtos.TreinoFinalizado.TreinoIdentificacaoFinalizadoResponseDTO;
import tech.vitalis.caringu.service.TreinoFinalizadoService;

import java.util.List;

@RestController
@SecurityRequirement(name = "Bearer")
@RequestMapping("/treinos-finalizados")
public class TreinoFinalizadoController {

    private final TreinoFinalizadoService service;

    public TreinoFinalizadoController(TreinoFinalizadoService service) {
        this.service = service;
    }

    @GetMapping("/personal/{personalId}")
    public ResponseEntity<List<TreinoIdentificacaoFinalizadoResponseDTO>> listarTreinosPorPersonal(@PathVariable Integer personalId) {
        List<TreinoIdentificacaoFinalizadoResponseDTO> treinos = service.listarPorPersonal(personalId);
        return ResponseEntity.ok(treinos);
    }

    @PatchMapping("/{idTreinoFinalizado}/finalizar")
    public ResponseEntity<Void> finalizarTreino(@PathVariable Integer idTreinoFinalizado, @RequestBody AtualizarDataFimDTO dto) {
        service.atualizarDataHorarioFim(idTreinoFinalizado, dto);
        return ResponseEntity.noContent().build();
    }
}
