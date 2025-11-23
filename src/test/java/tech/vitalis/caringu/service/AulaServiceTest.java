package tech.vitalis.caringu.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import tech.vitalis.caringu.dtos.Aula.Request.AulasAlunoRequestDTO;
import tech.vitalis.caringu.dtos.Aula.Response.AulasAgendadasResponseDTO;
import tech.vitalis.caringu.dtos.Aula.Response.AulasAlunoFeedbackResponseDTO;
import tech.vitalis.caringu.dtos.Aula.TotalAulasAgendamentoResponseGetDTO;
import tech.vitalis.caringu.dtos.Feedback.FeedbackCountDTO;
import tech.vitalis.caringu.dtos.SessaoTreino.HorasTreinadasResponseDTO;
import tech.vitalis.caringu.dtos.SessaoTreino.HorasTreinadasSemanaMesDTO;
import tech.vitalis.caringu.entity.Aula;
import tech.vitalis.caringu.enums.Aula.AulaStatusEnum;
import tech.vitalis.caringu.exception.SessaoTreino.SessaoTreinoNaoEncontradoException;
import tech.vitalis.caringu.repository.AulaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AulaServiceTest {

    @Mock
    private AulaRepository aulaRepository;

    @Mock
    private NotificacaoFeedbackTreinoFinalizadoService notificacaoFeedbackTreinoFinalizadoService;

    @InjectMocks
    private AulaService aulaService;

    @Test
    @DisplayName("listarInfoAulaPorPersonal deve retornar primeiro elemento ou null")
    void listarInfoAulaPorPersonal_ComResultadosENulo() {
        AulasAgendadasResponseDTO dto = mock(AulasAgendadasResponseDTO.class);
        when(aulaRepository.findAllInfoAulaPorPersonal(1, 2))
                .thenReturn(List.of(dto));

        AulasAgendadasResponseDTO resultado = aulaService.listarInfoAulaPorPersonal(1, 2);
        assertNotNull(resultado);

        when(aulaRepository.findAllInfoAulaPorPersonal(1, 3))
                .thenReturn(List.of());

        assertNull(aulaService.listarInfoAulaPorPersonal(1, 3));
    }

    @Test
    @DisplayName("buscarHorasTreinadas deve mapear corretamente objetos do repository")
    void buscarHorasTreinadas_ComSucesso() {
        List<Object[]> lista = getObjects();

        when(aulaRepository.buscarHorasAgrupadasPorAlunoExercicio(1, 2))
                .thenReturn(lista);

        HorasTreinadasResponseDTO resposta = aulaService.buscarHorasTreinadas(1, 2);

        assertEquals(1, resposta.idAluno());
        assertEquals(2, resposta.idExercicio());
        assertEquals(1, resposta.dados().size());
        HorasTreinadasSemanaMesDTO item = resposta.dados().getFirst();
        assertEquals(2025, item.ano());
        assertEquals(10.5, item.horasTreinadas());
    }

    private static List<Object[]> getObjects() {
        Object[] linha = new Object[]{
                1,                       // idAluno
                "Aluno Teste",          // nomeAluno
                2,                       // idExercicio
                "Supino",               // nomeExercicio
                2025,                    // ano
                5,                       // mes
                20,                      // anoSemana
                10.5                     // horasTreinadas
        };
        List<Object[]> lista = new ArrayList<>();
        lista.add(linha);
        return lista;
    }

    @Test
    @DisplayName("buscarDisponibilidadeDeAulas deve delegar para repository")
    void buscarDisponibilidadeDeAulas_ComSucesso() {
        TotalAulasAgendamentoResponseGetDTO dto = mock(TotalAulasAgendamentoResponseGetDTO.class);
        when(aulaRepository.buscarDisponibilidadeDeAulas(1)).thenReturn(dto);

        TotalAulasAgendamentoResponseGetDTO resultado =
                aulaService.buscarDisponibilidadeDeAulas(1);

        assertNotNull(resultado);
        verify(aulaRepository).buscarDisponibilidadeDeAulas(1);
    }

    @Test
    @DisplayName("listarAulasPorAlunoComPlano deve montar página de resposta com feedbacks")
    void listarAulasPorAlunoComPlano_ComSucesso() {
        LocalDate hoje = LocalDate.now();
        AulasAlunoRequestDTO req = new AulasAlunoRequestDTO(
                1,
                LocalDateTime.of(2025, 1, 1, 10, 0),
                LocalDateTime.of(2025, 1, 1, 11, 0),
                "Personal X",
                10,
                "Treino Y"
        );

        Page<AulasAlunoRequestDTO> page =
                new PageImpl<>(List.of(req), PageRequest.of(0, 10), 1);
        when(aulaRepository.listarAulasPorAlunoComPlano(eq(1), eq(hoje), any(), any(), any()))
                .thenReturn(page);

        FeedbackCountDTO feedbackCount = new FeedbackCountDTO(1, 3);
        when(aulaRepository.buscarQuantidadeFeedbacksPorAulas(List.of(1)))
                .thenReturn(List.of(feedbackCount));

        Page<AulasAlunoFeedbackResponseDTO> resultado =
                aulaService.listarAulasPorAlunoComPlano(1, PageRequest.of(0, 10), hoje);

        assertEquals(1, resultado.getTotalElements());
        AulasAlunoFeedbackResponseDTO item = resultado.getContent().getFirst();
        assertEquals(3, item.qtdFeedbacks());
    }

    @Test
    @DisplayName("atualizarStatus deve atualizar status e datas corretamente e notificar quando realizado")
    void atualizarStatus_ComSucesso() {
        Aula aula = new Aula();
        aula.setId(1);
        aula.setStatus(AulaStatusEnum.AGENDADO);

        when(aulaRepository.findById(1)).thenReturn(java.util.Optional.of(aula));

        // REALIZADO
        aulaService.atualizarStatus(1, AulaStatusEnum.REALIZADO);
        assertEquals(AulaStatusEnum.REALIZADO, aula.getStatus());
        assertNotNull(aula.getDataHorarioFim());
        verify(notificacaoFeedbackTreinoFinalizadoService)
                .notificarTreinoFinalizado(1);

        // AGENDADO novamente reseta data fim
        aulaService.atualizarStatus(1, AulaStatusEnum.AGENDADO);
        assertNull(aula.getDataHorarioFim());
        assertEquals(AulaStatusEnum.AGENDADO, aula.getStatus());
    }

    @Test
    @DisplayName("atualizarStatus deve lançar exceção quando aula não existir")
    void atualizarStatus_NaoEncontrada() {
        when(aulaRepository.findById(1)).thenReturn(java.util.Optional.empty());

        assertThrows(SessaoTreinoNaoEncontradoException.class,
                () -> aulaService.atualizarStatus(1, AulaStatusEnum.REALIZADO));
    }

    @Test
    @DisplayName("deletarAulasRascunhos deve deletar apenas aulas em rascunho")
    void deletarAulasRascunhos_ComSucesso() {
        Aula aulaRascunho = new Aula();
        aulaRascunho.setId(1);
        aulaRascunho.setStatus(AulaStatusEnum.RASCUNHO);

        Aula aulaAgendada = new Aula();
        aulaAgendada.setId(2);
        aulaAgendada.setStatus(AulaStatusEnum.AGENDADO);

        when(aulaRepository.findAllById(List.of(1, 2)))
                .thenReturn(List.of(aulaRascunho, aulaAgendada));

        aulaService.deletarAulasRascunhos(List.of(1, 2));

        verify(aulaRepository).deleteAll(List.of(aulaRascunho));
    }

    @Test
    @DisplayName("deletarAulasRascunhos não deve fazer nada quando lista for nula ou vazia")
    void deletarAulasRascunhos_ListaVazia() {
        aulaService.deletarAulasRascunhos(null);
        aulaService.deletarAulasRascunhos(List.of());

        verify(aulaRepository, never()).deleteAll(anyList());
    }
}


