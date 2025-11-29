package tech.vitalis.caringu.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.vitalis.caringu.consumer.NotificacaoEventoPublicacao;
import tech.vitalis.caringu.dtos.Notificacoes.NotificacaoPlanoVencimentoDto;
import tech.vitalis.caringu.entity.Aluno;
import tech.vitalis.caringu.entity.PersonalTrainer;
import tech.vitalis.caringu.entity.Plano;
import tech.vitalis.caringu.enums.Notificacoes.TipoNotificacaoEnum;
import tech.vitalis.caringu.enums.PreferenciaNotificacao.TipoPreferenciaEnum;
import tech.vitalis.caringu.core.domain.valueObject.StatusEnum;
import tech.vitalis.caringu.infrastructure.persistence.planoContratado.PlanoContratadoEntity;
import tech.vitalis.caringu.infrastructure.persistence.planoContratado.PlanoContratadoRepository;
import tech.vitalis.caringu.repository.NotificacoesRepository;
import tech.vitalis.caringu.repository.PreferenciaNotificacaoRepository;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificacaoPlanoVencimentoServiceTests {

    @Mock
    private PlanoContratadoRepository planoContratadoRepository;

    @Mock
    private PreferenciaNotificacaoRepository preferenciaNotificacaoRepository;

    @Mock
    private NotificacoesRepository notificacoesRepository;

    @Mock
    private NotificacaoEventoPublicacao notificacaoPublicador;

    @InjectMocks
    private NotificacaoPlanoVencimentoService notificacaoPlanoVencimentoService;

    @Captor
    private ArgumentCaptor<Integer> pessoaIdCaptor;

    @Captor
    private ArgumentCaptor<String> tipoDestinatarioCaptor;

    @Captor
    private ArgumentCaptor<String> nomeCaptor;

    @Captor
    private ArgumentCaptor<String> tituloCaptor;

    @Captor
    private ArgumentCaptor<LocalDate> dataFimCaptor;

    @Test
    @DisplayName("enviarNotificacoesPlanoVencimento deve processar planos vencendo nas próximas 2 semanas")
    void enviarNotificacoesPlanoVencimento_ComPlanosVencendo_DeveProcessarTodos() {
        // ARRANGE
        LocalDate hoje = LocalDate.now();
        LocalDate daquiDuasSemanas = hoje.plusWeeks(2);

        PlanoContratadoEntity plano1 = criarPlanoContratadoMock(1, hoje.plusDays(5));
        PlanoContratadoEntity plano2 = criarPlanoContratadoMock(2, hoje.plusDays(10));

        when(planoContratadoRepository.findByDataFimBetweenAndStatus(hoje, daquiDuasSemanas, StatusEnum.ATIVO))
                .thenReturn(List.of(plano1, plano2));

        // Simular preferências ativadas
        when(preferenciaNotificacaoRepository.existsByPessoaAndTipoAndAtivadaTrue(any(), eq(TipoPreferenciaEnum.PLANO_PROXIMO_VENCIMENTO)))
                .thenReturn(true);

        // Simular que não existem notificações prévias
        when(notificacoesRepository.existsByAlunoIdAndTipoAndVisualizadaFalse(anyInt(), eq(TipoNotificacaoEnum.PLANO_PROXIMO_VENCIMENTO)))
                .thenReturn(false);
        when(notificacoesRepository.existsByPersonalIdAndTipoAndVisualizadaFalse(anyInt(), eq(TipoNotificacaoEnum.PLANO_PROXIMO_VENCIMENTO)))
                .thenReturn(false);

        // ACT
        notificacaoPlanoVencimentoService.enviarNotificacoesPlanoVencimento();

        // ASSERT
        verify(planoContratadoRepository).findByDataFimBetweenAndStatus(hoje, daquiDuasSemanas, StatusEnum.ATIVO);
        verify(notificacaoPublicador, times(4)).publicarEventoPlanoVencimentoComTitulo(
                anyInt(), anyString(), anyString(), anyString(), any(LocalDate.class)
        ); // 2 planos × 2 notificações (aluno + personal)
    }

    @Test
    @DisplayName("enviarNotificacoesPlanoVencimento não deve processar quando não há planos vencendo")
    void enviarNotificacoesPlanoVencimento_SemPlanos_NaoDevePublicarEventos() {
        // ARRANGE
        LocalDate hoje = LocalDate.now();
        LocalDate daquiDuasSemanas = hoje.plusWeeks(2);

        when(planoContratadoRepository.findByDataFimBetweenAndStatus(hoje, daquiDuasSemanas, StatusEnum.ATIVO))
                .thenReturn(List.of());

        // ACT
        notificacaoPlanoVencimentoService.enviarNotificacoesPlanoVencimento();

        // ASSERT
        verify(notificacaoPublicador, never()).publicarEventoPlanoVencimentoComTitulo(
                anyInt(), anyString(), anyString(), anyString(), any(LocalDate.class)
        );
    }

    @Test
    @DisplayName("enviarNotificacoesPlanoVencimento deve publicar evento para aluno com preferência ativada")
    void enviarNotificacoesPlanoVencimento_AlunoComPreferencia_DevePublicarEvento() {
        // ARRANGE
        LocalDate hoje = LocalDate.now();
        LocalDate dataVencimento = hoje.plusDays(7);
        PlanoContratadoEntity plano = criarPlanoContratadoMock(1, dataVencimento);

        when(planoContratadoRepository.findByDataFimBetweenAndStatus(any(), any(), eq(StatusEnum.ATIVO)))
                .thenReturn(List.of(plano));

        // Aluno prefere receber
        when(preferenciaNotificacaoRepository.existsByPessoaAndTipoAndAtivadaTrue(
                eq(plano.getAluno()), eq(TipoPreferenciaEnum.PLANO_PROXIMO_VENCIMENTO)))
                .thenReturn(true);

        // Personal prefere não receber
        when(preferenciaNotificacaoRepository.existsByPessoaAndTipoAndAtivadaTrue(
                eq(plano.getPlano().getPersonalTrainer()), eq(TipoPreferenciaEnum.PLANO_PROXIMO_VENCIMENTO)))
                .thenReturn(false);

        when(notificacoesRepository.existsByAlunoIdAndTipoAndVisualizadaFalse(anyInt(), any()))
                .thenReturn(false);

        // ACT
        notificacaoPlanoVencimentoService.enviarNotificacoesPlanoVencimento();

        // ASSERT
        verify(notificacaoPublicador, times(1)).publicarEventoPlanoVencimentoComTitulo(
                pessoaIdCaptor.capture(),
                tipoDestinatarioCaptor.capture(),
                nomeCaptor.capture(),
                tituloCaptor.capture(),
                dataFimCaptor.capture()
        );

        assertEquals(1, pessoaIdCaptor.getValue());
        assertEquals("ALUNO", tipoDestinatarioCaptor.getValue());
        assertEquals("João Silva", nomeCaptor.getValue());
        assertTrue(tituloCaptor.getValue().contains("Plano Premium"));
        assertTrue(tituloCaptor.getValue().contains("vencerá em breve"));
        assertEquals(dataVencimento, dataFimCaptor.getValue());
    }

    @Test
    @DisplayName("enviarNotificacoesPlanoVencimento deve publicar evento para personal com preferência ativada")
    void enviarNotificacoesPlanoVencimento_PersonalComPreferencia_DevePublicarEvento() {
        // ARRANGE
        LocalDate hoje = LocalDate.now();
        LocalDate dataVencimento = hoje.plusDays(7);
        PlanoContratadoEntity plano = criarPlanoContratadoMock(1, dataVencimento);

        when(planoContratadoRepository.findByDataFimBetweenAndStatus(any(), any(), eq(StatusEnum.ATIVO)))
                .thenReturn(List.of(plano));

        // Aluno prefere não receber
        when(preferenciaNotificacaoRepository.existsByPessoaAndTipoAndAtivadaTrue(
                eq(plano.getAluno()), eq(TipoPreferenciaEnum.PLANO_PROXIMO_VENCIMENTO)))
                .thenReturn(false);

        // Personal prefere receber
        when(preferenciaNotificacaoRepository.existsByPessoaAndTipoAndAtivadaTrue(
                eq(plano.getPlano().getPersonalTrainer()), eq(TipoPreferenciaEnum.PLANO_PROXIMO_VENCIMENTO)))
                .thenReturn(true);

        when(notificacoesRepository.existsByPersonalIdAndTipoAndVisualizadaFalse(anyInt(), any()))
                .thenReturn(false);

        // ACT
        notificacaoPlanoVencimentoService.enviarNotificacoesPlanoVencimento();

        // ASSERT
        verify(notificacaoPublicador, times(1)).publicarEventoPlanoVencimentoComTitulo(
                pessoaIdCaptor.capture(),
                tipoDestinatarioCaptor.capture(),
                nomeCaptor.capture(),
                tituloCaptor.capture(),
                dataFimCaptor.capture()
        );

        assertEquals(2, pessoaIdCaptor.getValue());
        assertEquals("PERSONAL", tipoDestinatarioCaptor.getValue());
        assertEquals("Carlos Personal", nomeCaptor.getValue());
        assertTrue(tituloCaptor.getValue().contains("João Silva"));
        assertTrue(tituloCaptor.getValue().contains("Plano Premium"));
        assertEquals(dataVencimento, dataFimCaptor.getValue());
    }

    @Test
    @DisplayName("enviarNotificacoesPlanoVencimento não deve publicar quando notificação já existe para aluno")
    void enviarNotificacoesPlanoVencimento_NotificacaoAlunoExistente_NaoDevePublicar() {
        // ARRANGE
        PlanoContratadoEntity plano = criarPlanoContratadoMock(1, LocalDate.now().plusDays(7));

        when(planoContratadoRepository.findByDataFimBetweenAndStatus(any(), any(), eq(StatusEnum.ATIVO)))
                .thenReturn(List.of(plano));

        when(preferenciaNotificacaoRepository.existsByPessoaAndTipoAndAtivadaTrue(
                eq(plano.getAluno()), eq(TipoPreferenciaEnum.PLANO_PROXIMO_VENCIMENTO)))
                .thenReturn(true);

        // Notificação já existe
        when(notificacoesRepository.existsByAlunoIdAndTipoAndVisualizadaFalse(
                eq(1), eq(TipoNotificacaoEnum.PLANO_PROXIMO_VENCIMENTO)))
                .thenReturn(true);

        when(preferenciaNotificacaoRepository.existsByPessoaAndTipoAndAtivadaTrue(
                eq(plano.getPlano().getPersonalTrainer()), eq(TipoPreferenciaEnum.PLANO_PROXIMO_VENCIMENTO)))
                .thenReturn(false);

        // ACT
        notificacaoPlanoVencimentoService.enviarNotificacoesPlanoVencimento();

        // ASSERT
        verify(notificacaoPublicador, never()).publicarEventoPlanoVencimentoComTitulo(
                eq(1), eq("ALUNO"), anyString(), anyString(), any()
        );
    }

    @Test
    @DisplayName("enviarNotificacoesPlanoVencimento não deve publicar quando notificação já existe para personal")
    void enviarNotificacoesPlanoVencimento_NotificacaoPersonalExistente_NaoDevePublicar() {
        // ARRANGE
        PlanoContratadoEntity plano = criarPlanoContratadoMock(1, LocalDate.now().plusDays(7));

        when(planoContratadoRepository.findByDataFimBetweenAndStatus(any(), any(), eq(StatusEnum.ATIVO)))
                .thenReturn(List.of(plano));

        when(preferenciaNotificacaoRepository.existsByPessoaAndTipoAndAtivadaTrue(
                eq(plano.getAluno()), eq(TipoPreferenciaEnum.PLANO_PROXIMO_VENCIMENTO)))
                .thenReturn(false);

        when(preferenciaNotificacaoRepository.existsByPessoaAndTipoAndAtivadaTrue(
                eq(plano.getPlano().getPersonalTrainer()), eq(TipoPreferenciaEnum.PLANO_PROXIMO_VENCIMENTO)))
                .thenReturn(true);

        // Notificação já existe
        when(notificacoesRepository.existsByPersonalIdAndTipoAndVisualizadaFalse(
                eq(2), eq(TipoNotificacaoEnum.PLANO_PROXIMO_VENCIMENTO)))
                .thenReturn(true);

        // ACT
        notificacaoPlanoVencimentoService.enviarNotificacoesPlanoVencimento();

        // ASSERT
        verify(notificacaoPublicador, never()).publicarEventoPlanoVencimentoComTitulo(
                eq(2), eq("PERSONAL"), anyString(), anyString(), any()
        );
    }

    @Test
    @DisplayName("enviarNotificacoesPlanoVencimento não deve publicar quando ambos têm preferência desativada")
    void enviarNotificacoesPlanoVencimento_AmbosSemPreferencia_NaoDevePublicar() {
        // ARRANGE
        PlanoContratadoEntity plano = criarPlanoContratadoMock(1, LocalDate.now().plusDays(7));

        when(planoContratadoRepository.findByDataFimBetweenAndStatus(any(), any(), eq(StatusEnum.ATIVO)))
                .thenReturn(List.of(plano));

        // Ambos preferem não receber
        when(preferenciaNotificacaoRepository.existsByPessoaAndTipoAndAtivadaTrue(
                any(), eq(TipoPreferenciaEnum.PLANO_PROXIMO_VENCIMENTO)))
                .thenReturn(false);

        // ACT
        notificacaoPlanoVencimentoService.enviarNotificacoesPlanoVencimento();

        // ASSERT
        verify(notificacaoPublicador, never()).publicarEventoPlanoVencimentoComTitulo(
                anyInt(), anyString(), anyString(), anyString(), any()
        );
    }

    @Test
    @DisplayName("buscarNotificacoesPlanoVencimento deve retornar lista de notificações até data limite")
    void buscarNotificacoesPlanoVencimento_ComDataLimite_DeveRetornarLista() {
        // ARRANGE
        LocalDate dataLimite = LocalDate.now().plusWeeks(2);

        NotificacaoPlanoVencimentoDto notif1 = mock(NotificacaoPlanoVencimentoDto.class);
        NotificacaoPlanoVencimentoDto notif2 = mock(NotificacaoPlanoVencimentoDto.class);

        when(planoContratadoRepository.findNotificacoesPlanoVencimento(dataLimite))
                .thenReturn(List.of(notif1, notif2));

        // ACT
        List<NotificacaoPlanoVencimentoDto> resultado =
                notificacaoPlanoVencimentoService.buscarNotificacoesPlanoVencimento(dataLimite);

        // ASSERT
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(planoContratadoRepository).findNotificacoesPlanoVencimento(dataLimite);
    }

    @Test
    @DisplayName("buscarNotificacoesPlanoVencimento deve retornar lista vazia quando não há planos")
    void buscarNotificacoesPlanoVencimento_SemPlanos_DeveRetornarListaVazia() {
        // ARRANGE
        LocalDate dataLimite = LocalDate.now().plusWeeks(2);

        when(planoContratadoRepository.findNotificacoesPlanoVencimento(dataLimite))
                .thenReturn(List.of());

        // ACT
        List<NotificacaoPlanoVencimentoDto> resultado =
                notificacaoPlanoVencimentoService.buscarNotificacoesPlanoVencimento(dataLimite);

        // ASSERT
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    @Test
    @DisplayName("buscarNotificacoesPlanoVencimentoPorPersonal deve retornar notificações filtradas por personal")
    void buscarNotificacoesPlanoVencimentoPorPersonal_ComPersonalId_DeveRetornarListaFiltrada() {
        // ARRANGE
        LocalDate dataLimite = LocalDate.now().plusWeeks(2);
        Integer personalId = 2;

        NotificacaoPlanoVencimentoDto notif1 = mock(NotificacaoPlanoVencimentoDto.class);
        NotificacaoPlanoVencimentoDto notif2 = mock(NotificacaoPlanoVencimentoDto.class);

        when(planoContratadoRepository.findNotificacoesPlanoVencimentoPorPersonal(dataLimite, personalId))
                .thenReturn(List.of(notif1, notif2));

        // ACT
        List<NotificacaoPlanoVencimentoDto> resultado =
                notificacaoPlanoVencimentoService.buscarNotificacoesPlanoVencimentoPorPersonal(dataLimite, personalId);

        // ASSERT
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(planoContratadoRepository).findNotificacoesPlanoVencimentoPorPersonal(dataLimite, personalId);
    }

    @Test
    @DisplayName("buscarNotificacoesPlanoVencimentoPorPersonal deve retornar lista vazia quando personal não tem planos")
    void buscarNotificacoesPlanoVencimentoPorPersonal_PersonalSemPlanos_DeveRetornarListaVazia() {
        // ARRANGE
        LocalDate dataLimite = LocalDate.now().plusWeeks(2);
        Integer personalId = 999;

        when(planoContratadoRepository.findNotificacoesPlanoVencimentoPorPersonal(dataLimite, personalId))
                .thenReturn(List.of());

        // ACT
        List<NotificacaoPlanoVencimentoDto> resultado =
                notificacaoPlanoVencimentoService.buscarNotificacoesPlanoVencimentoPorPersonal(dataLimite, personalId);

        // ASSERT
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    @Test
    @DisplayName("enviarNotificacoesPlanoVencimento deve formatar data corretamente no título")
    void enviarNotificacoesPlanoVencimento_DeveFormatarDataCorretamente() {
        // ARRANGE
        LocalDate dataVencimento = LocalDate.of(2024, 12, 15);
        PlanoContratadoEntity plano = criarPlanoContratadoMock(1, dataVencimento);

        when(planoContratadoRepository.findByDataFimBetweenAndStatus(any(), any(), eq(StatusEnum.ATIVO)))
                .thenReturn(List.of(plano));

        when(preferenciaNotificacaoRepository.existsByPessoaAndTipoAndAtivadaTrue(
                eq(plano.getAluno()), eq(TipoPreferenciaEnum.PLANO_PROXIMO_VENCIMENTO)))
                .thenReturn(true);

        when(preferenciaNotificacaoRepository.existsByPessoaAndTipoAndAtivadaTrue(
                eq(plano.getPlano().getPersonalTrainer()), eq(TipoPreferenciaEnum.PLANO_PROXIMO_VENCIMENTO)))
                .thenReturn(false);

        when(notificacoesRepository.existsByAlunoIdAndTipoAndVisualizadaFalse(anyInt(), any()))
                .thenReturn(false);

        // ACT
        notificacaoPlanoVencimentoService.enviarNotificacoesPlanoVencimento();

        // ASSERT
        verify(notificacaoPublicador).publicarEventoPlanoVencimentoComTitulo(
                anyInt(), eq("ALUNO"), anyString(), tituloCaptor.capture(), any()
        );

        assertTrue(tituloCaptor.getValue().contains("15/12/2024"));
    }

    //  MÉTODOS AUXILIARES

    /**
     * Cria um PlanoContratadoEntity mock com todas as entidades relacionadas
     */
    private PlanoContratadoEntity criarPlanoContratadoMock(Integer id, LocalDate dataFim) {
        // Aluno
        Aluno aluno = new Aluno();
        aluno.setId(1);
        aluno.setNome("João Silva");

        // Personal
        PersonalTrainer personal = new PersonalTrainer();
        personal.setId(2);
        personal.setNome("Carlos Personal");

        // Plano
        Plano plano = new Plano();
        plano.setId(10);
        plano.setNome("Plano Premium");
        plano.setPersonalTrainer(personal);

        // Plano Contratado
        PlanoContratadoEntity planoContratado = new PlanoContratadoEntity();
        planoContratado.setId(id);
        planoContratado.setAluno(aluno);
        planoContratado.setPlano(plano);
        planoContratado.setStatus(StatusEnum.ATIVO);
        planoContratado.setDataContratacao(LocalDate.now().minusMonths(1));
        planoContratado.setDataFim(dataFim);

        return planoContratado;
    }
}
