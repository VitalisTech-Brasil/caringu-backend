package tech.vitalis.caringu.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.vitalis.caringu.dtos.Aluno.*;
import tech.vitalis.caringu.entity.Aluno;
import tech.vitalis.caringu.mapper.AlunoMapper;
import tech.vitalis.caringu.service.AlunoService;

import java.util.List;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

    private final AlunoService service;
    private final AlunoMapper mapper;

    public AlunoController(AlunoService service, AlunoMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Listar alunos")
    public ResponseEntity<List<AlunoResponseGetDTO>> listar() {
        List<AlunoResponseGetDTO> listaAlunos = service.listar();

        return ResponseEntity.status(200).body(listaAlunos);
    }

    @GetMapping("/presenca/{personalId}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<List<PresencaAlunoResponseDTO>> listarPresencas(
            @PathVariable Integer personalId,
            @RequestParam("periodo") String periodo) {
        return ResponseEntity.ok(service.listarPresencas(periodo, personalId));
    }

    @GetMapping("/ativos/{personalId}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<List<AlunoAtivoResponseDTO>> listarAlunosAtivos(@PathVariable Integer personalId) {
        return ResponseEntity.ok(service.listarAlunosAtivos(personalId));
    }

    @GetMapping("/anamneses-pendentes")
    public ResponseEntity<List<AlunoResponseGetDTO>> listarAlunosSemAnamnese() {
        List<AlunoResponseGetDTO> alunosSemAnamnese = service.buscarAlunosSemAnamnese();

        return ResponseEntity.ok(alunosSemAnamnese);
    }

    @GetMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Buscar aluno por ID")
    public ResponseEntity<AlunoResponseGetDTO> buscarPorId(@PathVariable Integer id) {
        AlunoResponseGetDTO aluno = service.buscarPorId(id);
        return ResponseEntity.ok(aluno);
    }

    @PostMapping
    @Operation(summary = "Cadastrar aluno")
    public ResponseEntity<AlunoResponseGetDTO> cadastrar(@Valid @RequestBody AlunoRequestPostDTO cadastroDTO) {
        Aluno aluno = mapper.toEntity(cadastroDTO);
        AlunoResponseGetDTO respostaDTO = service.cadastrar(aluno);

        return ResponseEntity.status(201).body(respostaDTO);
    }

    @PutMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Atualizar aluno")
    public ResponseEntity<AlunoResponseGetDTO> atualizar(
            @PathVariable Integer id,
            @Valid @RequestBody AlunoRequestPostDTO dto) {

        Aluno novoAluno = mapper.toEntity(dto);
        AlunoResponseGetDTO atualizado = service.atualizar(id, novoAluno);
        return ResponseEntity.ok(atualizado);
    }

    @PatchMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Atualizar aluno parcialmente")
    public ResponseEntity<AlunoResponsePatchDTO> atualizarParcial(
            @PathVariable Integer id,
            @Valid @RequestBody AlunoRequestPatchDTO atualizacoes) {

        AlunoResponsePatchDTO atualizado = service.atualizarParcial(id, atualizacoes);
        return ResponseEntity.ok(atualizado);
    }

    @PatchMapping("/{id}/dados-fisicos")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Atualizar apenas os dados físicos do aluno utilizados pela anamnese")
    public ResponseEntity<AlunoResponsePatchDadosFisicosDTO> atualizarDadosFisicos(
            @PathVariable Integer id,
            @Valid @RequestBody AlunoRequestPatchDadosFisicosDTO dto) {

        AlunoResponsePatchDadosFisicosDTO atualizado = service.atualizarDadosFisicos(id, dto);

        return ResponseEntity.ok().body(atualizado);
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Remover aluno")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
