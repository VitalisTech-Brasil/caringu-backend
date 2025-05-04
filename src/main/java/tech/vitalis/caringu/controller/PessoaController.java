package tech.vitalis.caringu.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tech.vitalis.caringu.dtos.Pessoa.PessoaRequestPostDTO;
import tech.vitalis.caringu.dtos.Pessoa.PessoaResponseFotoPerfilGetDTO;
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

    // ---------- GETs ----------
    @GetMapping
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Listar todas as pessoas")
    public ResponseEntity<List<PessoaResponseGetDTO>> listarTodos() {
        return ResponseEntity.ok(pessoaService.listarTodos());
    }

    @GetMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Buscar pessoa por ID")
    public ResponseEntity<PessoaResponseGetDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(pessoaService.buscarPorId(id));
    }

    @GetMapping("/{id}/foto-perfil")
    @Operation(summary = "Buscar nome, email e URL da foto de perfil por ID")
    public ResponseEntity<PessoaResponseFotoPerfilGetDTO> buscarFotoPerfilPorId(@PathVariable Integer id) {
        PessoaResponseFotoPerfilGetDTO dto = pessoaService.buscarFotoPerfilPorId(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/verificacao-email")
    @Operation(summary = "Verificar existência de e-mail cadastrado")
    public ResponseEntity<Boolean> verificarEmail(@RequestParam String email) {
        boolean existe = pessoaService.emailExiste(email);
        return ResponseEntity.ok(existe);
    }

    // ---------- POST ----------
    @PostMapping("/{id}/upload-foto-perfil")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Fazer upload da foto de perfil da pessoa")
    public ResponseEntity<String> uploadFotoPerfil(
            @PathVariable Integer id,
            @RequestParam("arquivo") MultipartFile arquivo
    ) {
        String url = pessoaService.uploadFotoPerfil(id, arquivo);
        return ResponseEntity.ok(url);
    }

    // ---------- PUT ----------
    @PutMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Atualizar todos os dados da pessoa")
    public ResponseEntity<PessoaResponseGetDTO> atualizar(
            @PathVariable Integer id,
            @RequestBody @Valid PessoaRequestPostDTO pessoaDto
    ) {
        return ResponseEntity.ok(pessoaService.atualizar(id, pessoaDto));
    }

    // ---------- PATCH ----------
    @PatchMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Atualizar parcialmente os dados da pessoa")
    public ResponseEntity<PessoaResponseGetDTO> editarInfoPessoa(
            @PathVariable Integer id,
            @RequestBody PessoaRequestPostDTO pessoaDto
    ) {
        return ResponseEntity.ok(pessoaService.editarInfoPessoa(id, pessoaDto));
    }

    // ---------- DELETE ----------
    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Remover pessoa por ID")
    public ResponseEntity<Void> removerPessoa(@PathVariable Integer id) {
        pessoaService.removerPessoa(id);
        return ResponseEntity.noContent().build();
    }
}