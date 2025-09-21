package tech.vitalis.caringu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import tech.vitalis.caringu.config.GerenciadorTokenJwt;
import tech.vitalis.caringu.dtos.Pessoa.PessoaResponseFotoPerfilGetDTO;
import tech.vitalis.caringu.dtos.Pessoa.PessoaResponseGetDTO;
import tech.vitalis.caringu.dtos.Pessoa.PessoaRequestPostDTO;
import tech.vitalis.caringu.dtos.Pessoa.security.ControleLogin;
import tech.vitalis.caringu.dtos.Pessoa.security.PessoaTokenDTO;
import tech.vitalis.caringu.exception.ApiExceptions;
import tech.vitalis.caringu.exception.Pessoa.EmailJaCadastradoException;
import tech.vitalis.caringu.exception.Pessoa.PessoaNaoEncontradaException;
import tech.vitalis.caringu.exception.Pessoa.SenhaInvalidaException;
import tech.vitalis.caringu.mapper.PessoaMapper;
import tech.vitalis.caringu.entity.Pessoa;
import tech.vitalis.caringu.repository.PessoaRepository;
import tech.vitalis.caringu.service.ArmazenamentoFotos.ArmazenamentoService;


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

    @Autowired
    private Environment env;

    private final ControleLogin controleLogin = new ControleLogin();

    private final PessoaRepository pessoaRepository;

    private final PessoaMapper pessoaMapper;
    private final ArmazenamentoService armazenamentoService;

    public PessoaService(PessoaRepository pessoaRepository, PessoaMapper pessoaMapper, ArmazenamentoService armazenamentoService) {
        this.pessoaRepository = pessoaRepository;
        this.pessoaMapper = pessoaMapper;
        this.armazenamentoService = armazenamentoService;
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

        return pessoaMapper.toDTO(pessoaSalva);
    }

    public PessoaTokenDTO autenticar(Pessoa pessoa) {

        if (controleLogin.validarBloqueio(pessoa.getEmail())) {
            long tempoRestante = controleLogin.tempoRestante(pessoa.getEmail());
            throw new ResponseStatusException(423, "Conta bloqueada. Tente novamente em " + tempoRestante + " segundos.", null);
        }

        final UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(pessoa.getEmail(), pessoa.getSenha());

        try {
            final Authentication authentication = this.authenticationManager.authenticate(credentials);

            controleLogin.registrarSucesso(pessoa.getEmail());

            Pessoa pessoaAutenticado =
                    pessoaRepository.findByEmail(pessoa.getEmail())
                            .orElseThrow(
                                    () -> new ResponseStatusException(404, "Pessoa não encontrada", null)
                            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            final String token = gerenciadorTokenJwt.generateToken(authentication);

            return pessoaMapper.of(pessoaAutenticado, token);

        } catch (AuthenticationException e) {

            boolean justLocked = controleLogin.registrarFalha(pessoa.getEmail());

            if (justLocked) {
                throw new ResponseStatusException(401, "5 tentativas falhas. Conta bloqueada por 15 minutos.", e);
            } else if (controleLogin.validarBloqueio(pessoa.getEmail())) {
                long timeLeft = controleLogin.tempoRestante(pessoa.getEmail());
                throw new ResponseStatusException(423, "Conta bloqueada. Tente novamente em " + timeLeft + " segundos.", e);
            } else {
                throw new ResponseStatusException(401, "Usuário ou senha inválidos.", e);
            }
        }
    }

    public Boolean emailExiste(String email) {
        return pessoaRepository.existsByEmail(email);
    }

    public List<PessoaResponseGetDTO> listarTodos() {
        List<Pessoa> pessoas = pessoaRepository.findAll();

        if (pessoas.isEmpty()) {
            throw new ApiExceptions.ResourceNotFoundException("Nenhum usuário encontrado.");
        }

        return pessoas.stream()
                .map(pessoaMapper::toDTO)
                .collect(Collectors.toList());
    }

    public PessoaResponseGetDTO buscarPorId(Integer id) {
        Pessoa pessoa = pessoaRepository.findById(id)
                .orElseThrow(() -> new ApiExceptions.ResourceNotFoundException("Usuário com ID " + id + " não encontrado"));
        return pessoaMapper.toDTO(pessoa);
    }

    public PessoaResponseGetDTO atualizar(Integer id, PessoaRequestPostDTO pessoaDto) {
        Pessoa pessoaExistente = pessoaRepository.findById(id)
                .orElseThrow(() -> new ApiExceptions.ResourceNotFoundException("Usuário com ID " + id + " não encontrado"));

        pessoaMapper.updatePessoaFromDto(pessoaDto, pessoaExistente);
        Pessoa pessoaAtualizado = pessoaRepository.save(pessoaExistente);
        return pessoaMapper.toDTO(pessoaAtualizado);
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
        return pessoaMapper.toDTO(pessoaAtualizado);
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

    public String uploadFotoPerfil(Integer id, MultipartFile arquivo) {
        Pessoa pessoa = pessoaRepository.findById(id)
                .orElseThrow(() -> new PessoaNaoEncontradaException("Pessoa não encontrada"));

        if (pessoa.getUrlFotoPerfil() != null) {
            armazenamentoService.deletarArquivoPorUrl(pessoa.getUrlFotoPerfil());
        }

        String url = armazenamentoService.uploadArquivo(arquivo);

        pessoa.setUrlFotoPerfil(url);
        pessoaRepository.save(pessoa);

        return url;
    }

    public void removerFotoPerfil(Integer id) {
        Pessoa pessoa = pessoaRepository.findById(id)
                .orElseThrow(() -> new PessoaNaoEncontradaException("Pessoa não encontrada"));

        if (pessoa.getUrlFotoPerfil() != null) {
            armazenamentoService.deletarArquivoPorUrl(pessoa.getUrlFotoPerfil());
            pessoa.setUrlFotoPerfil(null);
            pessoaRepository.save(pessoa);
        }
    }

    public PessoaResponseFotoPerfilGetDTO buscarFotoPerfilPorId(Integer id) {
        Pessoa pessoa = pessoaRepository.findById(id)
                .orElseThrow(() -> new PessoaNaoEncontradaException("Pessoa não encontrada com ID " + id));

        PessoaResponseFotoPerfilGetDTO dto = pessoaMapper.toFotoPerfilDTO(pessoa);

        String urlFinal;

        if (env.acceptsProfiles(Profiles.of("prod"))) {
            urlFinal = dto.urlFotoPerfil();
        } else {
            String nomeArquivo = dto.urlFotoPerfil();
            urlFinal = "http://localhost:8080/pessoas/fotos-perfil/" + nomeArquivo;
        }

        return new PessoaResponseFotoPerfilGetDTO(
                dto.id(),
                dto.nome(),
                dto.email(),
                urlFinal
        );
    }

}
