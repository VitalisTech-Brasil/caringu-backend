package tech.vitalis.caringu.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.vitalis.caringu.dtos.Aula.ProximaAulaDTO;
import tech.vitalis.caringu.dtos.AulaTreinoExercicio.Request.AtribuicaoTreinosAulaRequestPostDTO;
import tech.vitalis.caringu.dtos.AulaTreinoExercicio.Response.AtribuicaoTreinosAulaResponsePostDTO;
import tech.vitalis.caringu.entity.AulaTreinoExercicio;
import tech.vitalis.caringu.service.AulaTreinoExercicioService;

import java.util.List;

@RestController
@RequestMapping("/aulas-treinos-exercicios")
public class AulaTreinoExercicioController {

    private final AulaTreinoExercicioService aulaTreinoExercicioService;

    public AulaTreinoExercicioController(
            AulaTreinoExercicioService aulaTreinoExercicioService
    ) {
        this.aulaTreinoExercicioService = aulaTreinoExercicioService;
    }

    @PostMapping("/atribuicao/treinos")
    public ResponseEntity<AtribuicaoTreinosAulaResponsePostDTO> atribuirTreinoAAula(
            @Valid @RequestBody AtribuicaoTreinosAulaRequestPostDTO requestDTO
    ) {
        AtribuicaoTreinosAulaResponsePostDTO response = aulaTreinoExercicioService.atribuirTreinoAAula(requestDTO);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/buscar-aulas/{id}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Listar próximas 2 aulas do aluno")
    public ResponseEntity<List<ProximaAulaDTO>> listarProximasAulas(@PathVariable Integer id) {
        List<ProximaAulaDTO> proxAulas = aulaTreinoExercicioService.listarProximasAulas(id);
        return ResponseEntity.ok(proxAulas);
    }
}
