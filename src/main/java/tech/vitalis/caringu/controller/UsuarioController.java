package tech.vitalis.caringu.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.vitalis.caringu.model.Usuario;
import tech.vitalis.caringu.service.UsuarioService;
import tech.vitalis.caringu.dtos.CriacaoUsuarioDTO;
import tech.vitalis.caringu.dtos.RespostaUsuarioDTO;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    @Operation(description = "Cadastro de usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Falha na validação do body. O usuário enviado é inválido.")
    })
    public ResponseEntity<Usuario> cadastrar(@RequestBody @Valid CriacaoUsuarioDTO usuarioDto) {
        return ResponseEntity.status(201).body(usuarioService.cadastrar(usuarioDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RespostaUsuarioDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(usuarioService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> editarUsuario(@PathVariable Integer id, @RequestBody Usuario informacoesDoUsuario) {
        return ResponseEntity.ok(usuarioService.editarUsuario(id, informacoesDoUsuario));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Usuario> editarInfoUsuario(@PathVariable Integer id, @RequestBody Usuario informacaoDoUsuario) {
        return ResponseEntity.ok(usuarioService.editarInfoUsuario(id, informacaoDoUsuario));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerUsuario(@PathVariable Integer id) {
        usuarioService.removerUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
