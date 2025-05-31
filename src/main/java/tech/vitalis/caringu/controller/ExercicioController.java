package tech.vitalis.caringu.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.vitalis.caringu.dtos.Exercicio.ExercicioFavoritoRequestPatchDto;
import tech.vitalis.caringu.dtos.Exercicio.ExercicioRequestPostDTO;
import tech.vitalis.caringu.dtos.Exercicio.ExercicioResponseGetDTO;
import tech.vitalis.caringu.dtos.TreinoExercicio.TreinoExercicioFavoritoRequestPatchDto;
import tech.vitalis.caringu.service.ExercicioService;
import java.util.List;

@RestController
@SecurityRequirement(name = "Bearer")
@RequestMapping("/exercicios")
public class ExercicioController {

    private final ExercicioService exercicioService;

    public ExercicioController(ExercicioService exercicioService) {
        this.exercicioService = exercicioService;
    }

    @PostMapping
    @Operation(summary = "Cadastrar novo exercício")
    public ResponseEntity<ExercicioResponseGetDTO> cadastrar(@RequestBody @Valid ExercicioRequestPostDTO exercicioDto) {
        ExercicioResponseGetDTO exercicioCriado = exercicioService.cadastrar(exercicioDto);
        return ResponseEntity.status(201).body(exercicioCriado);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar exercício por ID")
    public ResponseEntity<ExercicioResponseGetDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(exercicioService.buscarPorId(id));
    }

    @GetMapping
    @Operation(summary = "Listar todos os exercícios")
    public ResponseEntity<List<ExercicioResponseGetDTO>> listarTodos() {
        return ResponseEntity.ok(exercicioService.listarTodos());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar exercício")
    public ResponseEntity<ExercicioResponseGetDTO> atualizar(@PathVariable Integer id, @RequestBody @Valid ExercicioRequestPostDTO exercicioDto) {
        return ResponseEntity.ok(exercicioService.atualizar(id, exercicioDto));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Atualizar exercício parcialmente")
    public ResponseEntity<ExercicioResponseGetDTO> editarInfoExercicio(@PathVariable Integer id, @RequestBody ExercicioRequestPostDTO exercicioDto) {
        return ResponseEntity.ok(exercicioService.editarInfoExercicio(id, exercicioDto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover exercício")
    public ResponseEntity<Void> remover(@PathVariable Integer id) {
        exercicioService.remover(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/favorito")
    public ResponseEntity<Void> atualizarFavorito(@PathVariable Integer id, @RequestBody ExercicioFavoritoRequestPatchDto dto){
        exercicioService.atualizarFavorito(id, dto.favorito());
        return ResponseEntity.status(204).build();
    }
}
