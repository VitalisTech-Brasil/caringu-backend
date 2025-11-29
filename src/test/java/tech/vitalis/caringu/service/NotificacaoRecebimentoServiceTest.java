package tech.vitalis.caringu.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.vitalis.caringu.core.exceptions.ApiExceptions;
import tech.vitalis.caringu.entity.Aluno;
import tech.vitalis.caringu.entity.Notificacoes;
import tech.vitalis.caringu.entity.PersonalTrainer;
import tech.vitalis.caringu.entity.Plano;
import tech.vitalis.caringu.enums.Notificacoes.TipoNotificacaoEnum;
import tech.vitalis.caringu.enums.PreferenciaNotificacao.TipoPreferenciaEnum;
import tech.vitalis.caringu.infrastructure.persistence.planoContratado.PlanoContratadoEntity;
import tech.vitalis.caringu.infrastructure.persistence.planoContratado.PlanoContratadoRepository;
import tech.vitalis.caringu.repository.NotificacoesRepository;
import tech.vitalis.caringu.repository.PreferenciaNotificacaoRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificacaoRecebimentoServiceTest {

    @Mock
    private NotificacoesRepository notificacoesRepository;

    @Mock
    private PlanoContratadoRepository planoContratadoRepository;

    @Mock
    private PreferenciaNotificacaoRepository preferenciaNotificacaoRepository;

    @InjectMocks
    private NotificacaoRecebimentoService notificacaoRecebimentoService;

    @Captor
    private ArgumentCaptor<Notificacoes> notificacaoCaptor;

    @Test
    @DisplayName("notificarPagamentoConfirmado deve criar notificação quando preferência está ativada")
    void notificarPagamentoConfirmado_ComPreferenciaAtivada_DeveCriarNotificacao() {
        // ARRANGE
        PlanoContratadoEntity planoContratado = criarPlanoContratadoMock();

        when(planoContratadoRepository.findById(1)).thenReturn(Optional.of(planoContratado));
        when(preferenciaNotificacaoRepository.existsByPessoaIdAndTipoAndAtivadaTrue(
                1, TipoPreferenciaEnum.PAGAMENTO_REALIZADO))
                .thenReturn(true);

        // ACT
        notificacaoRecebimentoService.notificarPagamentoConfirmado(1);

        // ASSERT
        verify(notificacoesRepository).save(notificacaoCaptor.capture());
        Notificacoes notificacao = notificacaoCaptor.getValue();

        assertEquals(planoContratado.getAluno(), notificacao.getPessoa());
        assertEquals(TipoNotificacaoEnum.PAGAMENTO_REALIZADO, notificacao.getTipo());
        assertTrue(notificacao.getTitulo().contains("Plano Premium"));
        assertTrue(notificacao.getTitulo().contains("confirmado por"));
        assertTrue(notificacao.getTitulo().contains("Carlos Personal"));
        assertTrue(notificacao.getTitulo().contains("Seu plano está ativo!"));
        assertFalse(notificacao.getVisualizada());
        assertNotNull(notificacao.getDataCriacao());
    }

    @Test
    @DisplayName("notificarPagamentoConfirmado não deve criar notificação quando preferência está desativada")
    void notificarPagamentoConfirmado_ComPreferenciaDesativada_NaoDeveCriarNotificacao() {
        // ARRANGE
        PlanoContratadoEntity planoContratado = criarPlanoContratadoMock();

        when(planoContratadoRepository.findById(1)).thenReturn(Optional.of(planoContratado));
        when(preferenciaNotificacaoRepository.existsByPessoaIdAndTipoAndAtivadaTrue(
                1, TipoPreferenciaEnum.PAGAMENTO_REALIZADO))
                .thenReturn(false);

        // ACT
        notificacaoRecebimentoService.notificarPagamentoConfirmado(1);

        // ASSERT
        verify(notificacoesRepository, never()).save(any());
    }

    @Test
    @DisplayName("notificarPagamentoConfirmado deve lançar exceção quando plano não é encontrado")
    void notificarPagamentoConfirmado_PlanoNaoEncontrado_DeveLancarExcecao() {
        // ARRANGE
        when(planoContratadoRepository.findById(999)).thenReturn(Optional.empty());

        // ACT & ASSERT
        ApiExceptions.ResourceNotFoundException exception = assertThrows(
                ApiExceptions.ResourceNotFoundException.class,
                () -> notificacaoRecebimentoService.notificarPagamentoConfirmado(999)
        );

        assertEquals("Plano contratado com ID 999 não encontrado", exception.getMessage());
        verify(notificacoesRepository, never()).save(any());
    }

    @Test
    @DisplayName("notificarPagamentoNegado deve criar notificação com motivo quando informado")
    void notificarPagamentoNegado_ComMotivo_DeveCriarNotificacaoComMotivo() {
        // ARRANGE
        PlanoContratadoEntity planoContratado = criarPlanoContratadoMock();
        String motivo = "Comprovante inválido";

        when(planoContratadoRepository.findById(1)).thenReturn(Optional.of(planoContratado));
        when(preferenciaNotificacaoRepository.existsByPessoaIdAndTipoAndAtivadaTrue(
                1, TipoPreferenciaEnum.PAGAMENTO_CANCELADO))
                .thenReturn(true);

        // ACT
        notificacaoRecebimentoService.notificarPagamentoNegado(1, motivo);

        // ASSERT
        verify(notificacoesRepository).save(notificacaoCaptor.capture());
        Notificacoes notificacao = notificacaoCaptor.getValue();

        assertEquals(planoContratado.getAluno(), notificacao.getPessoa());
        assertEquals(TipoNotificacaoEnum.PAGAMENTO_CANCELADO, notificacao.getTipo());
        assertTrue(notificacao.getTitulo().contains("Plano Premium"));
        assertTrue(notificacao.getTitulo().contains("não foi confirmado por"));
        assertTrue(notificacao.getTitulo().contains("Carlos Personal"));
        assertTrue(notificacao.getTitulo().contains("Motivo: " + motivo));
        assertTrue(notificacao.getTitulo().contains("Entre em contato para resolver"));
        assertFalse(notificacao.getVisualizada());
        assertNotNull(notificacao.getDataCriacao());
    }

    @Test
    @DisplayName("notificarPagamentoNegado deve criar notificação sem motivo quando não informado")
    void notificarPagamentoNegado_SemMotivo_DeveCriarNotificacaoSemMotivo() {
        // ARRANGE
        PlanoContratadoEntity planoContratado = criarPlanoContratadoMock();

        when(planoContratadoRepository.findById(1)).thenReturn(Optional.of(planoContratado));
        when(preferenciaNotificacaoRepository.existsByPessoaIdAndTipoAndAtivadaTrue(
                1, TipoPreferenciaEnum.PAGAMENTO_CANCELADO))
                .thenReturn(true);

        // ACT
        notificacaoRecebimentoService.notificarPagamentoNegado(1, null);

        // ASSERT
        verify(notificacoesRepository).save(notificacaoCaptor.capture());
        Notificacoes notificacao = notificacaoCaptor.getValue();

        assertTrue(notificacao.getTitulo().contains("Plano Premium"));
        assertTrue(notificacao.getTitulo().contains("não foi confirmado por"));
        assertTrue(notificacao.getTitulo().contains("Carlos Personal"));
        assertFalse(notificacao.getTitulo().contains("Motivo:"));
        assertTrue(notificacao.getTitulo().contains("Entre em contato para resolver"));
    }

    @Test
    @DisplayName("notificarPagamentoNegado deve criar notificação sem motivo quando motivo está em branco")
    void notificarPagamentoNegado_MotivoEmBranco_DeveCriarNotificacaoSemMotivo() {
        // ARRANGE
        PlanoContratadoEntity planoContratado = criarPlanoContratadoMock();

        when(planoContratadoRepository.findById(1)).thenReturn(Optional.of(planoContratado));
        when(preferenciaNotificacaoRepository.existsByPessoaIdAndTipoAndAtivadaTrue(
                1, TipoPreferenciaEnum.PAGAMENTO_CANCELADO))
                .thenReturn(true);

        // ACT
        notificacaoRecebimentoService.notificarPagamentoNegado(1, "   ");

        // ASSERT
        verify(notificacoesRepository).save(notificacaoCaptor.capture());
        Notificacoes notificacao = notificacaoCaptor.getValue();

        assertFalse(notificacao.getTitulo().contains("Motivo:"));
    }

    @Test
    @DisplayName("notificarPagamentoNegado não deve criar notificação quando preferência está desativada")
    void notificarPagamentoNegado_ComPreferenciaDesativada_NaoDeveCriarNotificacao() {
        // ARRANGE
        PlanoContratadoEntity planoContratado = criarPlanoContratadoMock();

        when(planoContratadoRepository.findById(1)).thenReturn(Optional.of(planoContratado));
        when(preferenciaNotificacaoRepository.existsByPessoaIdAndTipoAndAtivadaTrue(
                1, TipoPreferenciaEnum.PAGAMENTO_CANCELADO))
                .thenReturn(false);

        // ACT
        notificacaoRecebimentoService.notificarPagamentoNegado(1, "Motivo qualquer");

        // ASSERT
        verify(notificacoesRepository, never()).save(any());
    }

    @Test
    @DisplayName("notificarPagamentoNegado deve lançar exceção quando plano não é encontrado")
    void notificarPagamentoNegado_PlanoNaoEncontrado_DeveLancarExcecao() {
        // ARRANGE
        when(planoContratadoRepository.findById(999)).thenReturn(Optional.empty());

        // ACT & ASSERT
        ApiExceptions.ResourceNotFoundException exception = assertThrows(
                ApiExceptions.ResourceNotFoundException.class,
                () -> notificacaoRecebimentoService.notificarPagamentoNegado(999, "Motivo")
        );

        assertEquals("Plano contratado com ID 999 não encontrado", exception.getMessage());
        verify(notificacoesRepository, never()).save(any());
    }

    @Test
    @DisplayName("notificarPagamentoConfirmado deve formatar título corretamente")
    void notificarPagamentoConfirmado_DeveFormatarTituloCorretamente() {
        // ARRANGE
        PlanoContratadoEntity planoContratado = criarPlanoContratadoMock();

        when(planoContratadoRepository.findById(1)).thenReturn(Optional.of(planoContratado));
        when(preferenciaNotificacaoRepository.existsByPessoaIdAndTipoAndAtivadaTrue(
                1, TipoPreferenciaEnum.PAGAMENTO_REALIZADO))
                .thenReturn(true);

        // ACT
        notificacaoRecebimentoService.notificarPagamentoConfirmado(1);

        // ASSERT
        verify(notificacoesRepository).save(notificacaoCaptor.capture());
        Notificacoes notificacao = notificacaoCaptor.getValue();

        String tituloEsperado = "Pagamento do plano 'Plano Premium' confirmado por Carlos Personal. Seu plano está ativo!";
        assertEquals(tituloEsperado, notificacao.getTitulo());
    }

    @Test
    @DisplayName("notificarPagamentoNegado deve formatar título corretamente com motivo")
    void notificarPagamentoNegado_DeveFormatarTituloCorretamenteComMotivo() {
        // ARRANGE
        PlanoContratadoEntity planoContratado = criarPlanoContratadoMock();
        String motivo = "Comprovante ilegível";

        when(planoContratadoRepository.findById(1)).thenReturn(Optional.of(planoContratado));
        when(preferenciaNotificacaoRepository.existsByPessoaIdAndTipoAndAtivadaTrue(
                1, TipoPreferenciaEnum.PAGAMENTO_CANCELADO))
                .thenReturn(true);

        // ACT
        notificacaoRecebimentoService.notificarPagamentoNegado(1, motivo);

        // ASSERT
        verify(notificacoesRepository).save(notificacaoCaptor.capture());
        Notificacoes notificacao = notificacaoCaptor.getValue();

        String tituloEsperado = "Pagamento do plano 'Plano Premium' não foi confirmado por Carlos Personal. Motivo: Comprovante ilegível. Entre em contato para resolver.";
        assertEquals(tituloEsperado, notificacao.getTitulo());
    }

    @Test
    @DisplayName("notificarPagamentoNegado deve formatar título corretamente sem motivo")
    void notificarPagamentoNegado_DeveFormatarTituloCorretamenteSemMotivo() {
        // ARRANGE
        PlanoContratadoEntity planoContratado = criarPlanoContratadoMock();

        when(planoContratadoRepository.findById(1)).thenReturn(Optional.of(planoContratado));
        when(preferenciaNotificacaoRepository.existsByPessoaIdAndTipoAndAtivadaTrue(
                1, TipoPreferenciaEnum.PAGAMENTO_CANCELADO))
                .thenReturn(true);

        // ACT
        notificacaoRecebimentoService.notificarPagamentoNegado(1, null);

        // ASSERT
        verify(notificacoesRepository).save(notificacaoCaptor.capture());
        Notificacoes notificacao = notificacaoCaptor.getValue();

        String tituloEsperado = "Pagamento do plano 'Plano Premium' não foi confirmado por Carlos Personal. Entre em contato para resolver.";
        assertEquals(tituloEsperado, notificacao.getTitulo());
    }

    // MÉTOODO AUXILIAR

    /**
     * Cria um PlanoContratadoEntity mock com todas as entidades relacionadas
     */
    private PlanoContratadoEntity criarPlanoContratadoMock() {
        Aluno aluno = new Aluno();
        aluno.setId(1);
        aluno.setNome("João Silva");

        PersonalTrainer personal = new PersonalTrainer();
        personal.setId(2);
        personal.setNome("Carlos Personal");

        Plano plano = new Plano();
        plano.setId(10);
        plano.setNome("Plano Premium");
        plano.setPersonalTrainer(personal);

        PlanoContratadoEntity planoContratado = new PlanoContratadoEntity();
        planoContratado.setId(1);
        planoContratado.setAluno(aluno);
        planoContratado.setPlano(plano);

        return planoContratado;
    }
}
