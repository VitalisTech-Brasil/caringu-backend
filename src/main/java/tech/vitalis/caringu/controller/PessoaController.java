package tech.vitalis.caringu.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.vitalis.caringu.dtos.Pessoa.PessoaRequestPostDTO;
import tech.vitalis.caringu.dtos.Pessoa.PessoaResponseGetDTO;
import tech.vitalis.caringu.service.PessoaService;

import java.util.List;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

    private final PessoaService pessoaService;

    public PessoaController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    @PostMapping
    @Operation(summary = "Cadastrar novo usuário")
    public ResponseEntity<PessoaResponseGetDTO> cadastrar(@Valid @RequestBody PessoaRequestPostDTO pessoaDto) {
        PessoaResponseGetDTO pessoaCriada = pessoaService.cadastrar(pessoaDto);
        return ResponseEntity.status(201).body(pessoaCriada);
    }

    @GetMapping
    @Operation(summary = "Buscar lista de pessoa")
    public ResponseEntity<List<PessoaResponseGetDTO>> listarTodos(){
        return ResponseEntity.ok(pessoaService.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar pessoa por ID")
    public ResponseEntity<PessoaResponseGetDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(pessoaService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar pessoa")
    public ResponseEntity<PessoaResponseGetDTO> atualizar(@PathVariable Integer id, @RequestBody @Valid PessoaRequestPostDTO pessoaDto) {
        return ResponseEntity.ok(pessoaService.atualizar(id, pessoaDto));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Atualizar pessoa parcialmente")
    public ResponseEntity<PessoaResponseGetDTO> editarInfoPessoa(@PathVariable Integer id, @RequestBody PessoaRequestPostDTO pessoaDto) {
        return ResponseEntity.ok(pessoaService.editarInfoPessoa(id, pessoaDto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover pessoa")
    public ResponseEntity<Void> removerPessoa(@PathVariable Integer id) {
        pessoaService.removerPessoa(id);
        return ResponseEntity.noContent().build();
    }
}
