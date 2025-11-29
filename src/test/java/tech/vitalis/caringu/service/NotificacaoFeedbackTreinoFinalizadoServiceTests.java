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
import tech.vitalis.caringu.entity.*;
import tech.vitalis.caringu.enums.Notificacoes.TipoNotificacaoEnum;
import tech.vitalis.caringu.enums.PreferenciaNotificacao.TipoPreferenciaEnum;
import tech.vitalis.caringu.infrastructure.persistence.planoContratado.PlanoContratadoEntity;
import tech.vitalis.caringu.repository.AulaRepository;
import tech.vitalis.caringu.repository.NotificacoesRepository;
import tech.vitalis.caringu.repository.PreferenciaNotificacaoRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificacaoFeedbackTreinoFinalizadoServiceTests {

    @Mock
    private NotificacoesRepository notificacoesRepository;

    @Mock
    private AulaRepository aulaRepository;

    @Mock
    private PreferenciaNotificacaoRepository preferenciaNotificacaoRepository;

    @InjectMocks
    private NotificacaoFeedbackTreinoFinalizadoService notificacaoFeedbackTreinoFinalizadoService;

    @Captor
    private ArgumentCaptor<Notificacoes> notificacaoCaptor;

    @Test
    @DisplayName("notificarTreinoFinalizado deve criar notificações para personal e aluno quando ambos têm preferência ativada")
    void notificarTreinoFinalizado_ComPreferenciasAtivadas_DeveCriarDuasNotificacoes() {
        // ARRANGE
        Aula aula = criarAulaMock();

        when(aulaRepository.findById(1)).thenReturn(Optional.of(aula));
        when(preferenciaNotificacaoRepository.existsByPessoaIdAndTipoAndAtivadaTrue(2, TipoPreferenciaEnum.FEEDBACK_TREINO))
                .thenReturn(true); // Personal
        when(preferenciaNotificacaoRepository.existsByPessoaIdAndTipoAndAtivadaTrue(3, TipoPreferenciaEnum.FEEDBACK_TREINO))
                .thenReturn(true); // Aluno

        // ACT
        notificacaoFeedbackTreinoFinalizadoService.notificarTreinoFinalizado(1);

        // ASSERT
        verify(notificacoesRepository, times(2)).save(any(Notificacoes.class));
        verify(aulaRepository).findById(1);
        verify(preferenciaNotificacaoRepository, times(2))
                .existsByPessoaIdAndTipoAndAtivadaTrue(anyInt(), eq(TipoPreferenciaEnum.FEEDBACK_TREINO));
    }

    @Test
    @DisplayName("notificarTreinoFinalizado deve lançar exceção quando aula não é encontrada")
    void notificarTreinoFinalizado_AulaNaoEncontrada_DeveLancarExcecao() {
        // ARRANGE
        when(aulaRepository.findById(999)).thenReturn(Optional.empty());

        // ACT & ASSERT
        ApiExceptions.ResourceNotFoundException exception = assertThrows(
                ApiExceptions.ResourceNotFoundException.class,
                () -> notificacaoFeedbackTreinoFinalizadoService.notificarTreinoFinalizado(999)
        );

        assertEquals("Aula com ID 999 não encontrada", exception.getMessage());
        verify(notificacoesRepository, never()).save(any());
    }

    @Test
    @DisplayName("notificarPersonal deve criar notificação com mensagem formatada corretamente")
    void notificarPersonal_ComDataHorarioFim_DeveCriarNotificacaoFormatada() {
        // ARRANGE
        LocalDateTime dataHorarioFim = LocalDateTime.of(2024, 11, 29, 14, 30);
        Aula aula = criarAulaMock();
        aula.setDataHorarioFim(dataHorarioFim);

        when(preferenciaNotificacaoRepository.existsByPessoaIdAndTipoAndAtivadaTrue(2, TipoPreferenciaEnum.FEEDBACK_TREINO))
                .thenReturn(true);

        // ACT
        notificacaoFeedbackTreinoFinalizadoService.notificarPersonal(aula);

        // ASSERT
        verify(notificacoesRepository).save(notificacaoCaptor.capture());
        Notificacoes notificacao = notificacaoCaptor.getValue();

        assertEquals(aula.getPlanoContratado().getPlano().getPersonalTrainer(), notificacao.getPessoa());
        assertEquals(TipoNotificacaoEnum.FEEDBACK_TREINO, notificacao.getTipo());
        assertTrue(notificacao.getTitulo().contains("João Silva")); // Nome do aluno
        assertTrue(notificacao.getTitulo().contains("29/11/2024 às 14:30"));
        assertTrue(notificacao.getTitulo().contains("finalizado"));
        assertTrue(notificacao.getTitulo().contains("Deixe seu feedback!"));
        assertFalse(notificacao.getVisualizada());
        assertNotNull(notificacao.getDataCriacao());
    }

    @Test
    @DisplayName("notificarPersonal deve usar texto padrão quando dataHorarioFim é nula")
    void notificarPersonal_SemDataHorarioFim_DeveUsarTextoDefault() {
        // ARRANGE
        Aula aula = criarAulaMock();
        aula.setDataHorarioFim(null);

        when(preferenciaNotificacaoRepository.existsByPessoaIdAndTipoAndAtivadaTrue(2, TipoPreferenciaEnum.FEEDBACK_TREINO))
                .thenReturn(true);

        // ACT
        notificacaoFeedbackTreinoFinalizadoService.notificarPersonal(aula);

        // ASSERT
        verify(notificacoesRepository).save(notificacaoCaptor.capture());
        Notificacoes notificacao = notificacaoCaptor.getValue();

        assertTrue(notificacao.getTitulo().contains("data nao definida"));
        assertTrue(notificacao.getTitulo().contains("João Silva"));
    }

    @Test
    @DisplayName("notificarPersonal não deve criar notificação quando preferência está desativada")
    void notificarPersonal_PreferenciaDesativada_NaoDeveCriarNotificacao() {
        // ARRANGE
        Aula aula = criarAulaMock();

        when(preferenciaNotificacaoRepository.existsByPessoaIdAndTipoAndAtivadaTrue(2, TipoPreferenciaEnum.FEEDBACK_TREINO))
                .thenReturn(false);

        // ACT
        notificacaoFeedbackTreinoFinalizadoService.notificarPersonal(aula);

        // ASSERT
        verify(notificacoesRepository, never()).save(any());
        verify(preferenciaNotificacaoRepository).existsByPessoaIdAndTipoAndAtivadaTrue(2, TipoPreferenciaEnum.FEEDBACK_TREINO);
    }

    @Test
    @DisplayName("notificarAluno deve criar notificação com mensagem formatada corretamente")
    void notificarAluno_ComDataHorarioFim_DeveCriarNotificacaoFormatada() {
        // ARRANGE
        LocalDateTime dataHorarioFim = LocalDateTime.of(2024, 11, 29, 16, 45);
        Aula aula = criarAulaMock();
        aula.setDataHorarioFim(dataHorarioFim);

        when(preferenciaNotificacaoRepository.existsByPessoaIdAndTipoAndAtivadaTrue(3, TipoPreferenciaEnum.FEEDBACK_TREINO))
                .thenReturn(true);

        // ACT
        notificacaoFeedbackTreinoFinalizadoService.notificarAluno(aula);

        // ASSERT
        verify(notificacoesRepository).save(notificacaoCaptor.capture());
        Notificacoes notificacao = notificacaoCaptor.getValue();

        assertEquals(aula.getPlanoContratado().getAluno(), notificacao.getPessoa());
        assertEquals(TipoNotificacaoEnum.FEEDBACK_TREINO, notificacao.getTipo());
        assertTrue(notificacao.getTitulo().contains("Carlos Personal")); // Nome do personal
        assertTrue(notificacao.getTitulo().contains("29/11/2024 às 16:45"));
        assertTrue(notificacao.getTitulo().contains("finalizado"));
        assertTrue(notificacao.getTitulo().contains("Como foi?"));
        assertTrue(notificacao.getTitulo().contains("Deixe seu feedback!"));
        assertFalse(notificacao.getVisualizada());
        assertNotNull(notificacao.getDataCriacao());
    }

    @Test
    @DisplayName("notificarAluno deve usar texto padrão quando dataHorarioFim é nula")
    void notificarAluno_SemDataHorarioFim_DeveUsarTextoDefault() {
        // ARRANGE
        Aula aula = criarAulaMock();
        aula.setDataHorarioFim(null);

        when(preferenciaNotificacaoRepository.existsByPessoaIdAndTipoAndAtivadaTrue(3, TipoPreferenciaEnum.FEEDBACK_TREINO))
                .thenReturn(true);

        // ACT
        notificacaoFeedbackTreinoFinalizadoService.notificarAluno(aula);

        // ASSERT
        verify(notificacoesRepository).save(notificacaoCaptor.capture());
        Notificacoes notificacao = notificacaoCaptor.getValue();

        assertTrue(notificacao.getTitulo().contains("data nao definida"));
        assertTrue(notificacao.getTitulo().contains("Carlos Personal"));
    }

    @Test
    @DisplayName("notificarAluno não deve criar notificação quando preferência está desativada")
    void notificarAluno_PreferenciaDesativada_NaoDeveCriarNotificacao() {
        // ARRANGE
        Aula aula = criarAulaMock();

        when(preferenciaNotificacaoRepository.existsByPessoaIdAndTipoAndAtivadaTrue(3, TipoPreferenciaEnum.FEEDBACK_TREINO))
                .thenReturn(false);

        // ACT
        notificacaoFeedbackTreinoFinalizadoService.notificarAluno(aula);

        // ASSERT
        verify(notificacoesRepository, never()).save(any());
        verify(preferenciaNotificacaoRepository).existsByPessoaIdAndTipoAndAtivadaTrue(3, TipoPreferenciaEnum.FEEDBACK_TREINO);
    }

    @Test
    @DisplayName("notificarTreinoFinalizado deve criar apenas notificação do personal quando aluno tem preferência desativada")
    void notificarTreinoFinalizado_ApenasPersonalComPreferencia_DeveCriarUmaNotificacao() {
        // ARRANGE
        Aula aula = criarAulaMock();

        when(aulaRepository.findById(1)).thenReturn(Optional.of(aula));
        when(preferenciaNotificacaoRepository.existsByPessoaIdAndTipoAndAtivadaTrue(2, TipoPreferenciaEnum.FEEDBACK_TREINO))
                .thenReturn(true); // Personal ativado
        when(preferenciaNotificacaoRepository.existsByPessoaIdAndTipoAndAtivadaTrue(3, TipoPreferenciaEnum.FEEDBACK_TREINO))
                .thenReturn(false); // Aluno desativado

        // ACT
        notificacaoFeedbackTreinoFinalizadoService.notificarTreinoFinalizado(1);

        // ASSERT
        verify(notificacoesRepository, times(1)).save(any(Notificacoes.class));
    }

    @Test
    @DisplayName("notificarTreinoFinalizado deve criar apenas notificação do aluno quando personal tem preferência desativada")
    void notificarTreinoFinalizado_ApenasAlunoComPreferencia_DeveCriarUmaNotificacao() {
        // ARRANGE
        Aula aula = criarAulaMock();

        when(aulaRepository.findById(1)).thenReturn(Optional.of(aula));
        when(preferenciaNotificacaoRepository.existsByPessoaIdAndTipoAndAtivadaTrue(2, TipoPreferenciaEnum.FEEDBACK_TREINO))
                .thenReturn(false); // Personal desativado
        when(preferenciaNotificacaoRepository.existsByPessoaIdAndTipoAndAtivadaTrue(3, TipoPreferenciaEnum.FEEDBACK_TREINO))
                .thenReturn(true); // Aluno ativado

        // ACT
        notificacaoFeedbackTreinoFinalizadoService.notificarTreinoFinalizado(1);

        // ASSERT
        verify(notificacoesRepository, times(1)).save(any(Notificacoes.class));
    }

    @Test
    @DisplayName("notificarTreinoFinalizado não deve criar notificações quando ambos têm preferência desativada")
    void notificarTreinoFinalizado_AmbosSemPreferencia_NaoDeveCriarNotificacoes() {
        // ARRANGE
        Aula aula = criarAulaMock();

        when(aulaRepository.findById(1)).thenReturn(Optional.of(aula));
        when(preferenciaNotificacaoRepository.existsByPessoaIdAndTipoAndAtivadaTrue(anyInt(), eq(TipoPreferenciaEnum.FEEDBACK_TREINO)))
                .thenReturn(false);

        // ACT
        notificacaoFeedbackTreinoFinalizadoService.notificarTreinoFinalizado(1);

        // ASSERT
        verify(notificacoesRepository, never()).save(any());
    }

    // ==================== MÉTODOS AUXILIARES ====================

    /**
     * Cria uma aula mock com todas as entidades relacionadas configuradas
     */
    private Aula criarAulaMock() {
        // Personal
        PersonalTrainer personal = new PersonalTrainer();
        personal.setId(2);
        personal.setNome("Carlos Personal");

        // Plano
        Plano plano = new Plano();
        plano.setPersonalTrainer(personal);

        // Aluno
        Aluno aluno = new Aluno();
        aluno.setId(3);
        aluno.setNome("João Silva");

        // Plano Contratado Entity
        PlanoContratadoEntity planoContratado = new PlanoContratadoEntity();
        planoContratado.setPlano(plano);
        planoContratado.setAluno(aluno);

        // Aula
        Aula aula = new Aula();
        aula.setId(1);
        aula.setPlanoContratado(planoContratado);
        aula.setDataHorarioFim(LocalDateTime.of(2024, 11, 29, 14, 30));

        return aula;
    }
}
