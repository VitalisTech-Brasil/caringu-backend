package tech.vitalis.caringu.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import tech.vitalis.caringu.dtos.Aluno.AlunoResponseGetDTO;
import tech.vitalis.caringu.entity.Aluno;
import tech.vitalis.caringu.enums.Aluno.NivelAtividadeEnum;
import tech.vitalis.caringu.enums.Aluno.NivelExperienciaEnum;
import tech.vitalis.caringu.enums.Pessoa.GeneroEnum;
import tech.vitalis.caringu.exception.Aluno.AlunoNaoEncontradoException;
import tech.vitalis.caringu.exception.Pessoa.EmailJaCadastradoException;
import tech.vitalis.caringu.exception.Pessoa.SenhaInvalidaException;
import tech.vitalis.caringu.mapper.AlunoMapper;
import tech.vitalis.caringu.repository.AlunoRepository;
import tech.vitalis.caringu.repository.PessoaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AlunoServiceTest {

    @InjectMocks
    private AlunoService alunoService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AlunoMapper alunoMapper;

    @Mock
    private AlunoRepository alunoRepository;

    @Mock
    private PessoaRepository pessoaRepository;

    private Aluno aluno;

    @BeforeEach
    void setUp() {
        aluno = new Aluno();
        aluno.setId(1);
        aluno.setNome("João");
        aluno.setEmail("joao@email.com");
        aluno.setSenha("Senha@123");
        aluno.setGenero(GeneroEnum.MULHER_CISGENERO);
        aluno.setNivelAtividade(NivelAtividadeEnum.LEVEMENTE_ATIVO);
        aluno.setNivelExperiencia(NivelExperienciaEnum.INICIANTE);
    }

    @Test
    @DisplayName("listar deve retornar lista de alunos convertidos")
    void listar_DeveRetornarListaDeAlunosConvertidos() {
        when(alunoRepository.findAll()).thenReturn(List.of(aluno));
        when(alunoMapper.toResponseDTO(aluno)).thenReturn(new AlunoResponseGetDTO(
                1,
                "Bianca Borges",
                "bianca@email.com",
                "(11) 91234-5678",
                "https://example.com/fotoPerfil.jpg",
                LocalDate.of(2000, 5, 10),
                GeneroEnum.MULHER_CISGENERO,
                60.5,
                1.65,
                NivelAtividadeEnum.MUITO_ATIVO,
                NivelExperienciaEnum.INTERMEDIARIO,
                LocalDateTime.now()
        ));

        List<AlunoResponseGetDTO> resultado = alunoService.listar();

        assertFalse(resultado.isEmpty());
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
    }

    @Test
    @DisplayName("listar deve retornar lista vazia se não houver alunos")
    void listar_DeveRetornarListaVazia() {
        when(alunoRepository.findAll()).thenReturn(Collections.emptyList());

        List<AlunoResponseGetDTO> resultado = alunoService.listar();

        assertTrue(resultado.isEmpty());
    }

    @Test
    @DisplayName("listarPresencas deve retornar resultado para SEMANA")
    void listarPresencas_Semana() {
        when(alunoRepository.buscarPresencaAlunos(any(), any(), eq(1))).thenReturn(List.of());
        alunoService.listarPresencas("SEMANA", 1);
        verify(alunoRepository).buscarPresencaAlunos(any(), any(), eq(1));
    }

    @Test
    @DisplayName("listarPresencas deve retornar resultado para MES")
    void listarPresencas_Mes() {
        when(alunoRepository.buscarPresencaAlunos(any(), any(), eq(1))).thenReturn(List.of());
        alunoService.listarPresencas("MES", 1);
        verify(alunoRepository).buscarPresencaAlunos(any(), any(), eq(1));
    }

    @Test
    @DisplayName("listarPresencas deve lançar IllegalArgumentException para período inválido")
    void listarPresencas_PeriodoInvalido() {
        assertThrows(IllegalArgumentException.class, () -> alunoService.listarPresencas("ANO", 1));
    }

    @Test
    @DisplayName("listarAlunosAtivos deve retornar lista de ativos")
    void listarAlunosAtivos() {
        when(alunoRepository.buscarAlunosAtivosComIndicadores(1)).thenReturn(List.of());
        alunoService.listarAlunosAtivos(1);
        verify(alunoRepository).buscarAlunosAtivosComIndicadores(1);
    }

    @Test
    @DisplayName("buscarAlunosSemAnamnese deve retornar alunos convertidos")
    void buscarAlunosSemAnamnese() {
        when(alunoRepository.findAlunosSemAnamnese()).thenReturn(List.of(aluno));
        when(alunoMapper.toResponseDTO(aluno)).thenReturn(new AlunoResponseGetDTO(1,
                "Bianca Borges",
                "bianca@email.com",
                "(11) 91234-5678",
                "https://example.com/fotoPerfil.jpg",
                LocalDate.of(2000, 5, 10),
                GeneroEnum.MULHER_CISGENERO,
                60.5,
                1.65,
                NivelAtividadeEnum.MUITO_ATIVO,
                NivelExperienciaEnum.INTERMEDIARIO,
                LocalDateTime.now()));
        alunoService.buscarAlunosSemAnamnese();
        verify(alunoRepository).findAlunosSemAnamnese();
        verify(alunoMapper).toResponseDTO(aluno);
    }

    @Test
    @DisplayName("buscarPorId deve retornar aluno existente")
    void buscarPorId_DeveRetornarAluno() {
        when(alunoRepository.findById(1)).thenReturn(Optional.of(aluno));
        when(alunoMapper.toResponseDTO(aluno)).thenReturn(new AlunoResponseGetDTO(1,
                "Bianca Borges",
                "bianca@email.com",
                "(11) 91234-5678",
                "https://example.com/fotoPerfil.jpg",
                LocalDate.of(2000, 5, 10),
                GeneroEnum.MULHER_CISGENERO,
                60.5,
                1.65,
                NivelAtividadeEnum.MUITO_ATIVO,
                NivelExperienciaEnum.INTERMEDIARIO,
                LocalDateTime.now()));
        alunoService.buscarPorId(1);
        verify(alunoRepository).findById(1);
    }

    @Test
    @DisplayName("buscarPorId deve lançar exceção se aluno não for encontrado")
    void buscarPorId_DeveLancarExcecao() {
        when(alunoRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(AlunoNaoEncontradoException.class, () -> alunoService.buscarPorId(1));
    }

    @Test
    @DisplayName("cadastrar deve salvar aluno com senha criptografada")
    void cadastrar_ComSucesso() {
        when(pessoaRepository.existsByEmail(any())).thenReturn(false);
        when(passwordEncoder.encode(any())).thenReturn("SenhaCriptografada");
        when(alunoMapper.toResponseDTO(any())).thenReturn(new AlunoResponseGetDTO(1,
                "Bianca Borges",
                "bianca@email.com",
                "(11) 91234-5678",
                "https://example.com/fotoPerfil.jpg",
                LocalDate.of(2000, 5, 10),
                GeneroEnum.MULHER_CISGENERO,
                60.5,
                1.65,
                NivelAtividadeEnum.MUITO_ATIVO,
                NivelExperienciaEnum.INTERMEDIARIO,
                LocalDateTime.now()));

        AlunoResponseGetDTO dto = alunoService.cadastrar(aluno);

        assertNotNull(dto);
        verify(alunoRepository).save(aluno);
    }

    @Test
    @DisplayName("cadastrar deve lançar exceção se email já existe")
    void cadastrar_EmailJaExiste() {
        when(pessoaRepository.existsByEmail(any())).thenReturn(true);
        assertThrows(EmailJaCadastradoException.class, () -> alunoService.cadastrar(aluno));
    }

    @Test
    @DisplayName("cadastrar deve lançar exceção para senha fraca")
    void cadastrar_SenhaFraca() {
        aluno.setSenha("fraca");
        assertThrows(SenhaInvalidaException.class, () -> alunoService.cadastrar(aluno));
    }

    @Test
    @DisplayName("deletar deve funcionar corretamente")
    void deletar_ComSucesso() {
        when(alunoRepository.findById(1)).thenReturn(Optional.of(aluno));
        alunoService.deletar(1);
        verify(alunoRepository).delete(aluno);
    }

    @Test
    @DisplayName("deletar deve lançar exceção se aluno não existir")
    void deletar_AlunoNaoEncontrado() {
        when(alunoRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(AlunoNaoEncontradoException.class, () -> alunoService.deletar(1));
    }
}
