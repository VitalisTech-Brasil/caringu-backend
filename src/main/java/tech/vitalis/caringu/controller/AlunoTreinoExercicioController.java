package tech.vitalis.caringu.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.vitalis.caringu.dtos.AlunosTreinoExercicio.ExerciciosPorTreinoResponseDTO;
import tech.vitalis.caringu.dtos.TreinoExercicio.TreinoExercicioResumoDTO;
import tech.vitalis.caringu.service.AlunoTreinoExercicioService;

import java.util.List;

@RestController
@RequestMapping("/alunos-treinos-exercicios")
public class AlunoTreinoExercicioController {

    private final AlunoTreinoExercicioService alunoTreinoExercicioService;

    public AlunoTreinoExercicioController(AlunoTreinoExercicioService alunoTreinoExercicioService) {
        this.alunoTreinoExercicioService = alunoTreinoExercicioService;
    }

    @GetMapping("/aluno/{alunoId}")
    @Operation(summary = "Buscar todos os Treino Exercício do Aluno")
    public ResponseEntity<List<TreinoExercicioResumoDTO>> listarPorAluno(@PathVariable Integer alunoId) {
        List<TreinoExercicioResumoDTO> treinosExerciciosResumo = alunoTreinoExercicioService.listarPorAluno(alunoId);
        return ResponseEntity.ok(treinosExerciciosResumo);
    }

    @GetMapping("/aluno-paginado/{alunoId}")
    @Operation(summary = "Buscar todos os Treino Exercício do Aluno")
    public ResponseEntity<Page<TreinoExercicioResumoDTO>> paginarPorAluno(
            @PathVariable Integer alunoId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size
    ) {
        Page<TreinoExercicioResumoDTO> treinosExerciciosResumo = alunoTreinoExercicioService.paginarPorAluno(alunoId, PageRequest.of(page, size));
        return ResponseEntity.ok(treinosExerciciosResumo);
    }

    @GetMapping("/exercicios-por-treino/{treinoId}/{alunoId}")
    public ResponseEntity<List<ExerciciosPorTreinoResponseDTO>> buscarExerciciosPorTreino(
            @PathVariable Integer treinoId,
            @PathVariable Integer alunoId
    ) {
        List<ExerciciosPorTreinoResponseDTO> treinosExerciciosResumo = alunoTreinoExercicioService.buscarExerciciosPorTreino(treinoId, alunoId);

        return ResponseEntity.ok(treinosExerciciosResumo);
    }
}
