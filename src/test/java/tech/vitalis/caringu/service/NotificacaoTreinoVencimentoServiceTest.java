package tech.vitalis.caringu.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tech.vitalis.caringu.dtos.Notificacoes.NotificacaoTreinoPersonalDTO;
import tech.vitalis.caringu.repository.AlunoTreinoRepository;
import tech.vitalis.caringu.repository.NotificacoesRepository;
import tech.vitalis.caringu.repository.PreferenciaNotificacaoRepository;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
public class NotificacaoTreinoVencimentoServiceTest {

    @Mock
    private AlunoTreinoRepository alunoTreinoRepository;

    @Mock
    private NotificacoesRepository notificacoesRepository;

    @Mock
    private PreferenciaNotificacaoRepository preferenciaNotificacaoRepository;

    @Mock
    private NotificacaoEnviarService notificacaoEnviarService;

    @Autowired
    private NotificacaoTreinoVencimentoService notificacaoTreinoVencimentoService;

    @Test
    @BeforeEach
    void testeBuscaTreinosVencendo() {
        LocalDate hoje = LocalDate.now();
        LocalDate limite = hoje.plusWeeks(2);

        List<NotificacaoTreinoPersonalDTO> listaFake = List.of(
                new NotificacaoTreinoPersonalDTO(1, "CREF123", 100, hoje.plusDays(5)),
                new NotificacaoTreinoPersonalDTO(2, "CREF456", 101, hoje.plusDays(10))
        );

        Mockito.when(alunoTreinoRepository.findTreinosVencendo(limite))
                .thenReturn(listaFake);

        List<NotificacaoTreinoPersonalDTO> resultado = notificacaoTreinoVencimentoService.buscarTreinosVencendo(limite);

        Assertions.assertEquals(2, resultado.size());
        Assertions.assertEquals(1, resultado.get(0).personalTrainerId());
        Assertions.assertEquals("CREF123", resultado.get(0).cref());
    }

    // Aqui você pode criar outros testes para a lógica de envio etc
}
