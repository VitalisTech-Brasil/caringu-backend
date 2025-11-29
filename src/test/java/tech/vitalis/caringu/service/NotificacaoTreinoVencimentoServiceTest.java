package tech.vitalis.caringu.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.vitalis.caringu.repository.NotificacoesRepository;
import tech.vitalis.caringu.repository.PreferenciaNotificacaoRepository;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class NotificacaoTreinoVencimentoServiceTest {

    @Mock
    private NotificacoesRepository notificacoesRepository;

    @Mock
    private PreferenciaNotificacaoRepository preferenciaNotificacaoRepository;

    @Mock
    private NotificacaoEnviarService notificacaoEnviarService;

    @InjectMocks
    private NotificacaoTreinoVencimentoService notificacaoTreinoVencimentoService;

    @Test
    @DisplayName("Ta safe")
    void deveCriarServiceComDependencias() {
        // ARRANGE & ACT

        // ASSERT
        assertNotNull(notificacaoTreinoVencimentoService);
        assertNotNull(notificacoesRepository);
        assertNotNull(preferenciaNotificacaoRepository);
        assertNotNull(notificacaoEnviarService);
    }


}
