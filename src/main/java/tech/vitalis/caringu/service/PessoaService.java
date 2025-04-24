package tech.vitalis.caringu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import tech.vitalis.caringu.config.GerenciadorTokenJwt;
import tech.vitalis.caringu.dtos.Pessoa.PessoaResponseGetDTO;
import tech.vitalis.caringu.dtos.Pessoa.PessoaRequestPostDTO;
import tech.vitalis.caringu.dtos.Pessoa.security.PessoaTokenDTO;
import tech.vitalis.caringu.exception.ApiExceptions;
import tech.vitalis.caringu.exception.Pessoa.EmailJaCadastradoException;
import tech.vitalis.caringu.exception.Pessoa.SenhaInvalidaException;
import tech.vitalis.caringu.mapper.PessoaMapper;
import tech.vitalis.caringu.entity.Pessoa;
import tech.vitalis.caringu.repository.PessoaRepository;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class PessoaService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private GerenciadorTokenJwt gerenciadorTokenJwt;

    @Autowired
    private AuthenticationManager authenticationManager;

    private final PessoaRepository pessoaRepository;

    public PessoaService(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    public PessoaResponseGetDTO cadastrar(Pessoa pessoa) {

        String regex = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*()_+=\\-{};:'\",.<>?/|\\\\]).{6,16}$";

        if (!Pattern.matches(regex, pessoa.getSenha())) {
            throw new SenhaInvalidaException("A senha incluir pelo menos uma letra maiúscula, um número e um caractere especial.");
        }

        if (pessoaRepository.existsByEmail(pessoa.getEmail())) {
            throw new EmailJaCadastradoException("O e-mail já está cadastrado.");
        }

        String senhaCriptografada = passwordEncoder.encode(pessoa.getSenha());

        pessoa.setSenha(senhaCriptografada);
        Pessoa pessoaSalva = pessoaRepository.save(pessoa);

        return PessoaMapper.toDTO(pessoaSalva);
    }

    public PessoaTokenDTO autenticar(Pessoa pessoa) {

        final UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(
                pessoa.getEmail(), pessoa.getSenha());

        final Authentication authentication = this.authenticationManager.authenticate(credentials);

        Pessoa pessoaAutenticado =
                pessoaRepository.findByEmail(pessoa.getEmail())
                        .orElseThrow(
                                () -> new ResponseStatusException(404, "Pessoa não encontrada", null)
                        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String token = gerenciadorTokenJwt.generateToken(authentication);

        return PessoaMapper.of(pessoaAutenticado, token);
    }

    public List<PessoaResponseGetDTO> listarTodos() {
        List<Pessoa> pessoas = pessoaRepository.findAll();

        if (pessoas.isEmpty()) {
            throw new ApiExceptions.ResourceNotFoundException("Nenhum usuário encontrado.");
        }

        return pessoas.stream()
                .map(PessoaMapper::toDTO)
                .collect(Collectors.toList());
    }

    public PessoaResponseGetDTO buscarPorId(Integer id) {
        Pessoa pessoa = pessoaRepository.findById(id)
                .orElseThrow(() -> new ApiExceptions.ResourceNotFoundException("Usuário com ID " + id + " não encontrado"));
        return PessoaMapper.toDTO(pessoa);
    }

    public PessoaResponseGetDTO atualizar(Integer id, PessoaRequestPostDTO pessoaDto) {
        Pessoa pessoaExistente = pessoaRepository.findById(id)
                .orElseThrow(() -> new ApiExceptions.ResourceNotFoundException("Usuário com ID " + id + " não encontrado"));

        PessoaMapper.updatePessoaFromDto(pessoaDto, pessoaExistente);
        Pessoa pessoaAtualizado = pessoaRepository.save(pessoaExistente);
        return PessoaMapper.toDTO(pessoaAtualizado);
    }

    public PessoaResponseGetDTO editarInfoPessoa(Integer id, PessoaRequestPostDTO pessoaDto) {
        Pessoa pessoaExistente = pessoaRepository.findById(id)
                .orElseThrow(() -> new ApiExceptions.ResourceNotFoundException("Usuário com ID " + id + " não encontrado"));

        boolean atualizado = false;

        if (pessoaDto.nome() != null && !pessoaDto.nome().isEmpty()) {
            pessoaExistente.setNome(pessoaDto.nome());
            atualizado = true;
        }
        if (pessoaDto.email() != null && !pessoaDto.email().isEmpty()) {
            pessoaExistente.setEmail(pessoaDto.email());
            atualizado = true;
        }
        if (pessoaDto.senha() != null && !pessoaDto.senha().isEmpty()) {
            validarSenha(pessoaDto.senha());
            pessoaExistente.setSenha(pessoaDto.senha());
            atualizado = true;
        }

        if (!atualizado) {
            throw new ApiExceptions.BadRequestException("Nenhuma informação válida foi fornecida para atualização.");
        }

        Pessoa pessoaAtualizado = pessoaRepository.save(pessoaExistente);
        return PessoaMapper.toDTO(pessoaAtualizado);
    }

    public void removerPessoa(Integer id) {
        if (!pessoaRepository.existsById(id)) {
            throw new ApiExceptions.ResourceNotFoundException("Usuário com ID " + id + " não encontrado");
        }
        pessoaRepository.deleteById(id);
    }

    private void validarSenha(String senha) {
        String regex = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*()_+=\\-{};:'\",.<>?/|\\\\])$";

        if (!Pattern.matches(regex, senha)) {
            throw new ApiExceptions.BadRequestException("A senha incluir pelo menos uma letra maiúscula, um número e um caractere especial.");
        }
    }
}
