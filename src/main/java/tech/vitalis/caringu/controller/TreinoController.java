package tech.vitalis.caringu.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.vitalis.caringu.dtos.Treino.TreinoRequestUpdateDto;
import tech.vitalis.caringu.dtos.Treino.TreinoResponseGetDTO;
import tech.vitalis.caringu.dtos.Treino.TreinoRequestPostDTO;
import tech.vitalis.caringu.dtos.TreinoExercicio.TreinoExercicioFavoritoRequestPatchDto;
import tech.vitalis.caringu.service.TreinoService;

import java.util.List;

@RestController
@SecurityRequirement(name = "Bearer")
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

    @GetMapping("/treinos-criados/{idPersonal}")
    public ResponseEntity<Integer> buscarTreinosCriados(@PathVariable Integer idPersonal) {
        Integer quantidadeTreinosCriados = treinoService.obterQuantidadeTreinosCriados(idPersonal);
        return ResponseEntity.status(200).body(quantidadeTreinosCriados);
    }

    @PutMapping("/{id}/personal/{personalId}")
    @Operation(summary = "Atualizar treino")
    public ResponseEntity<TreinoResponseGetDTO> atualizar(@PathVariable Integer id, @PathVariable Integer personalId, @RequestBody @Valid TreinoRequestUpdateDto treinoDto){
        return ResponseEntity.ok(treinoService.atualizar(id, treinoDto, personalId));
    }

    @PatchMapping("/{treinoId}/favorito")
    public ResponseEntity<Void> atualizarFavorito(@PathVariable Integer treinoId, @RequestBody TreinoExercicioFavoritoRequestPatchDto dto){
        treinoService.atualizarFavorito(treinoId, dto.favorito());
        return ResponseEntity.status(204).build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover Treino")
    public ResponseEntity<Void> removerTreino(@PathVariable Integer id){
        treinoService.removerComDesassociacao(id);
        return ResponseEntity.noContent().build();
    }

}
