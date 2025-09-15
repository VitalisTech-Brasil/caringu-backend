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

    @GetMapping("/evolucao-carga")
    public List<EvolucaoCargaDashboardResponseDTO> buscarEvolucaoCarga(
            @RequestParam Integer idAluno,
            @RequestParam Integer idExercicio
    ) {
        return sessaoTreinoService.buscarEvolucaoCarga(idAluno, idExercicio);
    }

    @GetMapping("/evolucao-treinos-cumpridos")
    public ResponseEntity<List<EvolucaoTreinoCumpridoResponseDTO>> buscarEvolucao(
            @RequestParam Integer idAluno,
            @RequestParam Integer idExercicio
    ) {
        List<EvolucaoTreinoCumpridoResponseDTO> resultado = sessaoTreinoService.buscarEvolucaoTreinosCumpridosMensal(idAluno, idExercicio);
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/horas-treinadas")
    public ResponseEntity<HorasTreinadasResponseDTO> buscarHorasTreinadas(
            @RequestParam Integer idAluno,
            @RequestParam Integer idExercicio
    ) {
        HorasTreinadasResponseDTO resultado = sessaoTreinoService.buscarHorasTreinadas(idAluno, idExercicio);
        return ResponseEntity.ok(resultado);
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
