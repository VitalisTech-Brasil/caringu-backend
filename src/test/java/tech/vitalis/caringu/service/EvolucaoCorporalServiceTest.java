package tech.vitalis.caringu.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;
import tech.vitalis.caringu.dtos.EvolucaoCorporal.EvolucaoCorporalRequestPostDTO;
import tech.vitalis.caringu.dtos.EvolucaoCorporal.EvolucaoCorporalResponseGetDTO;
import tech.vitalis.caringu.entity.Aluno;
import tech.vitalis.caringu.entity.EvolucaoCorporal;
import tech.vitalis.caringu.enums.EvolucaoCorporal.TipoEvolucaoEnum;
import tech.vitalis.caringu.exception.Aluno.AlunoNaoEncontradoException;
import tech.vitalis.caringu.exception.EvolucaoCorporal.EvolucaoCorporalNaoEncontradaException;
import tech.vitalis.caringu.mapper.EvolucaoCorporalMapper;
import tech.vitalis.caringu.repository.AlunoRepository;
import tech.vitalis.caringu.repository.EvolucaoCorporalRepository;
import tech.vitalis.caringu.service.ArmazenamentoFotos.ArmazenamentoService;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EvolucaoCorporalServiceTest {

    @Mock
    private EvolucaoCorporalRepository evolucaoCorporalRepository;

    @Mock
    private AlunoRepository alunoRepository;

    @Mock
    private EvolucaoCorporalMapper evolucaoCorporalMapper;

    @Mock
    private ArmazenamentoService armazenamentoService;

    @InjectMocks
    private EvolucaoCorporalService evolucaoCorporalService;

    private Aluno aluno;
    private EvolucaoCorporal evolucaoCorporal;
    private EvolucaoCorporalRequestPostDTO requestDTO;
    private EvolucaoCorporalResponseGetDTO responseDTO;

    @BeforeEach
    void setUp() {
        aluno = new Aluno();
        aluno.setId(1);
        aluno.setNome("João Silva");
        aluno.setEmail("joao@email.com");

        evolucaoCorporal = new EvolucaoCorporal();
        evolucaoCorporal.setId(1);
        evolucaoCorporal.setTipo(TipoEvolucaoEnum.FRONTAL);
        evolucaoCorporal.setUrlFotoShape("https://exemplo.com/foto.jpg");
        evolucaoCorporal.setDataEnvio(LocalDateTime.now());
        evolucaoCorporal.setPeriodoAvaliacao(30);
        evolucaoCorporal.setAluno(aluno);

        requestDTO = new EvolucaoCorporalRequestPostDTO(
                TipoEvolucaoEnum.FRONTAL,
                null,
                null,
                30,
                1
        );

        responseDTO = new EvolucaoCorporalResponseGetDTO(
                1,
                TipoEvolucaoEnum.FRONTAL,
                "https://exemplo.com/foto.jpg",
                LocalDateTime.now(),
                30
        );
    }

    @Test
    @DisplayName("listar deve retornar lista de evoluções corporais convertidas")
    void listar_DeveRetornarListaDeEvolucoesConvertidas() {
        // ARRANGE
        when(evolucaoCorporalRepository.findAll()).thenReturn(List.of(evolucaoCorporal));
        when(evolucaoCorporalMapper.toResponseDTO(evolucaoCorporal)).thenReturn(responseDTO);

        // ACT
        List<EvolucaoCorporalResponseGetDTO> resultado = evolucaoCorporalService.listar();

        // ASSERT
        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        verify(evolucaoCorporalRepository).findAll();
        verify(evolucaoCorporalMapper).toResponseDTO(evolucaoCorporal);
    }

    @Test
    @DisplayName("listar deve retornar lista vazia quando não houver evoluções")
    void listar_DeveRetornarListaVazia() {
        // ARRANGE
        when(evolucaoCorporalRepository.findAll()).thenReturn(Collections.emptyList());

        // ACT
        List<EvolucaoCorporalResponseGetDTO> resultado = evolucaoCorporalService.listar();

        // ASSERT
        assertTrue(resultado.isEmpty());
        verify(evolucaoCorporalRepository).findAll();
        verifyNoInteractions(evolucaoCorporalMapper);
    }

    @Test
    @DisplayName("listarPorAluno deve retornar lista de evoluções do aluno")
    void listarPorAluno_DeveRetornarListaDeEvolucoes() {
        // ARRANGE
        when(alunoRepository.findById(1)).thenReturn(Optional.of(aluno));
        when(evolucaoCorporalRepository.findByAlunoId(1)).thenReturn(List.of(evolucaoCorporal));
        when(evolucaoCorporalMapper.toResponseDTO(evolucaoCorporal)).thenReturn(responseDTO);

        // ACT
        List<EvolucaoCorporalResponseGetDTO> resultado = evolucaoCorporalService.listarPorAluno(1);

        // ASSERT
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(alunoRepository).findById(1);
        verify(evolucaoCorporalRepository).findByAlunoId(1);
        verify(evolucaoCorporalMapper).toResponseDTO(evolucaoCorporal);
    }

    @Test
    @DisplayName("listarPorAluno deve lançar exceção quando aluno não existe")
    void listarPorAluno_DeveLancarExcecaoQuandoAlunoNaoExiste() {
        // ARRANGE
        when(alunoRepository.findById(1)).thenReturn(Optional.empty());

        // ACT & ASSERT
        assertThrows(AlunoNaoEncontradoException.class, () -> evolucaoCorporalService.listarPorAluno(1));
        verify(alunoRepository).findById(1);
        verifyNoInteractions(evolucaoCorporalRepository, evolucaoCorporalMapper);
    }

    @Test
    @DisplayName("listarPorAluno deve retornar lista vazia quando aluno não tem evoluções")
    void listarPorAluno_DeveRetornarListaVaziaQuandoAlunoSemEvolucoes() {
        // ARRANGE
        when(alunoRepository.findById(1)).thenReturn(Optional.of(aluno));
        when(evolucaoCorporalRepository.findByAlunoId(1)).thenReturn(Collections.emptyList());

        // ACT
        List<EvolucaoCorporalResponseGetDTO> resultado = evolucaoCorporalService.listarPorAluno(1);

        // ASSERT
        assertTrue(resultado.isEmpty());
        verify(alunoRepository).findById(1);
        verify(evolucaoCorporalRepository).findByAlunoId(1);
        verifyNoInteractions(evolucaoCorporalMapper);
    }

    @Test
    @DisplayName("cadastrar deve salvar evolução corporal sem arquivo")
    void cadastrar_DeveSalvarEvolucaoSemArquivo() {
        // ARRANGE
        when(alunoRepository.findById(1)).thenReturn(Optional.of(aluno));
        when(evolucaoCorporalRepository.findByAlunoId(1)).thenReturn(Collections.emptyList());
        when(evolucaoCorporalMapper.toEntity(requestDTO)).thenReturn(evolucaoCorporal);
        when(evolucaoCorporalRepository.save(any(EvolucaoCorporal.class))).thenReturn(evolucaoCorporal);
        when(evolucaoCorporalMapper.toResponseDTO(evolucaoCorporal)).thenReturn(responseDTO);

        // ACT
        EvolucaoCorporalResponseGetDTO resultado = evolucaoCorporalService.cadastrar(requestDTO, null);

        // ASSERT
        assertNotNull(resultado);
        verify(alunoRepository).findById(1);
        verify(evolucaoCorporalRepository).findByAlunoId(1);
        verify(evolucaoCorporalMapper).toEntity(requestDTO);
        verify(evolucaoCorporalRepository).save(any(EvolucaoCorporal.class));
        verify(evolucaoCorporalMapper).toResponseDTO(evolucaoCorporal);
        verifyNoInteractions(armazenamentoService);
    }

    @Test
    @DisplayName("cadastrar deve salvar evolução corporal com arquivo")
    void cadastrar_DeveSalvarEvolucaoComArquivo() {
        // ARRANGE
        MultipartFile arquivo = mock(MultipartFile.class);
        when(arquivo.isEmpty()).thenReturn(false);
        when(armazenamentoService.uploadArquivo(arquivo)).thenReturn("https://exemplo.com/nova-foto.jpg");

        when(alunoRepository.findById(1)).thenReturn(Optional.of(aluno));
        when(evolucaoCorporalRepository.findByAlunoId(1)).thenReturn(Collections.emptyList());
        when(evolucaoCorporalMapper.toEntity(requestDTO)).thenReturn(evolucaoCorporal);
        when(evolucaoCorporalRepository.save(any(EvolucaoCorporal.class))).thenReturn(evolucaoCorporal);
        when(evolucaoCorporalMapper.toResponseDTO(evolucaoCorporal)).thenReturn(responseDTO);

        // ACT
        EvolucaoCorporalResponseGetDTO resultado = evolucaoCorporalService.cadastrar(requestDTO, arquivo);

        // ASSERT
        assertNotNull(resultado);
        verify(armazenamentoService).uploadArquivo(arquivo);
        verify(evolucaoCorporalRepository).save(any(EvolucaoCorporal.class));
        verify(evolucaoCorporalMapper).toResponseDTO(evolucaoCorporal);
    }

    @Test
    @DisplayName("cadastrar deve deletar foto antiga quando existir do mesmo tipo")
    void cadastrar_DeveDeletarFotoAntigaQuandoExistir() {
        // ARRANGE
        MultipartFile arquivo = mock(MultipartFile.class);
        when(arquivo.isEmpty()).thenReturn(false);
        when(armazenamentoService.uploadArquivo(arquivo)).thenReturn("https://exemplo.com/nova-foto.jpg");

        EvolucaoCorporal evolucaoAntiga = new EvolucaoCorporal();
        evolucaoAntiga.setTipo(TipoEvolucaoEnum.FRONTAL);
        evolucaoAntiga.setUrlFotoShape("https://exemplo.com/foto-antiga.jpg");

        when(alunoRepository.findById(1)).thenReturn(Optional.of(aluno));
        when(evolucaoCorporalRepository.findByAlunoId(1)).thenReturn(List.of(evolucaoAntiga));
        when(evolucaoCorporalMapper.toEntity(requestDTO)).thenReturn(evolucaoCorporal);
        when(evolucaoCorporalRepository.save(any(EvolucaoCorporal.class))).thenReturn(evolucaoCorporal);
        when(evolucaoCorporalMapper.toResponseDTO(evolucaoCorporal)).thenReturn(responseDTO);

        // ACT
        EvolucaoCorporalResponseGetDTO resultado = evolucaoCorporalService.cadastrar(requestDTO, arquivo);

        // ASSERT
        assertNotNull(resultado);
        verify(armazenamentoService).deletarArquivoPorUrl("https://exemplo.com/foto-antiga.jpg");
        verify(armazenamentoService).uploadArquivo(arquivo);
        verify(evolucaoCorporalRepository).save(any(EvolucaoCorporal.class));
    }

    @Test
    @DisplayName("cadastrar deve continuar mesmo se falhar ao deletar foto antiga")
    void cadastrar_DeveContinuarSeErroDeletarFotoAntiga() {
        // ARRANGE
        MultipartFile arquivo = mock(MultipartFile.class);
        when(arquivo.isEmpty()).thenReturn(false);
        when(armazenamentoService.uploadArquivo(arquivo)).thenReturn("https://exemplo.com/nova-foto.jpg");

        EvolucaoCorporal evolucaoAntiga = new EvolucaoCorporal();
        evolucaoAntiga.setTipo(TipoEvolucaoEnum.FRONTAL);
        evolucaoAntiga.setUrlFotoShape("https://exemplo.com/foto-antiga.jpg");

        when(alunoRepository.findById(1)).thenReturn(Optional.of(aluno));
        when(evolucaoCorporalRepository.findByAlunoId(1)).thenReturn(List.of(evolucaoAntiga));
        doThrow(new RuntimeException("Erro ao deletar")).when(armazenamentoService).deletarArquivoPorUrl(anyString());
        when(evolucaoCorporalMapper.toEntity(requestDTO)).thenReturn(evolucaoCorporal);
        when(evolucaoCorporalRepository.save(any(EvolucaoCorporal.class))).thenReturn(evolucaoCorporal);
        when(evolucaoCorporalMapper.toResponseDTO(evolucaoCorporal)).thenReturn(responseDTO);

        // ACT & ASSERT
        assertDoesNotThrow(() -> evolucaoCorporalService.cadastrar(requestDTO, arquivo));

        verify(armazenamentoService).deletarArquivoPorUrl("https://exemplo.com/foto-antiga.jpg");
        verify(armazenamentoService).uploadArquivo(arquivo);
        verify(evolucaoCorporalRepository).save(any(EvolucaoCorporal.class));
    }

    @Test
    @DisplayName("cadastrar deve lançar exceção quando aluno não existe")
    void cadastrar_DeveLancarExcecaoQuandoAlunoNaoExiste() {
        // ARRANGE
        when(alunoRepository.findById(1)).thenReturn(Optional.empty());

        // ACT & ASSERT
        assertThrows(AlunoNaoEncontradoException.class,
                () -> evolucaoCorporalService.cadastrar(requestDTO, null));

        verify(alunoRepository).findById(1);
        verifyNoInteractions(evolucaoCorporalRepository, evolucaoCorporalMapper, armazenamentoService);
    }

    @Test
    @DisplayName("cadastrar não deve deletar foto de tipo diferente")
    void cadastrar_NaoDeveDeletarFotoDeTipoDiferente() {
        // ARRANGE
        MultipartFile arquivo = mock(MultipartFile.class);
        when(arquivo.isEmpty()).thenReturn(false);
        when(armazenamentoService.uploadArquivo(arquivo)).thenReturn("https://exemplo.com/nova-foto.jpg");

        EvolucaoCorporal evolucaoOutroTipo = new EvolucaoCorporal();
        evolucaoOutroTipo.setTipo(TipoEvolucaoEnum.COSTAS); // Tipo diferente
        evolucaoOutroTipo.setUrlFotoShape("https://exemplo.com/foto-costas.jpg");

        when(alunoRepository.findById(1)).thenReturn(Optional.of(aluno));
        when(evolucaoCorporalRepository.findByAlunoId(1)).thenReturn(List.of(evolucaoOutroTipo));
        when(evolucaoCorporalMapper.toEntity(requestDTO)).thenReturn(evolucaoCorporal);
        when(evolucaoCorporalRepository.save(any(EvolucaoCorporal.class))).thenReturn(evolucaoCorporal);
        when(evolucaoCorporalMapper.toResponseDTO(evolucaoCorporal)).thenReturn(responseDTO);

        // ACT
        EvolucaoCorporalResponseGetDTO resultado = evolucaoCorporalService.cadastrar(requestDTO, arquivo);

        // ASSERT
        assertNotNull(resultado);
        verify(armazenamentoService, never()).deletarArquivoPorUrl(anyString());
        verify(armazenamentoService).uploadArquivo(arquivo);
    }

    @Test
    @DisplayName("deletar deve deletar evolução existente")
    void deletar_DeveDeletarEvolucaoExistente() {
        // ARRANGE
        when(evolucaoCorporalRepository.existsById(1)).thenReturn(true);

        // ACT
        evolucaoCorporalService.deletar(1);

        // ASSERT
        verify(evolucaoCorporalRepository).existsById(1);
        verify(evolucaoCorporalRepository).deleteById(1);
    }

    @Test
    @DisplayName("deletar deve lançar exceção quando evolução não existe")
    void deletar_DeveLancarExcecaoQuandoEvolucaoNaoExiste() {
        // ARRANGE
        when(evolucaoCorporalRepository.existsById(1)).thenReturn(false);

        // ACT & ASSERT
        assertThrows(EvolucaoCorporalNaoEncontradaException.class,
                () -> evolucaoCorporalService.deletar(1));

        verify(evolucaoCorporalRepository).existsById(1);
        verify(evolucaoCorporalRepository, never()).deleteById(anyInt());
    }
}

