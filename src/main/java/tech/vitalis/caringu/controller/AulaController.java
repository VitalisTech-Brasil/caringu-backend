package tech.vitalis.caringu.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.vitalis.caringu.dtos.Aula.ListaAulasRascunho.AulasRascunhoResponseDTO;
import tech.vitalis.caringu.dtos.Aula.Request.AtribuicaoTreinosAulaRequestPostDTO;
import tech.vitalis.caringu.dtos.Aula.Request.AulaRascunhoRequestPostDTO;
import tech.vitalis.caringu.dtos.Aula.Response.AtribuicaoTreinosAulaResponsePostDTO;
import tech.vitalis.caringu.dtos.Aula.Response.AulaRascunhoResponsePostDTO;
import tech.vitalis.caringu.dtos.Aula.Response.AulaTreinoResponsePostDTO;
import tech.vitalis.caringu.dtos.Aula.TotalAulasAgendamentoResponseGetDTO;
import tech.vitalis.caringu.dtos.SessaoTreino.*;
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
    public ResponseEntity<List<EvolucaoCargaDashboardResponseDTO>> buscarEvolucaoCarga(
            @RequestParam Integer idAluno,
            @RequestParam Integer idExercicio
    ) {
        List<EvolucaoCargaDashboardResponseDTO> resultado = aulaService.buscarEvolucaoCarga(idAluno, idExercicio);

        return ResponseEntity.ok(resultado);
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

    @GetMapping("/{idAluno}/disponibilidade")
    public ResponseEntity<TotalAulasAgendamentoResponseGetDTO> buscarDisponibilidadeDeAulas(
            @PathVariable Integer idAluno
    ) {
        TotalAulasAgendamentoResponseGetDTO infoTotalAulas = aulaService.buscarDisponibilidadeDeAulas(idAluno);
        return ResponseEntity.ok(infoTotalAulas);
    }

    @GetMapping("/{idAluno}/rascunhos")
    public ResponseEntity<AulasRascunhoResponseDTO> buscarAulasRascunho(@PathVariable Integer idAluno) {
        AulasRascunhoResponseDTO aulas = aulaService.buscarAulasRascunho(idAluno);
        return ResponseEntity.ok(aulas);
    }

    @PostMapping("/{idAluno}/rascunhos")
    public ResponseEntity<AulaRascunhoResponsePostDTO> criarAulasRascunho(
            @PathVariable Integer idAluno,
            @Valid @RequestBody AulaRascunhoRequestPostDTO requestDTO
    ) {
        AulaRascunhoResponsePostDTO response = aulaService.criarAulasRascunho(idAluno, requestDTO);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{idAula}/atribuicao/treino")
    public ResponseEntity<AtribuicaoTreinosAulaResponsePostDTO> atribuirTreinoAAula(
            @PathVariable Integer idAula,
            @Valid @RequestBody AtribuicaoTreinosAulaRequestPostDTO requestDTO
    ) {
        AtribuicaoTreinosAulaResponsePostDTO response = aulaService.atribuirTreinoAAula(idAula, requestDTO);
        return ResponseEntity.ok(response);
    }

//    @PatchMapping("/{idAluno}/rascunhos/agendado")

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
