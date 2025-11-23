package tech.vitalis.caringu.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.vitalis.caringu.dtos.ExecucaoExercicio.AtualizarStatusExecucaoExercicioRequestDTO;
import tech.vitalis.caringu.entity.ExecucaoExercicio;
import tech.vitalis.caringu.exception.ExecucaoExercicio.ExecucaoExercicioNaoEncontradaException;
import tech.vitalis.caringu.repository.ExecucaoExercicioRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExecucaoExercicioServiceTest {

    @Mock
    private ExecucaoExercicioRepository execucaoExercicioRepository;

    @InjectMocks
    private ExecucaoExercicioService execucaoExercicioService;

    @Test
    @DisplayName("atualizarStatusExecucao deve marcar execução como finalizada e salvar")
    void atualizarStatusExecucao_ComSucesso() {
        ExecucaoExercicio execucao = new ExecucaoExercicio();
        execucao.setFinalizado(false);

        when(execucaoExercicioRepository.findById(1)).thenReturn(Optional.of(execucao));

        AtualizarStatusExecucaoExercicioRequestDTO payload =
                new AtualizarStatusExecucaoExercicioRequestDTO(true);

        execucaoExercicioService.atualizarStatusExecucao(1, payload);

        assertTrue(execucao.getFinalizado());
        verify(execucaoExercicioRepository).save(execucao);
    }

    @Test
    @DisplayName("atualizarStatusExecucao deve lançar exceção quando execução não existir")
    void atualizarStatusExecucao_NaoEncontrada() {
        when(execucaoExercicioRepository.findById(1)).thenReturn(Optional.empty());

        AtualizarStatusExecucaoExercicioRequestDTO payload =
                new AtualizarStatusExecucaoExercicioRequestDTO(true);

        assertThrows(ExecucaoExercicioNaoEncontradaException.class,
                () -> execucaoExercicioService.atualizarStatusExecucao(1, payload));
    }
}


