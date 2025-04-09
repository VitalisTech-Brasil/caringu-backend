package tech.vitalis.caringu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.vitalis.caringu.dtos.Usuario.CriacaoUsuarioDTO;
import tech.vitalis.caringu.dtos.Usuario.RespostaUsuarioDTO;
import tech.vitalis.caringu.exception.ApiExceptions;
import tech.vitalis.caringu.mapper.UsuarioMapper;
import tech.vitalis.caringu.entity.Usuario;
import tech.vitalis.caringu.repository.UsuarioRepository;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, UsuarioMapper usuarioMapper) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
    }

    public RespostaUsuarioDTO cadastrar(CriacaoUsuarioDTO usuarioDto) {
        if (usuarioRepository.existsByEmail(usuarioDto.getEmail())) {
            throw new ApiExceptions.ConflictException("O e-mail já está cadastrado.");
        }

        validarSenha(usuarioDto.getSenha());

        Usuario novoUsuario = usuarioMapper.toEntity(usuarioDto);
        Usuario usuarioSalvo = usuarioRepository.save(novoUsuario);
        return usuarioMapper.toDTO(usuarioSalvo);
    }

    public List<RespostaUsuarioDTO> listarTodos() {
        List<Usuario> usuarios = usuarioRepository.findAll();

        if (usuarios.isEmpty()) {
            throw new ApiExceptions.ResourceNotFoundException("Nenhum usuário encontrado.");
        }

        return usuarios.stream()
                .map(usuarioMapper::toDTO)
                .collect(Collectors.toList());
    }

    public RespostaUsuarioDTO buscarPorId(Integer id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ApiExceptions.ResourceNotFoundException("Usuário com ID " + id + " não encontrado"));
        return usuarioMapper.toDTO(usuario);
    }

    public RespostaUsuarioDTO atualizar(Integer id, CriacaoUsuarioDTO usuarioDto) {
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new ApiExceptions.ResourceNotFoundException("Usuário com ID " + id + " não encontrado"));

        usuarioMapper.updateUsuarioFromDto(usuarioDto, usuarioExistente);
        Usuario usuarioAtualizado = usuarioRepository.save(usuarioExistente);
        return usuarioMapper.toDTO(usuarioAtualizado);
    }

    public RespostaUsuarioDTO editarInfoUsuario(Integer id, CriacaoUsuarioDTO usuarioDto) {
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new ApiExceptions.ResourceNotFoundException("Usuário com ID " + id + " não encontrado"));

        boolean atualizado = false;

        if (usuarioDto.getNome() != null && !usuarioDto.getNome().isEmpty()) {
            usuarioExistente.setNome(usuarioDto.getNome());
            atualizado = true;
        }
        if (usuarioDto.getEmail() != null && !usuarioDto.getEmail().isEmpty()) {
            usuarioExistente.setEmail(usuarioDto.getEmail());
            atualizado = true;
        }
        if (usuarioDto.getSenha() != null && !usuarioDto.getSenha().isEmpty()) {
            validarSenha(usuarioDto.getSenha());
            usuarioExistente.setSenha(usuarioDto.getSenha());
            atualizado = true;
        }

        if (!atualizado) {
            throw new ApiExceptions.BadRequestException("Nenhuma informação válida foi fornecida para atualização.");
        }

        Usuario usuarioAtualizado = usuarioRepository.save(usuarioExistente);
        return usuarioMapper.toDTO(usuarioAtualizado);
    }

    public void removerUsuario(Integer id) {
        if (!usuarioRepository.existsById(id)) {
            throw new ApiExceptions.ResourceNotFoundException("Usuário com ID " + id + " não encontrado");
        }
        usuarioRepository.deleteById(id);
    }

    private void validarSenha(String senha) {
        String regex = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*()_+=\\-{};:'\",.<>?/|\\\\]).{6,16}$";

        if (!Pattern.matches(regex, senha)) {
            throw new ApiExceptions.BadRequestException("A senha deve conter entre 6 e 16 caracteres, incluindo pelo menos uma letra maiúscula, um número e um caractere especial.");
        }
    }
}
