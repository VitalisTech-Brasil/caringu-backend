package tech.vitalis.caringu.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.vitalis.caringu.dtos.Treino.TreinoRequestUpdateDto;
import tech.vitalis.caringu.dtos.Treino.TreinoResponseGetDTO;
import tech.vitalis.caringu.dtos.Treino.TreinoRequestPostDTO;
import tech.vitalis.caringu.service.TreinoService;

import java.util.List;

@RestController
@RequestMapping("/treino")
public class TreinoController {

    private final TreinoService treinoService;

    public TreinoController(TreinoService treinoService) {
        this.treinoService = treinoService;
    }

    @PostMapping
    @Operation(summary = "Cadastrar novo Treino")
    public ResponseEntity<TreinoResponseGetDTO> cadastrar(@Valid @RequestBody TreinoRequestPostDTO treinoDTO){
        TreinoResponseGetDTO treinoCriado = treinoService.cadastrar(treinoDTO);
        return ResponseEntity.status(201).body(treinoCriado);
    }

    @GetMapping
    @Operation(summary = "Buscar todos os Treinos")
    public ResponseEntity<List<TreinoResponseGetDTO>> listarTodos(){
        return ResponseEntity.ok(treinoService.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar Treino por id")
    public ResponseEntity<TreinoResponseGetDTO> buscarPorId(@PathVariable Integer id){
        return ResponseEntity.ok(treinoService.buscarPorId(id));
    }

    @PutMapping("/{id}/personal/{personalId}")
    @Operation(summary = "Atualizar treino")
    public ResponseEntity<TreinoResponseGetDTO> atualizar(@PathVariable Integer id, @PathVariable Integer personalId, @RequestBody @Valid TreinoRequestUpdateDto treinoDto){
        return ResponseEntity.ok(treinoService.atualizar(id, treinoDto, personalId));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover Treino")
    public ResponseEntity<Void> removerTreino(@PathVariable Integer id){
        treinoService.removerComDesassociacao(id);
        treinoService.remover(id);
        return ResponseEntity.noContent().build();
    }

}
