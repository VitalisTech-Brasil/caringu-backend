package tech.vitalis.caringu.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.vitalis.caringu.model.Exercicio;
import tech.vitalis.caringu.service.ExercicioService;

import java.util.List;


@RestController
@RequestMapping("/exercicios")
public class ExercicioController {

    @Autowired
    private final ExercicioService exercicioService;

    public ExercicioController(ExercicioService exercicioService) {
        this.exercicioService = exercicioService;
    }

    @Operation(description = "Cadastro de exercicio")
    @PostMapping
    public Exercicio salvarExercicio(@RequestBody Exercicio exercicio) {
        return exercicioService.salvarExercicio(exercicio).getBody();
    }

    @GetMapping
    public ResponseEntity<List<Exercicio>> pegarExercicios() {
        return exercicioService.pegarExercicios();
    }

    @PutMapping("/{id}")
    public Exercicio atualizarExercicio(@PathVariable Long id , @RequestBody Exercicio exercicio) {
        return exercicioService.atualizarExercicio(id, exercicio).getBody();
    }

    @PatchMapping("/{id}")
    public Exercicio atualizarParteExercicio(@PathVariable Long id, @RequestBody Exercicio exercicio) {
        return exercicioService.atualizarParteExercicio(id, exercicio).getBody();
    }


}
