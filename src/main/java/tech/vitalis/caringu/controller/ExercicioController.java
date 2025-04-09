package tech.vitalis.caringu.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.vitalis.caringu.dtos.Exercicio.CriacaoExercicioDTO;
import tech.vitalis.caringu.dtos.Exercicio.RespostaExercicioDTO;
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
    @Operation(summary = "Cadastrar novo exercício")
    public ResponseEntity<RespostaExercicioDTO> cadastrar(@RequestBody @Valid CriacaoExercicioDTO exercicioDto) {
        RespostaExercicioDTO exercicioCriado = exercicioService.cadastrar(exercicioDto);
        return ResponseEntity.status(201).body(exercicioCriado);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar exercício por ID")
    public ResponseEntity<RespostaExercicioDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(exercicioService.buscarPorId(id));
    }

    @GetMapping
    @Operation(summary = "Listar todos os exercícios")
    public ResponseEntity<List<RespostaExercicioDTO>> listarTodos() {
        return ResponseEntity.ok(exercicioService.listarTodos());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar exercício")
    public ResponseEntity<RespostaExercicioDTO> atualizar(@PathVariable Integer id, @RequestBody @Valid CriacaoExercicioDTO exercicioDto) {
        return ResponseEntity.ok(exercicioService.atualizar(id, exercicioDto));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Atualizar exercício parcialmente")
    public ResponseEntity<RespostaExercicioDTO> editarInfoExercicio(@PathVariable Integer id, @RequestBody CriacaoExercicioDTO exercicioDto) {
        return ResponseEntity.ok(exercicioService.editarInfoExercicio(id, exercicioDto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover exercício")
    public ResponseEntity<Void> remover(@PathVariable Integer id) {
        exercicioService.remover(id);
        return ResponseEntity.noContent().build();
    }
}
