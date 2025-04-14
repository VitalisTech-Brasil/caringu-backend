package tech.vitalis.caringu.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.vitalis.caringu.dtos.Aluno.AlunoRequestPatchDTO;
import tech.vitalis.caringu.dtos.Aluno.AlunoRequestPostDTO;
import tech.vitalis.caringu.dtos.Aluno.AlunoResponsePatchDTO;
import tech.vitalis.caringu.dtos.Aluno.AlunoRespostaDTO;
import tech.vitalis.caringu.entity.Aluno;
import tech.vitalis.caringu.mapper.AlunoMapper;
import tech.vitalis.caringu.service.AlunoService;

import java.util.List;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

    private final AlunoService service;

    public AlunoController(AlunoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<AlunoRespostaDTO>> listar() {
        List<AlunoRespostaDTO> listaAlunos = service.listar();

        return ResponseEntity.status(200).body(listaAlunos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlunoRespostaDTO> buscarPorId(@PathVariable Integer id) {
        AlunoRespostaDTO aluno = service.buscarPorId(id);
        return ResponseEntity.ok(aluno);
    }

    @PostMapping
    public ResponseEntity<AlunoRespostaDTO> cadastrar(@Valid @RequestBody AlunoRequestPostDTO cadastroDTO) {
        Aluno aluno = AlunoMapper.toEntity(cadastroDTO);
        AlunoRespostaDTO respostaDTO = service.cadastrar(aluno);

        return ResponseEntity.status(201).body(respostaDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlunoRespostaDTO> atualizar(
            @PathVariable Integer id,
            @Valid @RequestBody AlunoRequestPostDTO dto) {

        Aluno novoAluno = AlunoMapper.toEntity(dto);
        AlunoRespostaDTO atualizado = service.atualizar(id, novoAluno);
        return ResponseEntity.ok(atualizado);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AlunoResponsePatchDTO> atualizarParcial(
            @PathVariable Integer id,
            @Valid @RequestBody AlunoRequestPatchDTO atualizacoes) {

        AlunoResponsePatchDTO atualizado = service.atualizarParcial(id, atualizacoes);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
