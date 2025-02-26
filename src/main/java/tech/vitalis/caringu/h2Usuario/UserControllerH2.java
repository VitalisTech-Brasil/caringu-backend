package tech.vitalis.caringu.h2Usuario;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.vitalis.caringu.h2Usuario.dtos.CriacaoUsuarioDTO;
import tech.vitalis.caringu.h2Usuario.dtos.RespostaUsuarioDTO;

import java.util.Optional;

@RequestMapping("/crud")
@RestController
public class UserControllerH2 {
    @Autowired
    private UserRepository usuarioRepository;

    @PostMapping
    // TODO implementar nos outros métodos
    @Operation(description = "Cadastro de usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Caso dê erro 400, foi uma falha na validação do body, ou seja, o usuário enviado é inválido.")
    })


    public ResponseEntity<Usuario> cadastrar(
            @RequestBody @Valid CriacaoUsuarioDTO usuarioDto
    ) {
        Usuario usuarioCriado = usuarioRepository.save(new Usuario(usuarioDto));
        // cria um usuário Usuario com base no DTO por meio do construtor de Usuario

        return ResponseEntity.status(201).body(usuarioCriado);
        // Retornando o status 201 (created) indicando que o usuário foi criado e o usuário em si.
    }

    @GetMapping("/{id}")
    public ResponseEntity<RespostaUsuarioDTO> buscarPorId(
            @PathVariable Integer id
    ) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);

        if (usuarioOptional.isEmpty()) {
            return ResponseEntity.status(404).build();
            // retornando 404 (not found) indicando que nao foi encontrado usuario com o ID indicado e houve um erro.
        }
        // colocar no controllerAdvice
        RespostaUsuarioDTO usuarioDTO = new RespostaUsuarioDTO(usuarioOptional.get());

        return ResponseEntity.status(200).body(usuarioDTO);
        // retornando 200 (ok) e o conteúdo solicitado.

    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> editarUsuario(
            @PathVariable Integer id,
            @RequestBody Usuario informacoesDoUsuario
    ) {
        if (!usuarioRepository.existsById(id)) {
            return ResponseEntity.status(404).build();
        }
        informacoesDoUsuario.setId(id);
        Usuario usuarioAtualizado = usuarioRepository.save(informacoesDoUsuario);

        return ResponseEntity.status(200).body(usuarioAtualizado);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Usuario> editarInfoUsuario(
            @PathVariable Integer id,
            @RequestBody Usuario informacaoDoUsuario
    ) {

        if (!usuarioRepository.existsById(id)) {
            return ResponseEntity.status(404).build();
        }

        informacaoDoUsuario.setId(id);

        Usuario usuario = usuarioRepository.findById(id).get();

        if (informacaoDoUsuario.getNome() != null) {
            usuario.setNome(informacaoDoUsuario.getNome());
        }

        if (informacaoDoUsuario.getEmail() != null) {
            usuario.setEmail(informacaoDoUsuario.getEmail());
        }

        if (informacaoDoUsuario.getSenha() != null) {
            usuario.setSenha(informacaoDoUsuario.getSenha());
        }

        Usuario usuarioAtualizado = usuarioRepository.save(usuario);
        return ResponseEntity.status(200).body(usuarioAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerUsuario(
            @PathVariable Integer id
    ) {
        if (!usuarioRepository.existsById(id)) {
            return ResponseEntity.status(404).build();
        }

        usuarioRepository.deleteById(id);
        return ResponseEntity.status(204).build();
        //Retornando o status 204 (no content) indicando que o usuário foi deletado com sucesso e nao retorna conteúdo.
    }
}
