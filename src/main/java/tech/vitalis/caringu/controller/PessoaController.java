package tech.vitalis.caringu.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.vitalis.caringu.dtos.Pessoa.CriacaoPessoaDTO;
import tech.vitalis.caringu.dtos.Pessoa.RespostaPessoaDTO;
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
    public ResponseEntity<RespostaPessoaDTO> cadastrar(@Valid @RequestBody CriacaoPessoaDTO pessoaDto) {
        RespostaPessoaDTO pessoaCriada = pessoaService.cadastrar(pessoaDto);
        return ResponseEntity.status(201).body(pessoaCriada);
    }

    @GetMapping
    @Operation(summary = "Buscar lista de pessoa")
    public ResponseEntity<List<RespostaPessoaDTO>> listarTodos(){
        return ResponseEntity.ok(pessoaService.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar pessoa por ID")
    public ResponseEntity<RespostaPessoaDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(pessoaService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar pessoa")
    public ResponseEntity<RespostaPessoaDTO> atualizar(@PathVariable Integer id, @RequestBody @Valid CriacaoPessoaDTO pessoaDto) {
        return ResponseEntity.ok(pessoaService.atualizar(id, pessoaDto));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Atualizar pessoa parcialmente")
    public ResponseEntity<RespostaPessoaDTO> editarInfoPessoa(@PathVariable Integer id, @RequestBody CriacaoPessoaDTO pessoaDto) {
        return ResponseEntity.ok(pessoaService.editarInfoPessoa(id, pessoaDto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover pessoa")
    public ResponseEntity<Void> removerPessoa(@PathVariable Integer id) {
        pessoaService.removerPessoa(id);
        return ResponseEntity.noContent().build();
    }
}
