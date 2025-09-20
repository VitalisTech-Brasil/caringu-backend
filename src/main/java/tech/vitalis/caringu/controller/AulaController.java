package tech.vitalis.caringu.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.vitalis.caringu.dtos.SessaoTreino.AtualizarStatusSessaoTreinoDTO;
import tech.vitalis.caringu.dtos.SessaoTreino.SessaoAulasAgendadasResponseDTO;
import tech.vitalis.caringu.dtos.SessaoTreino.EvolucaoCargaDashboardResponseDTO;
import tech.vitalis.caringu.dtos.SessaoTreino.EvolucaoTreinoCumpridoResponseDTO;
import tech.vitalis.caringu.dtos.SessaoTreino.HorasTreinadasResponseDTO;
import tech.vitalis.caringu.service.AulaService;

import java.util.List;

@RestController
@SecurityRequirement(name = "Bearer")
@RequestMapping("/aulas")
public class AulaController {

    private final AulaService aulaService;

    public AulaController(AulaService aulaService) {
        this.aulaService = aulaService;
    }

    @GetMapping("/personal-aulas/{idPersonal}")
    public ResponseEntity<List<SessaoAulasAgendadasResponseDTO>> listarAulasPorPersonal(@PathVariable Integer idPersonal) {
        List<SessaoAulasAgendadasResponseDTO> aulasAgendadas = aulaService.listarAulasPorPersonal(idPersonal);

        return ResponseEntity.ok(aulasAgendadas);
    }

    @GetMapping("/evolucao-carga")
    public List<EvolucaoCargaDashboardResponseDTO> buscarEvolucaoCarga(
            @RequestParam Integer idAluno,
            @RequestParam Integer idExercicio
    ) {
        return aulaService.buscarEvolucaoCarga(idAluno, idExercicio);
    }

    @GetMapping("/evolucao-treinos-cumpridos")
    public ResponseEntity<List<EvolucaoTreinoCumpridoResponseDTO>> buscarEvolucao(
            @RequestParam Integer idAluno,
            @RequestParam Integer idExercicio
    ) {
        List<EvolucaoTreinoCumpridoResponseDTO> resultado = aulaService.buscarEvolucaoTreinosCumpridosMensal(idAluno, idExercicio);
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/horas-treinadas")
    public ResponseEntity<HorasTreinadasResponseDTO> buscarHorasTreinadas(
            @RequestParam Integer idAluno,
            @RequestParam Integer idExercicio
    ) {
        HorasTreinadasResponseDTO resultado = aulaService.buscarHorasTreinadas(idAluno, idExercicio);
        return ResponseEntity.ok(resultado);
    }

    @PatchMapping("/{idSessaoTreino}/status")
    @Operation(summary = "Atualizar status da sessão do treino")
    public ResponseEntity<Void> atualizarStatus(
            @PathVariable("idSessaoTreino") Integer idSessaoTreino,
            @RequestBody AtualizarStatusSessaoTreinoDTO dto
    ) {
        aulaService.atualizarStatus(idSessaoTreino, dto.status());
        return ResponseEntity.noContent().build();
    }
}
