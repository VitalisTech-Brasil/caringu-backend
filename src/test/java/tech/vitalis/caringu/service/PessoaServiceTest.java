package tech.vitalis.caringu.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;
import tech.vitalis.caringu.config.GerenciadorTokenJwt;
import tech.vitalis.caringu.dtos.Pessoa.PessoaRequestPostDTO;
import tech.vitalis.caringu.dtos.Pessoa.PessoaResponseFotoPerfilGetDTO;
import tech.vitalis.caringu.dtos.Pessoa.PessoaResponseGetDTO;
import tech.vitalis.caringu.dtos.Pessoa.security.PessoaTokenDTO;
import tech.vitalis.caringu.dtos.Pessoa.security.strategy.ControleLogin;
import tech.vitalis.caringu.entity.Pessoa;
import tech.vitalis.caringu.exception.ApiExceptions;
import tech.vitalis.caringu.exception.Pessoa.ContaBloqueadaException;
import tech.vitalis.caringu.exception.Pessoa.EmailJaCadastradoException;
import tech.vitalis.caringu.exception.Pessoa.SenhaInvalidaException;
import tech.vitalis.caringu.mapper.PessoaMapper;
import tech.vitalis.caringu.repository.PessoaRepository;
import tech.vitalis.caringu.service.ArmazenamentoFotos.ArmazenamentoService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PessoaServiceTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private GerenciadorTokenJwt gerenciadorTokenJwt;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private Environment env;

    @Mock
    private ControleLogin controleLogin;

    @Mock
    private PessoaRepository pessoaRepository;

    @Mock
    private PessoaMapper pessoaMapper;

    @Mock
    private ArmazenamentoService armazenamentoService;

    @InjectMocks
    private PessoaService pessoaService;

    private Pessoa pessoa;

    @BeforeEach
    void setUp() {
        pessoa = new Pessoa();
        pessoa.setId(1);
        pessoa.setNome("Usuário Teste");
        pessoa.setEmail("teste@exemplo.com");
        pessoa.setSenha("Senha@123");
    }

    @Test
    @DisplayName("cadastrar deve salvar pessoa com senha criptografada")
    void cadastrar_ComSucesso() {
        when(pessoaRepository.existsByEmail(pessoa.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(pessoa.getSenha())).thenReturn("SenhaCriptografada");
        when(pessoaRepository.save(pessoa)).thenReturn(pessoa);

        PessoaResponseGetDTO dtoResposta = mock(PessoaResponseGetDTO.class);
        when(pessoaMapper.toDTO(pessoa)).thenReturn(dtoResposta);

        PessoaResponseGetDTO resultado = pessoaService.cadastrar(pessoa);

        assertNotNull(resultado);
        assertEquals("SenhaCriptografada", pessoa.getSenha());
        verify(passwordEncoder).encode("Senha@123");
        verify(pessoaRepository).save(pessoa);
        verify(pessoaMapper).toDTO(pessoa);
    }

    @Test
    @DisplayName("cadastrar deve lançar exceção para senha fraca")
    void cadastrar_SenhaFraca() {
        pessoa.setSenha("fraca");

        assertThrows(SenhaInvalidaException.class,
                () -> pessoaService.cadastrar(pessoa));

        verifyNoInteractions(passwordEncoder);
        verify(pessoaRepository, never()).save(any());
    }

    @Test
    @DisplayName("cadastrar deve lançar exceção quando email já existe")
    void cadastrar_EmailJaExiste() {
        when(pessoaRepository.existsByEmail(pessoa.getEmail())).thenReturn(true);

        assertThrows(EmailJaCadastradoException.class,
                () -> pessoaService.cadastrar(pessoa));

        verify(pessoaRepository).existsByEmail(pessoa.getEmail());
        verify(pessoaRepository, never()).save(any());
    }

    @Test
    @DisplayName("autenticar deve retornar token quando credenciais são válidas")
    void autenticar_ComSucesso() {
        when(controleLogin.validarBloqueio(pessoa.getEmail())).thenReturn(false);

        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);

        when(pessoaRepository.findByEmail(pessoa.getEmail()))
                .thenReturn(Optional.of(pessoa));
        when(gerenciadorTokenJwt.generateToken(authentication)).thenReturn("token-jwt");

        PessoaTokenDTO tokenDTO = pessoaService.autenticar(pessoa);

        assertNotNull(tokenDTO);
        verify(controleLogin).registrarSucesso(pessoa.getEmail());
    }

    @Test
    @DisplayName("autenticar deve lançar ContaBloqueadaException quando conta estiver bloqueada")
    void autenticar_ContaBloqueada() {
        when(controleLogin.validarBloqueio(pessoa.getEmail())).thenReturn(true);
        when(controleLogin.tempoRestante(pessoa.getEmail())).thenReturn(60L);

        assertThrows(ContaBloqueadaException.class,
                () -> pessoaService.autenticar(pessoa));
    }

    @Test
    @DisplayName("autenticar deve tratar credenciais inválidas e bloqueio após falhas")
    void autenticar_CredenciaisInvalidas() {
        when(controleLogin.validarBloqueio(pessoa.getEmail())).thenReturn(false);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("invalid"));

        when(controleLogin.registrarFalha(pessoa.getEmail())).thenReturn(false);
        when(controleLogin.validarBloqueio(pessoa.getEmail())).thenReturn(false);

        assertThrows(ResponseStatusException.class,
                () -> pessoaService.autenticar(pessoa));
    }

    @Test
    @DisplayName("listarTodos deve lançar ResourceNotFoundException quando lista vazia")
    void listarTodos_SemUsuarios() {
        when(pessoaRepository.findAll()).thenReturn(List.of());

        assertThrows(ApiExceptions.ResourceNotFoundException.class,
                () -> pessoaService.listarTodos());
    }

    @Test
    @DisplayName("buscarPorId deve retornar DTO quando existir")
    void buscarPorId_ComSucesso() {
        when(pessoaRepository.findById(1)).thenReturn(Optional.of(pessoa));
        PessoaResponseGetDTO dto = mock(PessoaResponseGetDTO.class);
        when(pessoaMapper.toDTO(pessoa)).thenReturn(dto);

        PessoaResponseGetDTO resultado = pessoaService.buscarPorId(1);

        assertNotNull(resultado);
        verify(pessoaRepository).findById(1);
        verify(pessoaMapper).toDTO(pessoa);
    }

    @Test
    @DisplayName("buscarPorId deve lançar ResourceNotFoundException quando não existir")
    void buscarPorId_NaoEncontrado() {
        when(pessoaRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ApiExceptions.ResourceNotFoundException.class,
                () -> pessoaService.buscarPorId(1));
    }

    @Test
    @DisplayName("editarInfoPessoa deve lançar BadRequest quando nenhum campo válido for enviado")
    void editarInfoPessoa_SemCamposValidos() {
        when(pessoaRepository.findById(1)).thenReturn(Optional.of(pessoa));

        PessoaRequestPostDTO dto = new PessoaRequestPostDTO(
                null, null, null, null, null, null, null
        );

        assertThrows(ApiExceptions.BadRequestException.class,
                () -> pessoaService.editarInfoPessoa(1, dto));
    }

    @Test
    @DisplayName("removerPessoa deve deletar quando existir")
    void removerPessoa_ComSucesso() {
        when(pessoaRepository.existsById(1)).thenReturn(true);

        pessoaService.removerPessoa(1);

        verify(pessoaRepository).deleteById(1);
    }

    @Test
    @DisplayName("removerPessoa deve lançar ResourceNotFoundException quando não existir")
    void removerPessoa_NaoEncontrado() {
        when(pessoaRepository.existsById(1)).thenReturn(false);

        assertThrows(ApiExceptions.ResourceNotFoundException.class,
                () -> pessoaService.removerPessoa(1));
    }

    @Test
    @DisplayName("buscarFotoPerfilPorId deve montar URL correta para perfil dev")
    void buscarFotoPerfilPorId_DevProfile() {
        pessoa.setUrlFotoPerfil("foto.jpg");
        when(pessoaRepository.findById(1)).thenReturn(Optional.of(pessoa));

        PessoaResponseFotoPerfilGetDTO dtoBase = new PessoaResponseFotoPerfilGetDTO(
                1, "Usuário Teste", "teste@exemplo.com", "foto.jpg"
        );
        when(pessoaMapper.toFotoPerfilDTO(pessoa)).thenReturn(dtoBase);

        when(env.acceptsProfiles(Profiles.of("prod"))).thenReturn(false);
        when(env.acceptsProfiles(Profiles.of("dev", "dev-with-redis"))).thenReturn(true);

        PessoaResponseFotoPerfilGetDTO resultado =
                pessoaService.buscarFotoPerfilPorId(1);

        assertTrue(resultado.urlFotoPerfil().contains("http://localhost:8080/pessoas/fotos-perfil/"));
    }
}


