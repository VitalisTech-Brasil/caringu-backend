package tech.vitalis.caringu.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.vitalis.caringu.dtos.Aula.Request.AtribuicaoTreinosAulaRequestPostDTO;
import tech.vitalis.caringu.dtos.Aula.Response.AtribuicaoTreinosAulaResponsePostDTO;
import tech.vitalis.caringu.service.AulaTreinoExercicioService;

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
}
