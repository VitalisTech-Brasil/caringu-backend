package tech.vitalis.caringu.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import tech.vitalis.caringu.dtos.PersonalTrainer.PersonalTrainerDisponivelResponseDTO;
import tech.vitalis.caringu.dtos.PersonalTrainer.PersonalTrainerInfoBasicaDTO;
import tech.vitalis.caringu.dtos.PersonalTrainer.PersonalTrainerResponseGetDTO;
import tech.vitalis.caringu.dtos.Plano.PlanoResumoDTO;
import tech.vitalis.caringu.entity.PersonalTrainer;
import tech.vitalis.caringu.enums.PeriodoEnum;
import tech.vitalis.caringu.enums.Pessoa.GeneroEnum;
import tech.vitalis.caringu.exception.PersonalTrainer.CrefJaExisteException;
import tech.vitalis.caringu.exception.PersonalTrainer.PersonalNaoEncontradoException;
import tech.vitalis.caringu.exception.Pessoa.EmailJaCadastradoException;
import tech.vitalis.caringu.exception.Pessoa.SenhaInvalidaException;
import tech.vitalis.caringu.mapper.PersonalTrainerMapper;
import tech.vitalis.caringu.repository.EspecialidadeRepository;
import tech.vitalis.caringu.repository.PersonalTrainerRepository;
import tech.vitalis.caringu.repository.PessoaRepository;
import tech.vitalis.caringu.repository.PlanoRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonalTrainerServiceTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private PessoaRepository pessoaRepository;

    @Mock
    private PersonalTrainerMapper personalTrainerMapper;

    @Mock
    private PersonalTrainerRepository personalTrainerRepository;

    @Mock
    private EspecialidadeRepository especialidadeRepository;

    @Mock
    private PreferenciaNotificacaoService preferenciaNotificacaoService;

    @Mock
    private PlanoRepository planoRepository;

    @InjectMocks
    private PersonalTrainerService personalTrainerService;

    private PersonalTrainer personal;

    @BeforeEach
    void setUp() {
        personal = new PersonalTrainer();
        personal.setId(1);
        personal.setNome("Personal Teste");
        personal.setEmail("personal@exemplo.com");
        personal.setSenha("Senha@123");
        personal.setGenero(GeneroEnum.HOMEM_CISGENERO);
        personal.setCref("CREF123");
        personal.setExperiencia(5);
    }

    @Test
    @DisplayName("cadastrar deve salvar personal com senha criptografada e preferências padrão")
    void cadastrar_ComSucesso() {
        when(pessoaRepository.existsByEmail(personal.getEmail())).thenReturn(false);
        when(personalTrainerRepository.existsByCref(personal.getCref())).thenReturn(false);
        when(passwordEncoder.encode(personal.getSenha())).thenReturn("SenhaCriptografada");
        when(personalTrainerRepository.save(any(PersonalTrainer.class))).thenReturn(personal);

        PersonalTrainerResponseGetDTO respostaMock = mock(PersonalTrainerResponseGetDTO.class);
        when(personalTrainerMapper.toResponseDTO(personal)).thenReturn(respostaMock);

        PersonalTrainerResponseGetDTO resultado = personalTrainerService.cadastrar(personal);

        assertNotNull(resultado);
        assertEquals("SenhaCriptografada", personal.getSenha());
        verify(passwordEncoder).encode("Senha@123");
        verify(personalTrainerRepository).save(personal);
        verify(preferenciaNotificacaoService).criarPreferenciasPadrao(personal);
        verify(personalTrainerMapper).toResponseDTO(personal);
    }

    @Test
    @DisplayName("cadastrar deve lançar exceção quando senha for fraca")
    void cadastrar_SenhaFraca() {
        personal.setSenha("fraca");

        assertThrows(SenhaInvalidaException.class, () -> personalTrainerService.cadastrar(personal));

        verifyNoInteractions(passwordEncoder);
        verify(personalTrainerRepository, never()).save(any());
    }

    @Test
    @DisplayName("cadastrar deve lançar exceção quando email já existe")
    void cadastrar_EmailJaExiste() {
        when(pessoaRepository.existsByEmail(personal.getEmail())).thenReturn(true);

        assertThrows(EmailJaCadastradoException.class, () -> personalTrainerService.cadastrar(personal));

        verify(pessoaRepository).existsByEmail(personal.getEmail());
        verify(personalTrainerRepository, never()).save(any());
    }

    @Test
    @DisplayName("cadastrar deve lançar exceção quando CREF já existe")
    void cadastrar_CrefJaExiste() {
        when(pessoaRepository.existsByEmail(personal.getEmail())).thenReturn(false);
        when(personalTrainerRepository.existsByCref(personal.getCref())).thenReturn(true);

        assertThrows(CrefJaExisteException.class, () -> personalTrainerService.cadastrar(personal));

        verify(personalTrainerRepository).existsByCref(personal.getCref());
        verify(personalTrainerRepository, never()).save(any());
    }

    @Test
    @DisplayName("buscarPorId deve retornar DTO quando personal existir")
    void buscarPorId_ComSucesso() {
        when(personalTrainerRepository.findById(1)).thenReturn(Optional.of(personal));
        PersonalTrainerResponseGetDTO respostaMock = mock(PersonalTrainerResponseGetDTO.class);
        when(personalTrainerMapper.toResponseDTO(personal)).thenReturn(respostaMock);

        PersonalTrainerResponseGetDTO resultado = personalTrainerService.buscarPorId(1);

        assertNotNull(resultado);
        verify(personalTrainerRepository).findById(1);
        verify(personalTrainerMapper).toResponseDTO(personal);
    }

    @Test
    @DisplayName("buscarPorId deve lançar exceção quando personal não existir")
    void buscarPorId_PersonalNaoEncontrado() {
        when(personalTrainerRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(PersonalNaoEncontradoException.class, () -> personalTrainerService.buscarPorId(1));
    }

    @Test
    @DisplayName("listar deve retornar lista de DTOs")
    void listar_DeveRetornarLista() {
        when(personalTrainerRepository.findAll()).thenReturn(List.of(personal));
        PersonalTrainerResponseGetDTO respostaMock = mock(PersonalTrainerResponseGetDTO.class);
        when(personalTrainerMapper.toResponseDTO(personal)).thenReturn(respostaMock);

        List<PersonalTrainerResponseGetDTO> resultado = personalTrainerService.listar();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(personalTrainerRepository).findAll();
        verify(personalTrainerMapper).toResponseDTO(personal);
    }

    @Test
    @DisplayName("deletar deve remover personal existente")
    void deletar_ComSucesso() {
        when(personalTrainerRepository.findById(1)).thenReturn(Optional.of(personal));

        personalTrainerService.deletar(1);

        verify(personalTrainerRepository).delete(personal);
    }

    @Test
    @DisplayName("deletar deve lançar exceção quando personal não existir")
    void deletar_PersonalNaoEncontrado() {
        when(personalTrainerRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(PersonalNaoEncontradoException.class, () -> personalTrainerService.deletar(1));
    }

    @Test
    @DisplayName("listarPersonaisDisponiveis deve montar resposta com especialidades e planos")
    void listarPersonaisDisponiveis_ComSucesso() {
        PersonalTrainerInfoBasicaDTO basico = new PersonalTrainerInfoBasicaDTO(
                1,
                "Personal Teste",
                "personal@exemplo.com",
                "11999999999",
                "https://example.com/foto.jpg",
                GeneroEnum.HOMEM_CISGENERO,
                5,
                "Bairro X",
                "Cidade Y",
                4.5,
                10L
        );

        when(personalTrainerRepository.buscarBasicos()).thenReturn(List.of(basico));

        when(planoRepository.findResumoByPersonalIds(List.of(1)))
                .thenReturn(List.of(new PlanoResumoDTO(1, 1, "Plano Básico", PeriodoEnum.MENSAL, 1, 99.9)));

        PersonalTrainerDisponivelResponseDTO respostaMock = mock(PersonalTrainerDisponivelResponseDTO.class);
        when(personalTrainerMapper.toResponse(any(PersonalTrainerInfoBasicaDTO.class), anyList(), anyList()))
                .thenReturn(respostaMock);

        List<PersonalTrainerDisponivelResponseDTO> resultado = personalTrainerService.listarPersonaisDisponiveis(null);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(personalTrainerRepository).buscarBasicos();
        verify(personalTrainerMapper).toResponse(
                any(PersonalTrainerInfoBasicaDTO.class),
                anyList(),
                anyList()
        );
    }
}


