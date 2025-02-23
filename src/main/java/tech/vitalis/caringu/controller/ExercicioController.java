package tech.vitalis.caringu.controller;

import org.springframework.web.bind.annotation.*;
import tech.vitalis.caringu.model.Exercicio;
import tech.vitalis.caringu.service.ExercicioService;

import java.util.List;


@RestController
@RequestMapping("/exercicios")
public class ExercicioController {

    private final ExercicioService exercicioService;

    public ExercicioController(ExercicioService exercicioService) {
        this.exercicioService = exercicioService;
    }

    @PostMapping
    public Exercicio salvarExercicio(@RequestBody Exercicio exercicio) {
        return exercicioService.salvarExercicio(exercicio);
    }

    @GetMapping
    public List<Exercicio> pegarExercicios() {
        return exercicioService.pegarExercicios();
    }


}
