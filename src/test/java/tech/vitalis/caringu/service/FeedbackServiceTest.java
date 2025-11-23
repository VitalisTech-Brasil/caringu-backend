package tech.vitalis.caringu.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.vitalis.caringu.dtos.Feedback.*;
import tech.vitalis.caringu.entity.Aula;
import tech.vitalis.caringu.entity.Feedback;
import tech.vitalis.caringu.entity.Pessoa;
import tech.vitalis.caringu.enums.Feedback.TipoAutorEnum;
import tech.vitalis.caringu.repository.AulaRepository;
import tech.vitalis.caringu.repository.FeedbackRepository;
import tech.vitalis.caringu.repository.PessoaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FeedbackServiceTest {

    @Mock
    private FeedbackRepository feedbackRepository;

    @Mock
    private AulaRepository aulaRepository;

    @Mock
    private PessoaRepository pessoaRepository;

    @InjectMocks
    private FeedbackService feedbackService;

    @Test
    @DisplayName("buscarFeedbacksPorAula deve agrupar feedbacks por data")
    void buscarFeedbacksPorAula_ComSucesso() {
        LocalDateTime dataAula = LocalDateTime.of(2025, 1, 10, 10, 0);
        LocalDateTime dataCriacao = LocalDateTime.of(2025, 1, 10, 11, 0);

        FeedbackPorAulaDTO dto = new FeedbackPorAulaDTO(
                dataAula,
                TipoAutorEnum.ALUNO,
                1,
                "Comentário",
                dataCriacao
        );

        when(feedbackRepository.buscarFeedbacksPorAula(1))
                .thenReturn(List.of(dto));

        List<FeedbackPorDataDTO> resultado = feedbackService.buscarFeedbacksPorAula(1);

        assertEquals(1, resultado.size());
        FeedbackPorDataDTO dia = resultado.getFirst();
        assertEquals(LocalDate.of(2025, 1, 10).toString(), dia.data());
        assertEquals(1, dia.feedbacks().size());
        FeedbackItemDTO item = dia.feedbacks().getFirst();
        assertEquals("ALUNO", item.autorTipo());
        assertEquals("Comentário", item.descricao());
    }

    @Test
    @DisplayName("criarFeedback deve salvar feedback e devolver resposta")
    void criarFeedback_ComSucesso() {
        CriacaoFeedbackDto dto = new CriacaoFeedbackDto(
                2,
                1,
                TipoAutorEnum.ALUNO,
                "Muito bom",
                LocalDateTime.of(2025, 1, 10, 12, 0)
        );

        Aula aula = new Aula();
        aula.setId(1);
        when(aulaRepository.findById(1)).thenReturn(Optional.of(aula));

        Pessoa pessoa = new Pessoa();
        pessoa.setId(2);
        when(pessoaRepository.findById(2)).thenReturn(Optional.of(pessoa));

        Feedback salvo = new Feedback(
                10,
                aula,
                pessoa,
                "Muito bom",
                dto.dataCriacao(),
                dto.autorTipo(),
                null
        );

        when(feedbackRepository.save(any(Feedback.class))).thenReturn(salvo);

        RespostaFeedbackDto resposta = feedbackService.criarFeedback(dto);

        assertNotNull(resposta);
        assertEquals(2, resposta.autorId());
        assertEquals(1, resposta.aulaId());
        assertEquals(TipoAutorEnum.ALUNO.toString(), resposta.autorTipo());
        assertEquals("Muito bom", resposta.descricao());
    }
}


