package tech.vitalis.caringu.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.vitalis.caringu.model.Exercicio;
import tech.vitalis.caringu.service.ExercicioService;

import java.util.List;
import java.util.Optional;


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

    @GetMapping("/{id}")
    public ResponseEntity<Exercicio> pegarExercicioPorId(@PathVariable Long id) {
        Optional<Exercicio> exercicio = exercicioService.buscarExercicioPorId(id);

        if (exercicio.isPresent()) {
            return ResponseEntity.status(200).body(exercicio.get());
        }

        return ResponseEntity.status(404).build();
    }

    @PutMapping("/{id}")
    public Exercicio atualizarExercicio(@PathVariable Long id , @RequestBody Exercicio exercicio) {
        return exercicioService.atualizarExercicio(id, exercicio).getBody();
    }

    @PatchMapping("/{id}")
    public Exercicio atualizarParteExercicio(@PathVariable Long id, @RequestBody Exercicio exercicio) {
        return exercicioService.atualizarParteExercicio(id, exercicio).getBody();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerExercicio(@PathVariable Long id) {
        return exercicioService.removerExercicio(id);
    }

}
