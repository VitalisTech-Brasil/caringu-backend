package tech.vitalis.caringu.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.vitalis.caringu.dtos.Notificacoes.NotificacoesRequestPatchDto;
import tech.vitalis.caringu.dtos.Notificacoes.NotificacoesRequestPostDto;
import tech.vitalis.caringu.dtos.Notificacoes.NotificacoesResponseGetDto;
import tech.vitalis.caringu.dtos.Pessoa.PessoaResponseGetDTO;
import tech.vitalis.caringu.entity.Notificacoes;
import tech.vitalis.caringu.entity.Pessoa;
import tech.vitalis.caringu.enums.Notificacoes.TipoNotificacaoEnum;
import tech.vitalis.caringu.exception.ApiExceptions;
import tech.vitalis.caringu.mapper.NotificacoesMapper;
import tech.vitalis.caringu.mapper.PessoaMapper;
import tech.vitalis.caringu.repository.NotificacoesRepository;
import tech.vitalis.caringu.repository.PessoaRepository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificacoesServiceTest {

    @Mock
    private NotificacoesRepository notificacoesRepository;

    @Mock
    private NotificacoesMapper notificacoesMapper;

    @Mock
    private PessoaRepository pessoaRepository;

    @InjectMocks
    private NotificacoesService notificacoesService;

    @Captor
    private ArgumentCaptor<Notificacoes> notificacoesCaptor;

    @Captor
    private ArgumentCaptor<List<Notificacoes>> notificacoesListCaptor;

    private Pessoa pessoa;
    private Notificacoes notificacao;
    private NotificacoesRequestPostDto requestPostDto;
    private NotificacoesRequestPatchDto requestPatchDto;
    private NotificacoesResponseGetDto responseDto;
    private PessoaResponseGetDTO pessoaResponseDto;

    @BeforeEach
    void setUp() {
        pessoa = new Pessoa();
        pessoa.setId(1);
        pessoa.setNome("João Silva");

        pessoaResponseDto = new PessoaResponseGetDTO(
                1,
                "João Silva",
                "joao@email.com",
                "11999999999",
                null,
                null,
                null,
                LocalDateTime.now()
        );

        notificacao = new Notificacoes();
        notificacao.setId(1);
        notificacao.setPessoa(pessoa);
        notificacao.setTipo(TipoNotificacaoEnum.AULAS_PENDENTES);
        notificacao.setTitulo("Teste de notificação");
        notificacao.setVisualizada(false);
        notificacao.setDataCriacao(LocalDateTime.now());

        requestPostDto = new NotificacoesRequestPostDto(
                1,
                TipoNotificacaoEnum.AULAS_PENDENTES,
                "Teste de notificação",
                false,
                LocalDateTime.now()
        );

        requestPatchDto = new NotificacoesRequestPatchDto(
                TipoNotificacaoEnum.FEEDBACK_TREINO,
                "Notificação atualizada",
                true,
                LocalDateTime.now()
        );

        responseDto = new NotificacoesResponseGetDto(
                1,
                pessoaResponseDto,
                TipoNotificacaoEnum.AULAS_PENDENTES,
                "Teste de notificação",
                false,
                LocalDateTime.now()
        );
    }

    @Test
    @DisplayName("cadastrar deve criar notificação com sucesso")
    void cadastrar_ComDadosValidos_DeveCriarNotificacao() {
        // ARRANGE
        when(pessoaRepository.findById(1)).thenReturn(Optional.of(pessoa));
        when(notificacoesMapper.toEntity(requestPostDto)).thenReturn(notificacao);
        when(notificacoesRepository.save(any(Notificacoes.class))).thenReturn(notificacao);
        when(notificacoesMapper.toResponseDTO(notificacao)).thenReturn(responseDto);

        // ACT
        NotificacoesResponseGetDto resultado = notificacoesService.cadastrar(requestPostDto);

        // ASSERT
        assertNotNull(resultado);
        assertEquals(1, resultado.id());
        verify(pessoaRepository).findById(1);
        verify(notificacoesMapper).toEntity(requestPostDto);
        verify(notificacoesRepository).save(notificacoesCaptor.capture());
        verify(notificacoesMapper).toResponseDTO(notificacao);

        Notificacoes notificacaoSalva = notificacoesCaptor.getValue();
        assertEquals(pessoa, notificacaoSalva.getPessoa());
    }

    @Test
    @DisplayName("cadastrar deve lançar exceção quando pessoa não é encontrada")
    void cadastrar_PessoaNaoEncontrada_DeveLancarExcecao() {
        // ARRANGE
        when(pessoaRepository.findById(1)).thenReturn(Optional.empty());

        // ACT & ASSERT
        ApiExceptions.BadRequestException exception = assertThrows(
                ApiExceptions.BadRequestException.class,
                () -> notificacoesService.cadastrar(requestPostDto)
        );

        assertTrue(exception.getMessage().contains("Pessoa com o ID"));
        verify(notificacoesRepository, never()).save(any());
    }

    // ==================== TESTES: buscarPorId() ====================

    @Test
    @DisplayName("buscarPorId deve retornar notificação quando existe")
    void buscarPorId_NotificacaoExiste_DeveRetornarNotificacao() {
        // ARRANGE
        when(notificacoesRepository.findById(1)).thenReturn(Optional.of(notificacao));
        when(notificacoesMapper.toResponseDTO(notificacao)).thenReturn(responseDto);

        // ACT
        NotificacoesResponseGetDto resultado = notificacoesService.buscarPorId(1);

        // ASSERT
        assertNotNull(resultado);
        assertEquals(1, resultado.id());
        verify(notificacoesRepository).findById(1);
        verify(notificacoesMapper).toResponseDTO(notificacao);
    }

    @Test
    @DisplayName("buscarPorId deve lançar exceção quando notificação não existe")
    void buscarPorId_NotificacaoNaoExiste_DeveLancarExcecao() {
        // ARRANGE
        when(notificacoesRepository.findById(999)).thenReturn(Optional.empty());

        // ACT & ASSERT
        ApiExceptions.ResourceNotFoundException exception = assertThrows(
                ApiExceptions.ResourceNotFoundException.class,
                () -> notificacoesService.buscarPorId(999)
        );

        assertTrue(exception.getMessage().contains("Notificação com ID"));
    }

    // ==================== TESTES: buscarPorPessoaId() ====================

    @Test
    @DisplayName("buscarPorPessoaId deve retornar lista de notificações quando existem")
    void buscarPorPessoaId_ComNotificacoes_DeveRetornarLista() {
        // ARRANGE
        when(pessoaRepository.findById(1)).thenReturn(Optional.of(pessoa));
        when(notificacoesRepository.findByPessoa_Id(1)).thenReturn(List.of(notificacao));
        when(notificacoesMapper.toResponseDTO(notificacao)).thenReturn(responseDto);

        // ACT
        List<NotificacoesResponseGetDto> resultado = notificacoesService.buscarPorPessoaId(1);

        // ASSERT
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(pessoaRepository).findById(1);
        verify(notificacoesRepository).findByPessoa_Id(1);
    }

    @Test
    @DisplayName("buscarPorPessoaId deve lançar exceção quando pessoa não existe")
    void buscarPorPessoaId_PessoaNaoExiste_DeveLancarExcecao() {
        // ARRANGE
        when(pessoaRepository.findById(999)).thenReturn(Optional.empty());

        // ACT & ASSERT
        ApiExceptions.BadRequestException exception = assertThrows(
                ApiExceptions.BadRequestException.class,
                () -> notificacoesService.buscarPorPessoaId(999)
        );

        assertTrue(exception.getMessage().contains("Pessoa com o ID"));
    }

    @Test
    @DisplayName("buscarPorPessoaId deve lançar exceção quando não há notificações")
    void buscarPorPessoaId_SemNotificacoes_DeveLancarExcecao() {
        // ARRANGE
        when(pessoaRepository.findById(1)).thenReturn(Optional.of(pessoa));
        when(notificacoesRepository.findByPessoa_Id(1)).thenReturn(Collections.emptyList());

        // ACT & ASSERT
        ApiExceptions.ResourceNotFoundException exception = assertThrows(
                ApiExceptions.ResourceNotFoundException.class,
                () -> notificacoesService.buscarPorPessoaId(1)
        );

        assertTrue(exception.getMessage().contains("Nenhuma notificação encontrada"));
    }

    // ==================== TESTES: listarTodos() ====================

    @Test
    @DisplayName("listarTodos deve retornar lista de todas as notificações")
    void listarTodos_ComNotificacoes_DeveRetornarLista() {
        // ARRANGE
        when(notificacoesRepository.findAll()).thenReturn(List.of(notificacao));
        when(notificacoesMapper.toResponseDTO(notificacao)).thenReturn(responseDto);

        // ACT
        List<NotificacoesResponseGetDto> resultado = notificacoesService.listarTodos();

        // ASSERT
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(notificacoesRepository).findAll();
    }

    @Test
    @DisplayName("listarTodos deve retornar lista vazia quando não há notificações")
    void listarTodos_SemNotificacoes_DeveRetornarListaVazia() {
        // ARRANGE
        when(notificacoesRepository.findAll()).thenReturn(Collections.emptyList());

        // ACT
        List<NotificacoesResponseGetDto> resultado = notificacoesService.listarTodos();

        // ASSERT
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    // ==================== TESTES: atualizar() ====================

    @Test
    @DisplayName("atualizar deve atualizar notificação existente")
    void atualizar_NotificacaoExiste_DeveAtualizar() {
        // ARRANGE
        when(notificacoesRepository.findById(1)).thenReturn(Optional.of(notificacao));
        when(notificacoesRepository.save(any(Notificacoes.class))).thenReturn(notificacao);
        when(notificacoesMapper.toResponseDTO(notificacao)).thenReturn(responseDto);

        // ACT
        NotificacoesResponseGetDto resultado = notificacoesService.atualizar(1, requestPatchDto);

        // ASSERT
        assertNotNull(resultado);
        verify(notificacoesRepository).findById(1);
        verify(notificacoesRepository).save(notificacoesCaptor.capture());

        Notificacoes notificacaoAtualizada = notificacoesCaptor.getValue();
        assertEquals(requestPatchDto.tipo(), notificacaoAtualizada.getTipo());
        assertEquals(requestPatchDto.titulo(), notificacaoAtualizada.getTitulo());
        assertEquals(requestPatchDto.visualizada(), notificacaoAtualizada.getVisualizada());
    }

    @Test
    @DisplayName("atualizar deve lançar exceção quando notificação não existe")
    void atualizar_NotificacaoNaoExiste_DeveLancarExcecao() {
        // ARRANGE
        when(notificacoesRepository.findById(999)).thenReturn(Optional.empty());

        // ACT & ASSERT
        ApiExceptions.ResourceNotFoundException exception = assertThrows(
                ApiExceptions.ResourceNotFoundException.class,
                () -> notificacoesService.atualizar(999, requestPatchDto)
        );

        assertTrue(exception.getMessage().contains("Notificação com ID"));
    }

    // ==================== TESTES: atualizarVisualizada() ====================

    @Test
    @DisplayName("atualizarVisualizada deve marcar notificação como visualizada")
    void atualizarVisualizada_NotificacaoExiste_DeveMarcarComoVisualizada() {
        // ARRANGE
        when(notificacoesRepository.findById(1)).thenReturn(Optional.of(notificacao));

        // ACT
        notificacoesService.atualizarVisualizada(1, true);

        // ASSERT
        assertTrue(notificacao.getVisualizada());
        verify(notificacoesRepository).findById(1);
    }

    @Test
    @DisplayName("atualizarVisualizada deve lançar exceção quando notificação não existe")
    void atualizarVisualizada_NotificacaoNaoExiste_DeveLancarExcecao() {
        // ARRANGE
        when(notificacoesRepository.findById(999)).thenReturn(Optional.empty());

        // ACT & ASSERT
        ApiExceptions.ResourceNotFoundException exception = assertThrows(
                ApiExceptions.ResourceNotFoundException.class,
                () -> notificacoesService.atualizarVisualizada(999, true)
        );

        assertTrue(exception.getMessage().contains("Notificação não encontrada"));
    }

    // ==================== TESTES: buscarPorPessoaIdENaoVisualza() ====================

    @Test
    @DisplayName("buscarPorPessoaIdENaoVisualza deve retornar notificações ordenadas")
    void buscarPorPessoaIdENaoVisualza_ComNotificacoes_DeveRetornarListaOrdenada() {
        // ARRANGE
        when(pessoaRepository.findById(1)).thenReturn(Optional.of(pessoa));
        when(notificacoesRepository.findByPessoaOrderByVisualizadaAscDataCriacaoDesc(pessoa))
                .thenReturn(List.of(notificacao));
        when(notificacoesMapper.toResponseDTO(notificacao)).thenReturn(responseDto);

        // ACT
        List<NotificacoesResponseGetDto> resultado = notificacoesService.buscarPorPessoaIdENaoVisualza(1);

        // ASSERT
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(pessoaRepository).findById(1);
        verify(notificacoesRepository).findByPessoaOrderByVisualizadaAscDataCriacaoDesc(pessoa);
    }

    @Test
    @DisplayName("buscarPorPessoaIdENaoVisualza deve lançar exceção quando pessoa não existe")
    void buscarPorPessoaIdENaoVisualza_PessoaNaoExiste_DeveLancarExcecao() {
        // ARRANGE
        when(pessoaRepository.findById(999)).thenReturn(Optional.empty());

        // ACT & ASSERT
        ApiExceptions.BadRequestException exception = assertThrows(
                ApiExceptions.BadRequestException.class,
                () -> notificacoesService.buscarPorPessoaIdENaoVisualza(999)
        );

        assertTrue(exception.getMessage().contains("Pessoa com o ID"));
    }

    @Test
    @DisplayName("buscarPorPessoaIdENaoVisualza deve lançar exceção quando não há notificações")
    void buscarPorPessoaIdENaoVisualza_SemNotificacoes_DeveLancarExcecao() {
        // ARRANGE
        when(pessoaRepository.findById(1)).thenReturn(Optional.of(pessoa));
        when(notificacoesRepository.findByPessoaOrderByVisualizadaAscDataCriacaoDesc(pessoa))
                .thenReturn(Collections.emptyList());

        // ACT & ASSERT
        ApiExceptions.ResourceNotFoundException exception = assertThrows(
                ApiExceptions.ResourceNotFoundException.class,
                () -> notificacoesService.buscarPorPessoaIdENaoVisualza(1)
        );

        assertTrue(exception.getMessage().contains("Nenhuma notificação não visualizada encontrada"));
    }

    // ==================== TESTES: marcarTodasComoVisualizadasPorPessoaId() ====================

    @Test
    @DisplayName("marcarTodasComoVisualizadasPorPessoaId deve marcar todas as notificações como visualizadas")
    void marcarTodasComoVisualizadasPorPessoaId_ComNotificacoes_DeveMarcarTodas() {
        // ARRANGE
        Notificacoes notificacao2 = new Notificacoes();
        notificacao2.setId(2);
        notificacao2.setPessoa(pessoa);
        notificacao2.setVisualizada(false);

        when(pessoaRepository.findById(1)).thenReturn(Optional.of(pessoa));
        when(notificacoesRepository.findByPessoaOrderByVisualizadaAscDataCriacaoDesc(pessoa))
                .thenReturn(List.of(notificacao, notificacao2));

        // ACT
        notificacoesService.marcarTodasComoVisualizadasPorPessoaId(1);

        // ASSERT
        verify(pessoaRepository).findById(1);
        verify(notificacoesRepository).saveAll(notificacoesListCaptor.capture());

        List<Notificacoes> notificacoesSalvas = notificacoesListCaptor.getValue();
        assertEquals(2, notificacoesSalvas.size());
        assertTrue(notificacoesSalvas.get(0).getVisualizada());
        assertTrue(notificacoesSalvas.get(1).getVisualizada());
    }

    @Test
    @DisplayName("marcarTodasComoVisualizadasPorPessoaId deve lançar exceção quando pessoa não existe")
    void marcarTodasComoVisualizadasPorPessoaId_PessoaNaoExiste_DeveLancarExcecao() {
        // ARRANGE
        when(pessoaRepository.findById(999)).thenReturn(Optional.empty());

        // ACT & ASSERT
        ApiExceptions.BadRequestException exception = assertThrows(
                ApiExceptions.BadRequestException.class,
                () -> notificacoesService.marcarTodasComoVisualizadasPorPessoaId(999)
        );

        assertTrue(exception.getMessage().contains("Pessoa com o ID"));
    }

    @Test
    @DisplayName("marcarTodasComoVisualizadasPorPessoaId deve lançar exceção quando não há notificações")
    void marcarTodasComoVisualizadasPorPessoaId_SemNotificacoes_DeveLancarExcecao() {
        // ARRANGE
        when(pessoaRepository.findById(1)).thenReturn(Optional.of(pessoa));
        when(notificacoesRepository.findByPessoaOrderByVisualizadaAscDataCriacaoDesc(pessoa))
                .thenReturn(Collections.emptyList());

        // ACT & ASSERT
        ApiExceptions.ResourceNotFoundException exception = assertThrows(
                ApiExceptions.ResourceNotFoundException.class,
                () -> notificacoesService.marcarTodasComoVisualizadasPorPessoaId(1)
        );

        assertTrue(exception.getMessage().contains("Nenhuma notificação não visualizada encontrada"));
    }
}
