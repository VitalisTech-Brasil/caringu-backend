package tech.vitalis.caringu.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tech.vitalis.caringu.dtos.TreinoExercicio.TreinoExercicioAssociacaoRequestDTO;
import tech.vitalis.caringu.dtos.TreinoExercicio.TreinoExercicioRequestPostDto;
import tech.vitalis.caringu.dtos.TreinoExercicio.TreinoExercicioResponseGetDto;
import tech.vitalis.caringu.entity.Exercicio;
import tech.vitalis.caringu.entity.Treino;
import tech.vitalis.caringu.entity.TreinoExercicio;
import tech.vitalis.caringu.enums.TreinoExercicio.GrauDificuldadeEnum;
import tech.vitalis.caringu.enums.TreinoExercicio.OrigemTreinoExercicioEnum;
import tech.vitalis.caringu.exception.ApiExceptions;
import tech.vitalis.caringu.exception.Treino.TreinoNaoEncontradoException;
import tech.vitalis.caringu.mapper.TreinoExercicioMapper;
import tech.vitalis.caringu.repository.ExercicioRepository;
import tech.vitalis.caringu.repository.TreinoExercicioRepository;
import tech.vitalis.caringu.repository.TreinoRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TreinoExercicioServiceTest {

    @Mock
    private TreinoExercicioRepository treinoExercicioRepository;

    @Mock
    private TreinoRepository treinoRepository;

    @Mock
    private ExercicioRepository exercicioRepository;

    @Mock
    private TreinoExercicioMapper treinoExercicioMapper;

    @InjectMocks
    private TreinoExercicioService treinoExercicioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void cadastrarComVariosExercicios_Sucesso() {
        // Dados de entrada
        Integer treinoId = 1;
        Treino treino = new Treino();
        treino.setId(treinoId);

        TreinoExercicioRequestPostDto dto = mock(TreinoExercicioRequestPostDto.class);
        when(dto.exercicioId()).thenReturn(10);
        when(dto.origemTreinoExercicio()).thenReturn(OrigemTreinoExercicioEnum.valueOf("BIBLIOTECA"));
        when(dto.grauDificuldade()).thenReturn(GrauDificuldadeEnum.valueOf("INICIANTE"));

        TreinoExercicioAssociacaoRequestDTO requestDTO = mock(TreinoExercicioAssociacaoRequestDTO.class);
        when(requestDTO.idTreino()).thenReturn(treinoId);
        when(requestDTO.exercicios()).thenReturn(Collections.singletonList(dto));

        Exercicio exercicio = new Exercicio();
        exercicio.setId(10);

        TreinoExercicio treinoExercicio = new TreinoExercicio();
        treinoExercicio.setId(100);
        treinoExercicio.setTreino(treino);
        treinoExercicio.setExercicio(exercicio);

        TreinoExercicioResponseGetDto responseDto = mock(TreinoExercicioResponseGetDto.class);

        // Mock comportamento dos repositórios
        when(treinoRepository.findById(treinoId)).thenReturn(Optional.of(treino));
        when(exercicioRepository.findById(10)).thenReturn(Optional.of(exercicio));
        when(treinoExercicioRepository.existsByTreino_IdAndExercicio_Id(treinoId, 10)).thenReturn(false);
        when(treinoExercicioMapper.toEntity(dto)).thenReturn(treinoExercicio);
        when(treinoExercicioRepository.saveAll(anyList())).thenReturn(Collections.singletonList(treinoExercicio));
        when(treinoExercicioMapper.toResponseDTO(treinoExercicio)).thenReturn(responseDto);

        // Execução
        List<TreinoExercicioResponseGetDto> resultado = treinoExercicioService.cadastrarComVariosExercicios(requestDTO);

        // Verificações
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(responseDto, resultado.get(0));

        verify(treinoRepository, times(1)).findById(treinoId);
        verify(exercicioRepository, times(1)).findById(10);
        verify(treinoExercicioRepository, times(1)).existsByTreino_IdAndExercicio_Id(treinoId, 10);
        verify(treinoExercicioRepository, times(1)).saveAll(anyList());
    }

    @Test
    void cadastrarComVariosExercicios_TreinoNaoEncontrado() {
        Integer treinoId = 1;

        TreinoExercicioAssociacaoRequestDTO requestDTO = mock(TreinoExercicioAssociacaoRequestDTO.class);
        when(requestDTO.idTreino()).thenReturn(treinoId);

        when(treinoRepository.findById(treinoId)).thenReturn(Optional.empty());

        TreinoNaoEncontradoException exception = assertThrows(TreinoNaoEncontradoException.class,
                () -> treinoExercicioService.cadastrarComVariosExercicios(requestDTO));

        assertTrue(exception.getMessage().contains("Treino com o ID " + treinoId + " não encontrado"));
    }

    @Test
    void cadastrarComVariosExercicios_ExercicioJaAssociado() {
        Integer treinoId = 1;
        Integer exercicioId = 10;

        Treino treino = new Treino();
        treino.setId(treinoId);

        TreinoExercicioRequestPostDto dto = mock(TreinoExercicioRequestPostDto.class);
        when(dto.exercicioId()).thenReturn(exercicioId);
        when(dto.origemTreinoExercicio()).thenReturn(OrigemTreinoExercicioEnum.valueOf("BIBLIOTECA"));
        when(dto.grauDificuldade()).thenReturn(GrauDificuldadeEnum.valueOf("INICIANTE"));

        TreinoExercicioAssociacaoRequestDTO requestDTO = mock(TreinoExercicioAssociacaoRequestDTO.class);
        when(requestDTO.idTreino()).thenReturn(treinoId);
        when(requestDTO.exercicios()).thenReturn(Collections.singletonList(dto));

        Exercicio exercicio = new Exercicio();
        exercicio.setId(exercicioId);

        when(treinoRepository.findById(treinoId)).thenReturn(Optional.of(treino));
        when(exercicioRepository.findById(exercicioId)).thenReturn(Optional.of(exercicio));
        when(treinoExercicioRepository.existsByTreino_IdAndExercicio_Id(treinoId, exercicioId)).thenReturn(true);

        ApiExceptions.BadRequestException exception = assertThrows(ApiExceptions.BadRequestException.class,
                () -> treinoExercicioService.cadastrarComVariosExercicios(requestDTO));

        assertTrue(exception.getMessage().contains("já está associado ao treino com ID " + treinoId));
    }
}
