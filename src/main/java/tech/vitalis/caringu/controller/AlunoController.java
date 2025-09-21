package tech.vitalis.caringu.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    @GetMapping("/paginado")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Listar alunos paginado")
    public ResponseEntity<Page<AlunoResponseGetDTO>> listarPaginado(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "4") int size
    ) {
        Page<AlunoResponseGetDTO> paginadoAlunos = service.listarPaginado(PageRequest.of(page, size));

        return ResponseEntity.status(200).body(paginadoAlunos);
    }

    @GetMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Buscar aluno por ID")
    public ResponseEntity<AlunoResponseGetDTO> buscarPorId(@PathVariable Integer id) {
        AlunoResponseGetDTO aluno = service.buscarPorId(id);
        return ResponseEntity.ok(aluno);
    }

    @GetMapping("/detalhes/personal/{personalId}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<List<AlunoDetalhadoComTreinosDTO>> buscarPorPersonal(@PathVariable Integer personalId) {
        var dados = service.buscarAlunosDetalhados(personalId);
        return ResponseEntity.ok(dados);
    }

    // Esse endpoint vai ser usado pra listar os alunos com planos ativos do personal
    // (na tela de Gerenciar Alunos -> No lado direito da tela: Alunos Ativos
    @GetMapping("/detalhes/personal/paginado/{personalId}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<Page<AlunoDetalhadoComTreinosDTO>> buscarPorPersonalPaginado(
            @PathVariable Integer personalId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "4") int size
            ) {
        Page<AlunoDetalhadoComTreinosDTO> dados = service.buscarAlunosDetalhadosPaginado(personalId, PageRequest.of(page, size));
        return ResponseEntity.ok(dados);
    }

    @GetMapping("/buscar-aluno/{email}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Buscar aluno por email")
    public ResponseEntity<AlunoResponseGetDTO> buscarAlunoPorEmail(@PathVariable String email){
        AlunoResponseGetDTO aluno = service.buscarAlunoPorEmail(email);
        return ResponseEntity.ok(aluno);
    }

    @GetMapping("/buscar-varios-alunos/{email}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Buscar alunos por email")
    public ResponseEntity<List<AlunoResponseGetDTO>> buscarAlunosPorEmail(@PathVariable String email){
        List<AlunoResponseGetDTO> alunos = service.buscarAlunosPorEmail(email);

        return ResponseEntity.status(200).body(alunos);
    }

    @PostMapping
    @Operation(summary = "Cadastrar aluno")
    public ResponseEntity<AlunoResponseGetDTO> cadastrar(@Valid @RequestBody AlunoRequestPostDTO cadastroDTO) {
        AlunoResponseGetDTO respostaDTO = service.cadastrar(cadastroDTO);

        return ResponseEntity.status(201).body(respostaDTO);
    }

    @PutMapping("/{alunoId}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Atualizar aluno")
    public ResponseEntity<AlunoResponseGetDTO> atualizar(
            @PathVariable Integer alunoId,
            @Valid @RequestBody AlunoRequestPostDTO dto) {

        Aluno novoAluno = mapper.toEntity(dto);
        AlunoResponseGetDTO atualizado = service.atualizar(alunoId, novoAluno);
        return ResponseEntity.ok(atualizado);
    }

    @PatchMapping("/{alunoId}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Atualizar aluno parcialmente")
    public ResponseEntity<AlunoResponsePatchDTO> atualizarParcial(
            @PathVariable Integer alunoId,
            @Valid @RequestBody AlunoRequestPatchDTO atualizacoes) {

        AlunoResponsePatchDTO atualizado = service.atualizarParcial(alunoId, atualizacoes);
        return ResponseEntity.ok(atualizado);
    }

    @PatchMapping("/{alunoId}/dados-fisicos")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Atualizar apenas os dados físicos do aluno utilizados pela anamnese")
    public ResponseEntity<AlunoResponsePatchDadosFisicosDTO> atualizarDadosFisicos(
            @PathVariable Integer alunoId,
            @Valid @RequestBody AlunoRequestPatchDadosFisicosDTO dto) {

        AlunoResponsePatchDadosFisicosDTO atualizado = service.atualizarDadosFisicos(alunoId, dto);

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
