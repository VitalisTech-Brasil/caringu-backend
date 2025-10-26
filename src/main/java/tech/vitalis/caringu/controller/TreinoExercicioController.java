package tech.vitalis.caringu.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.vitalis.caringu.dtos.TreinoExercicio.*;
import tech.vitalis.caringu.service.TreinoExercicioService;

import java.util.List;

@RestController
@RequestMapping("/treinos-exercicios")
@SecurityRequirement(name = "Bearer")
public class TreinoExercicioController {

    private final TreinoExercicioService treinoExercicioService;

    public TreinoExercicioController(TreinoExercicioService treinoExercicioService) {
        this.treinoExercicioService = treinoExercicioService;
    }

    @GetMapping("/personal/{personalId}")
    @Operation(summary = "Buscar todos os modelos de treinos com exercícios criados.")
    public ResponseEntity<List<TreinoExercicioResumoDTO>> listarPorPersonal(@PathVariable Integer personalId) {
        List<TreinoExercicioResumoDTO> treinosExerciciosResumo = treinoExercicioService.listarPorPersonal(personalId);
        return ResponseEntity.ok(treinosExerciciosResumo);
    }

    @GetMapping("/aluno/{alunoId}")
    @Operation(summary = "Buscar todos os Treino Exercício do Aluno")
    public ResponseEntity<List<TreinoExercicioResumoDTO>> listarPorAluno(@PathVariable Integer alunoId) {
        List<TreinoExercicioResumoDTO> treinosExerciciosResumo = treinoExercicioService.listarPorAluno(alunoId);
        return ResponseEntity.ok(treinosExerciciosResumo);
    }

    @GetMapping("/aluno-paginado/{alunoId}")
    @Operation(summary = "Paginar os Treino Exercício do Aluno")
    public ResponseEntity<Page<TreinoExercicioResumoDTO>> paginarPorAluno(
            @PathVariable Integer alunoId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size
    ) {
        Page<TreinoExercicioResumoDTO> treinosExerciciosResumo = treinoExercicioService.paginarPorAluno(alunoId, PageRequest.of(page, size));
        return ResponseEntity.ok(treinosExerciciosResumo);
    }

    @GetMapping("/relatorio-treino/aluno/{idAluno}")
    @Operation(summary = "Listar os Treinos Exercícios do Aluno na tela de Relatório de Treino de forma paginada")
    public ResponseEntity<Page<RelatorioTreinoAlunoDTO>> listarPaginadoTreinosAlunoEmRelatorioTreino(
            @PathVariable Integer idAluno,
            @ParameterObject Pageable pageable
    ) {
        Page<RelatorioTreinoAlunoDTO> treinosAluno = treinoExercicioService.listarPaginadoTreinosAlunoEmRelatorioTreino(
                idAluno,
                pageable
        );
        return ResponseEntity.ok(treinosAluno);
    }

    @GetMapping("/exercicios-por-treino/{treinoId}/{alunoId}")
    public ResponseEntity<List<ExerciciosPorTreinoResponseDTO>> buscarExerciciosPorTreino(
            @PathVariable Integer treinoId,
            @PathVariable Integer alunoId
    ) {
        List<ExerciciosPorTreinoResponseDTO> treinosExerciciosResumo = treinoExercicioService.buscarExerciciosPorTreino(treinoId, alunoId);

        return ResponseEntity.ok(treinosExerciciosResumo);
    }

    @GetMapping("/buscar-info-treino-edit/{idPersonal}/{idTreino}")
    @Operation(summary = "Buscar informações para editar um treino e seus exercícios.")
    public ResponseEntity<List<TreinoExercicioEditResponseGetDTO>> buscarInfosEditTreinoExercicio(@PathVariable Integer idPersonal, @PathVariable Integer idTreino) {
        List<TreinoExercicioEditResponseGetDTO> infosEditTreinoExercicio =
                treinoExercicioService.buscarInfosEditTreinoExercicio(idPersonal, idTreino);

        if (infosEditTreinoExercicio.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(infosEditTreinoExercicio);
    }

    @PostMapping("/cadastrar-lote")
    @Operation(summary = "Cadastrar Novo Treino com vários Exercícios")
    public ResponseEntity<List<TreinoExercicioResponseGetDto>> cadastrarComVariosExercicios(
            @Valid @RequestBody TreinoExercicioAssociacaoRequestDTO treinoExercicioAssociacaoDTO) {

        List<TreinoExercicioResponseGetDto> treinoCriado = treinoExercicioService.cadastrarComVariosExercicios(treinoExercicioAssociacaoDTO);
        return ResponseEntity.status(201).body(treinoCriado);
    }

    @PutMapping("/atualizar/treinos/{idTreino}/exercicios")
    @Operation(summary = "Atualizar Treino com vários Exercícios")
    public ResponseEntity<List<TreinoExercicioResponseGetDto>> atualizarComVariosExercicios(
            @PathVariable Integer idTreino,
            @RequestBody @Valid TreinoExercicioAssociacaoRequestDTO dto
    ){
        List<TreinoExercicioResponseGetDto> treinoExercicioAtualizados =
                treinoExercicioService.atualizarComVariosExercicios(idTreino, dto);

        return ResponseEntity.ok(treinoExercicioAtualizados);
    }
}
