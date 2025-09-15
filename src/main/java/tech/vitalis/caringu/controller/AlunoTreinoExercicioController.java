package tech.vitalis.caringu.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.vitalis.caringu.dtos.AlunosTreinoExercicio.ExerciciosPorTreinoResponseDTO;
import tech.vitalis.caringu.dtos.AlunosTreinoExercicio.TreinoExercicioEditResponseGetDTO;
import tech.vitalis.caringu.dtos.TreinoExercicio.TreinoExercicioAssociacaoRequestDTO;
import tech.vitalis.caringu.dtos.TreinoExercicio.TreinoExercicioResponseGetDto;
import tech.vitalis.caringu.dtos.TreinoExercicio.TreinoExercicioResumoDTO;
import tech.vitalis.caringu.service.AlunoTreinoExercicioService;

import java.util.List;

@RestController
@RequestMapping("/alunos-treinos-exercicios")
public class AlunoTreinoExercicioController {

    private final AlunoTreinoExercicioService alunoTreinoExercicioService;

    public AlunoTreinoExercicioController(AlunoTreinoExercicioService alunoTreinoExercicioService) {
        this.alunoTreinoExercicioService = alunoTreinoExercicioService;
    }

    @GetMapping("/personal/{personalId}")
    @Operation(summary = "Buscar todos os modelos de treinos com exercícios criados.")
    public ResponseEntity<List<TreinoExercicioResumoDTO>> listarPorPersonal(@PathVariable Integer personalId) {
        List<TreinoExercicioResumoDTO> treinosExerciciosResumo = alunoTreinoExercicioService.listarPorPersonal(personalId);
        return ResponseEntity.ok(treinosExerciciosResumo);
    }

    @GetMapping("/aluno/{alunoId}")
    @Operation(summary = "Buscar todos os Treino Exercício do Aluno")
    public ResponseEntity<List<TreinoExercicioResumoDTO>> listarPorAluno(@PathVariable Integer alunoId) {
        List<TreinoExercicioResumoDTO> treinosExerciciosResumo = alunoTreinoExercicioService.listarPorAluno(alunoId);
        return ResponseEntity.ok(treinosExerciciosResumo);
    }

    @GetMapping("/aluno-paginado/{alunoId}")
    @Operation(summary = "Paginar os Treino Exercício do Aluno")
    public ResponseEntity<Page<TreinoExercicioResumoDTO>> paginarPorAluno(
            @PathVariable Integer alunoId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size
    ) {
        Page<TreinoExercicioResumoDTO> treinosExerciciosResumo = alunoTreinoExercicioService.paginarPorAluno(alunoId, PageRequest.of(page, size));
        return ResponseEntity.ok(treinosExerciciosResumo);
    }

    @GetMapping("/exercicios-por-treino/{treinoId}/{alunoId}")
    public ResponseEntity<List<ExerciciosPorTreinoResponseDTO>> buscarExerciciosPorTreino(
            @PathVariable Integer treinoId,
            @PathVariable Integer alunoId
    ) {
        List<ExerciciosPorTreinoResponseDTO> treinosExerciciosResumo = alunoTreinoExercicioService.buscarExerciciosPorTreino(treinoId, alunoId);

        return ResponseEntity.ok(treinosExerciciosResumo);
    }

    @GetMapping("/buscar-info-treino-edit/{idPersonal}/{idTreino}")
    @Operation(summary = "Buscar informações para editar um treino e seus exercícios.")
    public ResponseEntity<List<TreinoExercicioEditResponseGetDTO>> buscarInfosEditTreinoExercicio(@PathVariable Integer idPersonal, @PathVariable Integer idTreino) {
        List<TreinoExercicioEditResponseGetDTO> infosEditTreinoExercicio =
                alunoTreinoExercicioService.buscarInfosEditTreinoExercicio(idPersonal, idTreino);

        if (infosEditTreinoExercicio.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(infosEditTreinoExercicio);
    }

    @PostMapping("/cadastrar-lote")
    @Operation(summary = "Cadastrar Novo Treino com vários Exercícios")
    public ResponseEntity<List<TreinoExercicioResponseGetDto>> cadastrarComVariosExercicios(
            @Valid @RequestBody TreinoExercicioAssociacaoRequestDTO treinoExercicioAssociacaoDTO) {

        List<TreinoExercicioResponseGetDto> treinoCriado = alunoTreinoExercicioService.cadastrarComVariosExercicios(treinoExercicioAssociacaoDTO);
        return ResponseEntity.status(201).body(treinoCriado);
    }

    @PutMapping("/atualizar/treinos/{idTreino}/exercicios")
    @Operation(summary = "Atualizar Treino com vários Exercícios")
    public ResponseEntity<List<TreinoExercicioResponseGetDto>> atualizarComVariosExercicios(
            @PathVariable Integer idTreino,
            @RequestBody @Valid TreinoExercicioAssociacaoRequestDTO dto
    ){
        List<TreinoExercicioResponseGetDto> treinoExercicioAtualizados =
                alunoTreinoExercicioService.atualizarComVariosExercicios(idTreino, dto);

        return ResponseEntity.ok(treinoExercicioAtualizados);
    }
}
