package tech.vitalis.caringu.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.vitalis.caringu.dtos.Aula.ProximaAulaDTO;
import tech.vitalis.caringu.dtos.AulaTreinoExercicio.Request.AtribuicaoTreinosAulaRequestPostDTO;
import tech.vitalis.caringu.dtos.AulaTreinoExercicio.Request.RemarcarAulaTreinoRequestDTO;
import tech.vitalis.caringu.dtos.AulaTreinoExercicio.Response.AtribuicaoTreinosAulaResponsePostDTO;
import tech.vitalis.caringu.dtos.AulaTreinoExercicio.Response.RemarcarAulaTreinoResponseDTO;
import tech.vitalis.caringu.dtos.AulaTreinoExercicio.Response.VisualizarAulasResponseDTO;
import tech.vitalis.caringu.service.AulaTreinoExercicioService;

import java.util.List;

@RestController
@SecurityRequirement(name = "Bearer")
@RequestMapping("/aulas-treinos-exercicios")
public class AulaTreinoExercicioController {

    private final AulaTreinoExercicioService aulaTreinoExercicioService;

    public AulaTreinoExercicioController(
            AulaTreinoExercicioService aulaTreinoExercicioService
    ) {
        this.aulaTreinoExercicioService = aulaTreinoExercicioService;
    }

    @GetMapping("/visualizar-aula/{idAula}")
    public ResponseEntity<VisualizarAulasResponseDTO> listarAulasComTreinosExercicios(
            @PathVariable Integer idAula,
            @RequestParam Integer idAluno
    ) {
        aulaTreinoExercicioService.listarAulasComTreinosExercicios(idAula, idAluno);
    }

    @PostMapping("/atribuicao/treinos")
    public ResponseEntity<AtribuicaoTreinosAulaResponsePostDTO> atribuirTreinoAAula(
            @Valid @RequestBody AtribuicaoTreinosAulaRequestPostDTO requestDTO
    ) {
        AtribuicaoTreinosAulaResponsePostDTO response = aulaTreinoExercicioService.atribuirTreinoAAula(requestDTO);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/remarcar")
    public ResponseEntity<RemarcarAulaTreinoResponseDTO> remarcarAulaTreino(
            @Valid @RequestBody RemarcarAulaTreinoRequestDTO request
    ) {
        RemarcarAulaTreinoResponseDTO response = aulaTreinoExercicioService.remarcarAulaTreino(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/buscar-aulas/{id}")
    @Operation(summary = "Listar próximas 2 aulas do aluno")
    public ResponseEntity<List<ProximaAulaDTO>> listarProximasAulas(@PathVariable Integer id) {
        List<ProximaAulaDTO> proxAulas = aulaTreinoExercicioService.listarProximasAulas(id);
        return ResponseEntity.ok(proxAulas);
    }
}
