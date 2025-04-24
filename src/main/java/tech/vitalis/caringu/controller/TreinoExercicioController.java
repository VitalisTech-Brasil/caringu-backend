package tech.vitalis.caringu.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.vitalis.caringu.dtos.Treino.TreinoRequestPostDTO;
import tech.vitalis.caringu.dtos.TreinoExericio.TreinoExercicioRequestPostDto;
import tech.vitalis.caringu.dtos.TreinoExericio.TreinoExercicioRequestUpdateDto;
import tech.vitalis.caringu.dtos.TreinoExericio.TreinoExercicioResponseGetDto;
import tech.vitalis.caringu.entity.TreinoExercicio;
import tech.vitalis.caringu.service.TreinoExercicioService;

import java.util.List;

@RestController
@RequestMapping("/treinos-exercicios")
public class TreinoExercicioController {

    private final TreinoExercicioService treinoExercicioService;

    public TreinoExercicioController(TreinoExercicioService treinoExercicioService) {
        this.treinoExercicioService = treinoExercicioService;
    }

    @PostMapping
    @Operation(summary = "Cadastrar novo Treino Exercício")
    public ResponseEntity<TreinoExercicioResponseGetDto> cadastrar(@Valid @RequestBody TreinoExercicioRequestPostDto treinoDTO){
        TreinoExercicioResponseGetDto treinoCriado = treinoExercicioService.cadastrar(treinoDTO);
        return ResponseEntity.status(201).body(treinoCriado);
    }

    @GetMapping
    @Operation(summary = "Buscar todos os Treino Exercício")
    public ResponseEntity<List<TreinoExercicioResponseGetDto>> listarTodos(){
        return ResponseEntity.ok(treinoExercicioService.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar Treino Exercício por Id")
    public ResponseEntity<TreinoExercicioResponseGetDto> buscarPorId(@PathVariable Integer id){
        return ResponseEntity.ok(treinoExercicioService.buscarPorId(id));
    }

    @PutMapping("/{id}/treino/{treinoId}/exercicio/{exercicioId}")
    @Operation(summary = "Atualizar o Treino Exercício")
    public ResponseEntity<TreinoExercicioResponseGetDto> atualizar(@PathVariable Integer id, @PathVariable Integer treinoId, @PathVariable Integer exercicioId, @RequestBody @Valid TreinoExercicioRequestUpdateDto treinoDto){
        return ResponseEntity.ok(treinoExercicioService.atualizar(id, treinoDto, exercicioId, treinoId));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover Treino")
    public ResponseEntity<Void> removerTreino(@PathVariable Integer id){
        treinoExercicioService.removerComDesassociacao(id);
        treinoExercicioService.remover(id);
        return ResponseEntity.noContent().build();
    }
}
