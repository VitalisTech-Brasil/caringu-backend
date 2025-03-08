package tech.vitalis.caringu.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.vitalis.caringu.dtos.Usuario.CriacaoUsuarioDTO;
import tech.vitalis.caringu.dtos.Usuario.RespostaUsuarioDTO;
import tech.vitalis.caringu.service.UsuarioService;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    @Operation(summary = "Cadastrar novo usuário")
    public ResponseEntity<RespostaUsuarioDTO> cadastrar(@RequestBody @Valid CriacaoUsuarioDTO usuarioDto) {
        RespostaUsuarioDTO usuarioCriado = usuarioService.cadastrar(usuarioDto);
        return ResponseEntity.status(201).body(usuarioCriado);
    }

    @GetMapping
    @Operation(summary = "Buscar lista de usuários")
    public ResponseEntity<List<RespostaUsuarioDTO>> listarTodos(){
        return ResponseEntity.ok(usuarioService.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuário por ID")
    public ResponseEntity<RespostaUsuarioDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar usuário")
    public ResponseEntity<RespostaUsuarioDTO> atualizar(@PathVariable Long id, @RequestBody @Valid CriacaoUsuarioDTO usuarioDto) {
        return ResponseEntity.ok(usuarioService.atualizar(id, usuarioDto));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Atualizar usuário parcialmente")
    public ResponseEntity<RespostaUsuarioDTO> editarInfoUsuario(@PathVariable Long id, @RequestBody CriacaoUsuarioDTO usuarioDto) {
        return ResponseEntity.ok(usuarioService.editarInfoUsuario(id, usuarioDto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover usuário")
    public ResponseEntity<Void> removerUsuario(@PathVariable Long id) {
        usuarioService.removerUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
