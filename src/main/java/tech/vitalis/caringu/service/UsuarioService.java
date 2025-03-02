package tech.vitalis.caringu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.vitalis.caringu.exception.ResourceNotFoundException;
import tech.vitalis.caringu.dtos.CriacaoUsuarioDTO;
import tech.vitalis.caringu.dtos.RespostaUsuarioDTO;
import tech.vitalis.caringu.model.Usuario;
import tech.vitalis.caringu.repository.UsuarioRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario cadastrar(CriacaoUsuarioDTO usuarioDto) {
        validarSenha(usuarioDto.getSenha());

        return usuarioRepository.save(new Usuario(usuarioDto));
    }

    public void validarSenha(String senha){

        List<String> erros = new ArrayList<>();
        String regex = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*()_+=\\-{};:'\",.<>?/|\\\\]).{1,16}$";

        if(senha.length() > 16){
            erros.add("A senha não pode ter mais que 16 caracteres.");
        } else if (senha.length() < 6) {
            erros.add("A senha deve ter mais que 6 caracteres.");
        }else if(!Pattern.matches(regex, senha)){
            erros.add("A senha deve conter ao menos um caracter especial, uma letra maíscula e um número");
        }

        if(!erros.isEmpty()){
            throw new IllegalArgumentException(String.join("", erros));
        }
    }

    public RespostaUsuarioDTO buscarPorId(Integer id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário com ID " + id + " não encontrado"));

        return new RespostaUsuarioDTO(usuario);
    }

    public Usuario editarUsuario(Integer id, Usuario informacoesDoUsuario) {
        if (!usuarioRepository.existsById(id)) {
            throw new ResourceNotFoundException("Usuário com ID " + id + " não encontrado");
        }
        informacoesDoUsuario.setId(id);
        return usuarioRepository.save(informacoesDoUsuario);
    }

    public Usuario editarInfoUsuario(Integer id, Usuario informacaoDoUsuario) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário com ID " + id + " não encontrado"));

        if (informacaoDoUsuario.getNome() != null) {
            usuario.setNome(informacaoDoUsuario.getNome());
        }
        if (informacaoDoUsuario.getEmail() != null) {
            usuario.setEmail(informacaoDoUsuario.getEmail());
        }
        if (informacaoDoUsuario.getSenha() != null) {
            usuario.setSenha(informacaoDoUsuario.getSenha());
        }

        return usuarioRepository.save(usuario);
    }

    public void removerUsuario(Integer id) {
        if (!usuarioRepository.existsById(id)) {
            throw new ResourceNotFoundException("Usuário com ID " + id + " não encontrado");
        }
        usuarioRepository.deleteById(id);
    }
}
