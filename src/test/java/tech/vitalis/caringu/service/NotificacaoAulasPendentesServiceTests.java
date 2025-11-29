package tech.vitalis.caringu.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.vitalis.caringu.dtos.Aula.AulasPendentesNotificacaoDTO;
import tech.vitalis.caringu.entity.Notificacoes;
import tech.vitalis.caringu.entity.Pessoa;
import tech.vitalis.caringu.enums.Notificacoes.TipoNotificacaoEnum;
import tech.vitalis.caringu.repository.AulaRepository;
import tech.vitalis.caringu.repository.NotificacoesRepository;
import tech.vitalis.caringu.repository.PessoaRepository;
import tech.vitalis.caringu.repository.PreferenciaNotificacaoRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificacaoAulasPendentesServiceTests {

    @Mock
    private AulaRepository aulaRepository;

    @Mock
    private PessoaRepository pessoaRepository;

    @Mock
    private NotificacoesRepository notificacoesRepository;

    @InjectMocks
    private NotificacaoAulasPendentesService notificacaoAulasPendentesService;

    @Captor
    private ArgumentCaptor<List<Notificacoes>> notificacoesCaptor;

    @Test
    @DisplayName("notificarAulasPendentesSemanalmente deve criar notificações para aluno e personal quando existem aulas pendentes")
    void notificarAulasPendentesSemanalmente_ComAulasPendentes_DeveCriarNotificacoes() {
        // ARRANGE
        AulasPendentesNotificacaoDTO plano = new AulasPendentesNotificacaoDTO(
                1,                      // alunoId
                "João Silva",           // nomeAluno
                2,                      // personalId
                "Carlos Personal",      // nomePersonal
                10,                     // planoContratadoId
                12L,                    // totalAulasPlano
                8L,                     // aulasAgendadas
                4L                      // aulasDisponiveis
        );

        Pessoa aluno = new Pessoa();
        aluno.setId(1);
        aluno.setNome("João Silva");

        Pessoa personal = new Pessoa();
        personal.setId(2);
        personal.setNome("Carlos Personal");

        when(aulaRepository.buscarTodosPlanosAtivos()).thenReturn(List.of(plano));
        when(pessoaRepository.findById(1)).thenReturn(Optional.of(aluno));
        when(pessoaRepository.findById(2)).thenReturn(Optional.of(personal));

        // ACT
        notificacaoAulasPendentesService.notificarAulasPendentesSemanalmente();

        // ASSERT
        verify(notificacoesRepository).saveAll(notificacoesCaptor.capture());
        List<Notificacoes> notificacoesSalvas = notificacoesCaptor.getValue();

        assertEquals(2, notificacoesSalvas.size()); // aluno + personal

        // Verificar notificação do aluno
        Notificacoes notifAluno = notificacoesSalvas.get(0);
        assertEquals(aluno, notifAluno.getPessoa());
        assertEquals(TipoNotificacaoEnum.AULAS_PENDENTES, notifAluno.getTipo());
        assertTrue(notifAluno.getTitulo().contains("4 aulas disponíveis"));
        assertTrue(notifAluno.getTitulo().contains("Carlos Personal"));
        assertFalse(notifAluno.getVisualizada());
        assertNotNull(notifAluno.getDataCriacao());

        // Verificar notificação do personal
        Notificacoes notifPersonal = notificacoesSalvas.get(1);
        assertEquals(personal, notifPersonal.getPessoa());
        assertEquals(TipoNotificacaoEnum.AULAS_PENDENTES, notifPersonal.getTipo());
        assertTrue(notifPersonal.getTitulo().contains("4 aulas disponíveis"));
        assertTrue(notifPersonal.getTitulo().contains("João Silva"));
        assertFalse(notifPersonal.getVisualizada());
        assertNotNull(notifPersonal.getDataCriacao());
    }

    @Test
    @DisplayName("notificarAulasPendentesSemanalmente não deve criar notificações quando lista está vazia")
    void notificarAulasPendentesSemanalmente_SemAulasPendentes_NaoDeveCriarNotificacoes() {
        // ARRANGE
        when(aulaRepository.buscarTodosPlanosAtivos()).thenReturn(List.of());

        // ACT
        notificacaoAulasPendentesService.notificarAulasPendentesSemanalmente();

        // ASSERT
        verify(notificacoesRepository, never()).saveAll(any());
        verify(pessoaRepository, never()).findById(anyInt());
    }

    @Test
    @DisplayName("notificarAulasPendentesSemanalmente deve ignorar notificação quando aluno não é encontrado")
    void notificarAulasPendentesSemanalmente_AlunoNaoEncontrado_DeveCriarApenasNotificacaoPersonal() {
        // ARRANGE
        AulasPendentesNotificacaoDTO plano = new AulasPendentesNotificacaoDTO(
                1, "João Silva", 2, "Carlos Personal", 10, 12L, 8L, 4L
        );

        Pessoa personal = new Pessoa();
        personal.setId(2);
        personal.setNome("Carlos Personal");

        when(aulaRepository.buscarTodosPlanosAtivos()).thenReturn(List.of(plano));
        when(pessoaRepository.findById(1)).thenReturn(Optional.empty());
        when(pessoaRepository.findById(2)).thenReturn(Optional.of(personal));

        // ACT
        notificacaoAulasPendentesService.notificarAulasPendentesSemanalmente();

        // ASSERT
        verify(notificacoesRepository).saveAll(notificacoesCaptor.capture());
        List<Notificacoes> notificacoesSalvas = notificacoesCaptor.getValue();

        assertEquals(1, notificacoesSalvas.size()); // apenas personal
        assertEquals(personal, notificacoesSalvas.get(0).getPessoa());
    }

    @Test
    @DisplayName("notificarAulasPendentesSemanalmente deve ignorar notificação quando personal não é encontrado")
    void notificarAulasPendentesSemanalmente_PersonalNaoEncontrado_DeveCriarApenasNotificacaoAluno() {
        // ARRANGE
        AulasPendentesNotificacaoDTO plano = new AulasPendentesNotificacaoDTO(
                1, "João Silva", 2, "Carlos Personal", 10, 12L, 8L, 4L
        );

        Pessoa aluno = new Pessoa();
        aluno.setId(1);
        aluno.setNome("João Silva");

        when(aulaRepository.buscarTodosPlanosAtivos()).thenReturn(List.of(plano));
        when(pessoaRepository.findById(1)).thenReturn(Optional.of(aluno));
        when(pessoaRepository.findById(2)).thenReturn(Optional.empty());

        // ACT
        notificacaoAulasPendentesService.notificarAulasPendentesSemanalmente();

        // ASSERT
        verify(notificacoesRepository).saveAll(notificacoesCaptor.capture());
        List<Notificacoes> notificacoesSalvas = notificacoesCaptor.getValue();

        assertEquals(1, notificacoesSalvas.size());
        assertEquals(aluno, notificacoesSalvas.get(0).getPessoa());
    }

    @Test
    @DisplayName("notificarAulasPendentesSemanalmente deve criar múltiplas notificações para vários planos")
    void notificarAulasPendentesSemanalmente_VariosPlanos_DeveCriarTodasNotificacoes() {
        // ARRANGE
        AulasPendentesNotificacaoDTO plano1 = new AulasPendentesNotificacaoDTO(
                1, "João Silva", 2, "Carlos Personal", 10, 12L, 8L, 4L
        );
        AulasPendentesNotificacaoDTO plano2 = new AulasPendentesNotificacaoDTO(
                3, "Maria Santos", 4, "Ana Personal", 11, 8L, 6L, 2L
        );

        Pessoa aluno1 = new Pessoa();
        aluno1.setId(1);
        Pessoa personal1 = new Pessoa();
        personal1.setId(2);
        Pessoa aluno2 = new Pessoa();
        aluno2.setId(3);
        Pessoa personal2 = new Pessoa();
        personal2.setId(4);

        when(aulaRepository.buscarTodosPlanosAtivos()).thenReturn(List.of(plano1, plano2));
        when(pessoaRepository.findById(1)).thenReturn(Optional.of(aluno1));
        when(pessoaRepository.findById(2)).thenReturn(Optional.of(personal1));
        when(pessoaRepository.findById(3)).thenReturn(Optional.of(aluno2));
        when(pessoaRepository.findById(4)).thenReturn(Optional.of(personal2));

        // ACT
        notificacaoAulasPendentesService.notificarAulasPendentesSemanalmente();

        // ASSERT
        verify(notificacoesRepository).saveAll(notificacoesCaptor.capture());
        List<Notificacoes> notificacoesSalvas = notificacoesCaptor.getValue();

        assertEquals(4, notificacoesSalvas.size());
    }

    @Test
    @DisplayName("buscarPlanosComAulasPendentes deve retornar apenas planos com aulas disponíveis")
    void buscarPlanosComAulasPendentes_DeveFiltrarPlanosComAulasDisponiveis() {
        // ARRANGE
        AulasPendentesNotificacaoDTO planoComAulas = new AulasPendentesNotificacaoDTO(
                1, "João Silva", 2, "Carlos Personal", 10, 12L, 8L, 4L
        );
        AulasPendentesNotificacaoDTO planoSemAulas = new AulasPendentesNotificacaoDTO(
                3, "Maria Santos", 4, "Ana Personal", 11, 8L, 8L, 0L
        );

        when(aulaRepository.buscarTodosPlanosAtivos())
                .thenReturn(List.of(planoComAulas, planoSemAulas));

        // ACT
        List<AulasPendentesNotificacaoDTO> resultado =
                notificacaoAulasPendentesService.buscarPlanosComAulasPendentes();

        // ASSERT
        assertEquals(1, resultado.size());
        assertEquals(4L, resultado.get(0).aulasDisponiveis());
        verify(aulaRepository).buscarTodosPlanosAtivos();
    }

    @Test
    @DisplayName("buscarPlanosComAulasPendentes deve ordenar por aulas disponíveis decrescente")
    void buscarPlanosComAulasPendentes_DeveOrdenarDecrescente() {
        // ARRANGE
        AulasPendentesNotificacaoDTO plano1 = new AulasPendentesNotificacaoDTO(
                1, "João Silva", 2, "Carlos Personal", 10, 12L, 10L, 2L
        );
        AulasPendentesNotificacaoDTO plano2 = new AulasPendentesNotificacaoDTO(
                3, "Maria Santos", 4, "Ana Personal", 11, 10L, 5L, 5L
        );
        AulasPendentesNotificacaoDTO plano3 = new AulasPendentesNotificacaoDTO(
                5, "Pedro Costa", 6, "Bruno Personal", 12, 8L, 7L, 1L
        );

        when(aulaRepository.buscarTodosPlanosAtivos())
                .thenReturn(List.of(plano1, plano2, plano3));

        // ACT
        List<AulasPendentesNotificacaoDTO> resultado =
                notificacaoAulasPendentesService.buscarPlanosComAulasPendentes();

        // ASSERT
        assertEquals(3, resultado.size());
        assertEquals(5L, resultado.get(0).aulasDisponiveis()); // Maior
        assertEquals(2L, resultado.get(1).aulasDisponiveis()); // Meio
        assertEquals(1L, resultado.get(2).aulasDisponiveis()); // Menor
    }

    @Test
    @DisplayName("buscarPlanosComAulasPendentes deve retornar lista vazia quando não há planos ativos")
    void buscarPlanosComAulasPendentes_SemPlanosAtivos_DeveRetornarListaVazia() {
        // ARRANGE
        when(aulaRepository.buscarTodosPlanosAtivos()).thenReturn(List.of());

        // ACT
        List<AulasPendentesNotificacaoDTO> resultado =
                notificacaoAulasPendentesService.buscarPlanosComAulasPendentes();

        // ASSERT
        assertTrue(resultado.isEmpty());
        verify(aulaRepository).buscarTodosPlanosAtivos();
    }

    @Test
    @DisplayName("buscarPlanosComAulasPendentes deve retornar lista vazia quando todos os planos não têm aulas disponíveis")
    void buscarPlanosComAulasPendentes_TodosPlanosCompletos_DeveRetornarListaVazia() {
        // ARRANGE
        AulasPendentesNotificacaoDTO plano1 = new AulasPendentesNotificacaoDTO(
                1, "João Silva", 2, "Carlos Personal", 10, 12L, 12L, 0L
        );
        AulasPendentesNotificacaoDTO plano2 = new AulasPendentesNotificacaoDTO(
                3, "Maria Santos", 4, "Ana Personal", 11, 8L, 8L, 0L
        );

        when(aulaRepository.buscarTodosPlanosAtivos()).thenReturn(List.of(plano1, plano2));

        // ACT
        List<AulasPendentesNotificacaoDTO> resultado =
                notificacaoAulasPendentesService.buscarPlanosComAulasPendentes();

        // ASSERT
        assertTrue(resultado.isEmpty());
    }
}
