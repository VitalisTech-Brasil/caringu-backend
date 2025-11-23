package tech.vitalis.caringu.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.vitalis.caringu.dtos.AulaTreinoExercicio.AcompanhamentoAulaCruDTO;
import tech.vitalis.caringu.dtos.AulaTreinoExercicio.AulaComTreinoDTO;
import tech.vitalis.caringu.dtos.AulaTreinoExercicio.AulaComTreinoModeloCruDTO;
import tech.vitalis.caringu.dtos.AulaTreinoExercicio.Response.AcompanhamentoAulaResponseDTO;
import tech.vitalis.caringu.dtos.AulaTreinoExercicio.Response.VisualizarAulasResponseDTO;
import tech.vitalis.caringu.dtos.AulaTreinoExercicio.TreinoDetalhadoRepositoryDTO;
import tech.vitalis.caringu.enums.Aula.AulaStatusEnum;
import tech.vitalis.caringu.enums.Exercicio.GrupoMuscularEnum;
import tech.vitalis.caringu.exception.Aula.AulaNaoEncontradaException;
import tech.vitalis.caringu.exception.AulaTreinoExercicio.AulaTreinoExercicioNaoEncontradaException;
import tech.vitalis.caringu.mapper.AulaTreinoExercicioMapper;
import tech.vitalis.caringu.repository.AulaTreinoExercicioRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AulaTreinoExercicioServiceTest {

    @Mock
    private AulaTreinoExercicioMapper aulaTreinoExercicioMapper;

    @Mock
    private AulaTreinoExercicioRepository aulaTreinoExercicioRepository;

    @InjectMocks
    private AulaTreinoExercicioService aulaTreinoExercicioService;

    @Test
    @DisplayName("listarAulasComTreinosExercicios deve montar resposta quando existirem dados")
    void listarAulasComTreinosExercicios_ComSucesso() {
        TreinoDetalhadoRepositoryDTO dto = new TreinoDetalhadoRepositoryDTO(
                10,
                "Treino Força",
                1,
                1,
                100,
                "Supino Reto",
                60,
                "3x10",
                "40kg",
                GrupoMuscularEnum.PEITORAL,
                "Manter postura",
                "https://video.com/supino",
                false
        );

        when(aulaTreinoExercicioRepository.listarAulasComTreinosExercicios(1, 2))
                .thenReturn(List.of(dto));

        VisualizarAulasResponseDTO resposta =
                aulaTreinoExercicioService.listarAulasComTreinosExercicios(1, 2);

        assertNotNull(resposta);
        assertEquals(10, resposta.idTreino());
        assertEquals("Treino Força", resposta.nomeTreino());
        assertEquals(1, resposta.exercicios().size());
    }

    @Test
    @DisplayName("listarAulasComTreinosExercicios deve lançar exceção quando não encontrar dados")
    void listarAulasComTreinosExercicios_AulaNaoEncontrada() {
        when(aulaTreinoExercicioRepository.listarAulasComTreinosExercicios(1, 2))
                .thenReturn(List.of());

        assertThrows(AulaNaoEncontradaException.class,
                () -> aulaTreinoExercicioService.listarAulasComTreinosExercicios(1, 2));
    }

    @Test
    @DisplayName("listarProximasAulas deve limitar resultado a 2 aulas")
    void listarProximasAulas_DeveLimitarQuantidade() {
        List<AulaComTreinoModeloCruDTO> listaCrua = List.of(
                mock(AulaComTreinoModeloCruDTO.class)
        );
        when(aulaTreinoExercicioRepository.listarProximasAulas(1))
                .thenReturn(listaCrua);

        List<AulaComTreinoDTO> mapeadas = List.of(
                mock(AulaComTreinoDTO.class),
                mock(AulaComTreinoDTO.class),
                mock(AulaComTreinoDTO.class)
        );
        when(aulaTreinoExercicioMapper.toAulaComTreinoDTO(listaCrua))
                .thenReturn(mapeadas);

        List<AulaComTreinoDTO> resultado = aulaTreinoExercicioService.listarProximasAulas(1);

        assertEquals(2, resultado.size());
        verify(aulaTreinoExercicioRepository).listarProximasAulas(1);
        verify(aulaTreinoExercicioMapper).toAulaComTreinoDTO(listaCrua);
    }

    @Test
    @DisplayName("listarAcompanhamentoDaAula deve retornar DTO quando existirem linhas")
    void listarAcompanhamentoDaAula_ComSucesso() {
        AcompanhamentoAulaCruDTO linha = new AcompanhamentoAulaCruDTO(
                1,
                2,
                AulaStatusEnum.AGENDADO,
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(1),
                10,
                "Treino Força",
                100,
                "Supino",
                null,
                null,
                null,
                null,
                null,
                null,
                GrupoMuscularEnum.PEITORAL,
                false
        );

        when(aulaTreinoExercicioRepository.listarAcompanharDaAula(1))
                .thenReturn(List.of(linha));

        AcompanhamentoAulaResponseDTO dtoResposta = mock(AcompanhamentoAulaResponseDTO.class);
        when(aulaTreinoExercicioMapper.toAcompanhamentoAulaResponseDTO(anyList()))
                .thenReturn(dtoResposta);

        AcompanhamentoAulaResponseDTO resultado =
                aulaTreinoExercicioService.listarAcompanhamentoDaAula(1);

        assertNotNull(resultado);
        verify(aulaTreinoExercicioRepository).listarAcompanharDaAula(1);
        verify(aulaTreinoExercicioMapper).toAcompanhamentoAulaResponseDTO(anyList());
    }

    @Test
    @DisplayName("listarAcompanhamentoDaAula deve lançar exceção quando não houver dados")
    void listarAcompanhamentoDaAula_SemDados() {
        when(aulaTreinoExercicioRepository.listarAcompanharDaAula(1))
                .thenReturn(List.of());

        assertThrows(AulaTreinoExercicioNaoEncontradaException.class,
                () -> aulaTreinoExercicioService.listarAcompanhamentoDaAula(1));
    }
}


