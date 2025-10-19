package tech.vitalis.caringu.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import tech.vitalis.caringu.dtos.Aluno.*;
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

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

    private AlunoRequestPostDTO dtoAluno;

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

    @BeforeEach
    void setUpDto() {
        dtoAluno = new AlunoRequestPostDTO(
                "Roger A. Jones",
                "roger.jones@gmail.com",
                "123Ab@",
                "11947139850",
                "https://meuarquivo.blob.core.windows.net/imagens/roger.jpg",
                LocalDate.of(2000, 4, 17),
                GeneroEnum.HOMEM_CISGENERO,
                75.0,
                1.78,
                NivelAtividadeEnum.MODERADAMENTE_ATIVO,
                NivelExperienciaEnum.INTERMEDIARIO
        );
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

//    @Test
//    @DisplayName("cadastrar deve salvar aluno com senha criptografada")
//    void cadastrar_ComSucesso() {
//        when(pessoaRepository.existsByEmail(any())).thenReturn(false);
//        when(passwordEncoder.encode(any())).thenReturn("SenhaCriptografada");
//        when(alunoMapper.toResponseDTO(any())).thenReturn(new AlunoResponseGetDTO(1,
//                "Bianca Borges",
//                "bianca@email.com",
//                "(11) 91234-5678",
//                "https://example.com/fotoPerfil.jpg",
//                LocalDate.of(2000, 5, 10),
//                GeneroEnum.MULHER_CISGENERO,
//                60.5,
//                1.65,
//                NivelAtividadeEnum.MUITO_ATIVO,
//                NivelExperienciaEnum.INTERMEDIARIO,
//                LocalDateTime.now()));
//
//        AlunoResponseGetDTO dto = alunoService.cadastrar(dtoAluno);
//
//        assertNotNull(dto);
//        verify(alunoRepository).save(aluno);
//    }

//    @Test
//    @DisplayName("cadastrar deve lançar exceção se email já existe")
//    void cadastrar_EmailJaExiste() {
//        when(pessoaRepository.existsByEmail(any())).thenReturn(true);
//        assertThrows(EmailJaCadastradoException.class, () -> alunoService.cadastrar(dtoAluno));
//    }
//
//    @Test
//    @DisplayName("cadastrar deve lançar exceção para senha fraca")
//    void cadastrar_SenhaFraca() {
//        aluno.setSenha("fraca");
//        assertThrows(SenhaInvalidaException.class, () -> alunoService.cadastrar(dtoAluno));
//    }

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

    @Test
    @DisplayName("atualizarParcial deve atualizar apenas os campos fornecidos")
    void atualizarParcial_DeveAtualizarCamposParciais() {
        when(alunoRepository.findById(1)).thenReturn(Optional.of(aluno));

        AlunoRequestPatchDTO dto = new AlunoRequestPatchDTO(
                "João Atualizado", "joao.atualizado@email.com", null, null,
                null, null, null, null,
                null, null, null
        );

        AlunoResponsePatchDTO response = alunoService.atualizarParcial(1, dto);

        assertEquals(Optional.of("João Atualizado"), response.nome());
        assertEquals(Optional.of("joao.atualizado@email.com"), response.email());
        verify(alunoRepository).save(aluno);
    }

    @Test
    @DisplayName("atualizarDadosFisicos deve atualizar campos físicos do aluno")
    void atualizarDadosFisicos_DeveAtualizarCamposFisicos() {
        when(alunoRepository.findById(1)).thenReturn(Optional.of(aluno));

        AlunoRequestPatchDadosFisicosDTO dto = new AlunoRequestPatchDadosFisicosDTO(
                75.0,
                1.80,
                NivelAtividadeEnum.MUITO_ATIVO,
                NivelExperienciaEnum.AVANCADO
        );

        when(alunoMapper.toResponseDadosFisicosDTO(any())).thenReturn(
                new AlunoResponsePatchDadosFisicosDTO(
                        1,
                        dto.peso(),
                        dto.altura(),
                        dto.nivelAtividade(),
                        dto.nivelExperiencia()
                )
        );

        AlunoResponsePatchDadosFisicosDTO response = alunoService.atualizarDadosFisicos(1, dto);

        assertEquals(75.0, aluno.getPeso());
        assertEquals(1.80, aluno.getAltura());
        assertEquals(NivelAtividadeEnum.MUITO_ATIVO, aluno.getNivelAtividade());
        assertEquals(NivelExperienciaEnum.AVANCADO, aluno.getNivelExperiencia());

        assertNotNull(response);
        assertEquals(75.0, response.peso());
        assertEquals(1.80, response.altura());
        assertEquals(NivelAtividadeEnum.MUITO_ATIVO, response.nivelAtividade());
        assertEquals(NivelExperienciaEnum.AVANCADO, response.nivelExperiencia());

        verify(alunoRepository).save(aluno);
    }

    @Test
    @DisplayName("buscarAlunosDetalhados deve retornar lista de DTOs detalhados")
    void buscarAlunosDetalhados_DeveRetornarDTOsDetalhados() {
        Integer personalId = 1;

        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.with(DayOfWeek.MONDAY);
        LocalDate endOfWeek = today.with(DayOfWeek.SUNDAY);

        List<AlunoDetalhadoResponseDTO> dadosBrutos = List.of(
                mock(AlunoDetalhadoResponseDTO.class)
        );
        when(alunoRepository.buscarDetalhesPorPersonal(personalId, startOfWeek.atStartOfDay(), endOfWeek.atTime(LocalTime.MAX))).thenReturn(dadosBrutos);

        List<AlunoDetalhadoComTreinosDTO> listaConsolidada = List.of(
                mock(AlunoDetalhadoComTreinosDTO.class)
        );
        when(alunoMapper.consolidarPorAluno(dadosBrutos)).thenReturn(listaConsolidada);

        List<AlunoDetalhadoComTreinosDTO> resultado = alunoService.buscarAlunosDetalhados(personalId);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(alunoRepository).buscarDetalhesPorPersonal(personalId, startOfWeek.atStartOfDay(), endOfWeek.atTime(LocalTime.MAX));
        verify(alunoMapper).consolidarPorAluno(dadosBrutos);
    }

}
