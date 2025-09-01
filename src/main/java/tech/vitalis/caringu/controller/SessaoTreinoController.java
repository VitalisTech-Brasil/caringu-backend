package tech.vitalis.caringu.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.vitalis.caringu.dtos.SessaoTreino.AtualizarStatusSessaoTreinoDTO;
import tech.vitalis.caringu.dtos.SessaoTreino.SessaoAulasAgendadasResponseDTO;
import tech.vitalis.caringu.service.SessaoTreinoService;

import java.util.List;

@RestController
@SecurityRequirement(name = "Bearer")
@RequestMapping("/sessao-treino")
public class SessaoTreinoController {

    private final SessaoTreinoService sessaoTreinoService;

    public SessaoTreinoController(SessaoTreinoService sessaoTreinoService) {
        this.sessaoTreinoService = sessaoTreinoService;
    }

    @GetMapping("/personal-aulas/{idPersonal}")
    public ResponseEntity<List<SessaoAulasAgendadasResponseDTO>> listarAulasPorPersonal(@PathVariable Integer idPersonal) {
        List<SessaoAulasAgendadasResponseDTO> aulasAgendadas = sessaoTreinoService.listarAulasPorPersonal(idPersonal);

        return ResponseEntity.ok(aulasAgendadas);
    }

    @PatchMapping("/{idSessaoTreino}/status")
    @Operation(summary = "Atualizar status da sessão do treino")
    public ResponseEntity<Void> atualizarStatus(
            @PathVariable("idSessaoTreino") Integer idSessaoTreino,
            @RequestBody AtualizarStatusSessaoTreinoDTO dto
    ) {
        sessaoTreinoService.atualizarStatus(idSessaoTreino, dto.status());
        return ResponseEntity.noContent().build();
    }
}
