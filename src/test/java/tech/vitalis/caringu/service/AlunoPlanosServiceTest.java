package tech.vitalis.caringu.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.vitalis.caringu.dtos.AlunoPlanos.AlunoPlanosDTO;
import tech.vitalis.caringu.mapper.AlunoTreinosMapper;
import tech.vitalis.caringu.repository.AlunoPlanosRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AlunoPlanosServiceTest {

    @Mock
    private AlunoPlanosRepository repository;

    @Mock
    private AlunoTreinosMapper mapper;

    @InjectMocks
    private AlunoPlanosService alunoPlanosService;

    private Object[] resultadoArray1;
    private Object[] resultadoArray2;
    private AlunoPlanosDTO planoDto1;
    private AlunoPlanosDTO planoDto2;

    @BeforeEach
    void setUp() {
        // Simular Object[] retornado pela query nativa
        resultadoArray1 = new Object[]{1L, "Plano Mensal", "ATIVO", 12};
        resultadoArray2 = new Object[]{2L, "Plano Trimestral", "ATIVO", 24};

        // DTOs mapeados - usar mock pois o mapper faz a conversão complexa
        planoDto1 = mock(AlunoPlanosDTO.class);
        planoDto2 = mock(AlunoPlanosDTO.class);
    }

    @Test
    @DisplayName("buscarPlanosPorAluno deve retornar lista de planos quando existem")
    void buscarPlanosPorAluno_ComPlanos_DeveRetornarListaMapeada() {
        // ARRANGE
        List<Object[]> resultados = Collections.singletonList(resultadoArray1);
        when(repository.findPlanosByAlunoId(1)).thenReturn(resultados);
        when(mapper.toAlunoPlanosDTO(resultadoArray1)).thenReturn(planoDto1);

        // ACT
        List<AlunoPlanosDTO> resultado = alunoPlanosService.buscarPlanosPorAluno(1);

        // ASSERT
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(planoDto1, resultado.get(0));
        verify(repository).findPlanosByAlunoId(1);
        verify(mapper).toAlunoPlanosDTO(resultadoArray1);
    }

    @Test
    @DisplayName("buscarPlanosPorAluno deve retornar múltiplos planos quando aluno tem vários")
    void buscarPlanosPorAluno_ComMultiplosPlanos_DeveRetornarTodos() {
        // ARRANGE
        List<Object[]> resultados = Arrays.asList(resultadoArray1, resultadoArray2);
        when(repository.findPlanosByAlunoId(1)).thenReturn(resultados);
        when(mapper.toAlunoPlanosDTO(resultadoArray1)).thenReturn(planoDto1);
        when(mapper.toAlunoPlanosDTO(resultadoArray2)).thenReturn(planoDto2);

        // ACT
        List<AlunoPlanosDTO> resultado = alunoPlanosService.buscarPlanosPorAluno(1);

        // ASSERT
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals(planoDto1, resultado.get(0));
        assertEquals(planoDto2, resultado.get(1));
        verify(repository).findPlanosByAlunoId(1);
        verify(mapper, times(2)).toAlunoPlanosDTO(any(Object[].class));
    }

    @Test
    @DisplayName("buscarPlanosPorAluno deve lançar exceção quando não há planos")
    void buscarPlanosPorAluno_SemPlanos_DeveLancarExcecao() {
        // ARRANGE
        when(repository.findPlanosByAlunoId(1)).thenReturn(Collections.emptyList());

        // ACT & ASSERT
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> alunoPlanosService.buscarPlanosPorAluno(1)
        );

        assertEquals("Nenhum plano encontrado para o aluno com ID: 1", exception.getMessage());
        verify(repository).findPlanosByAlunoId(1);
        verify(mapper, never()).toAlunoPlanosDTO(any());
    }

    @Test
    @DisplayName("buscarPlanosPorAluno deve mapear corretamente cada Object[] para AlunoPlanosDTO")
    void buscarPlanosPorAluno_DeveChamarMapperParaCadaResultado() {
        // ARRANGE
        when(repository.findPlanosByAlunoId(1)).thenReturn(List.of(resultadoArray1, resultadoArray2));
        when(mapper.toAlunoPlanosDTO(resultadoArray1)).thenReturn(planoDto1);
        when(mapper.toAlunoPlanosDTO(resultadoArray2)).thenReturn(planoDto2);

        // ACT
        List<AlunoPlanosDTO> resultado = alunoPlanosService.buscarPlanosPorAluno(1);

        // ASSERT
        verify(mapper).toAlunoPlanosDTO(resultadoArray1);
        verify(mapper).toAlunoPlanosDTO(resultadoArray2);
        assertEquals(2, resultado.size());
    }

    @Test
    @DisplayName("buscarPlanosPorAluno deve funcionar com diferentes IDs de aluno")
    void buscarPlanosPorAluno_ComDiferentesIds_DeveBuscarCorretamente() {
        // ARRANGE
        List<Object[]> resultados = Collections.singletonList(resultadoArray1);
        when(repository.findPlanosByAlunoId(5)).thenReturn(resultados);
        when(mapper.toAlunoPlanosDTO(resultadoArray1)).thenReturn(planoDto1);

        // ACT
        List<AlunoPlanosDTO> resultado = alunoPlanosService.buscarPlanosPorAluno(5);

        // ASSERT
        assertNotNull(resultado);
        verify(repository).findPlanosByAlunoId(5); // Verifica ID correto
    }

    @Test
    @DisplayName("buscarPlanosPorAluno deve processar stream corretamente")
    void buscarPlanosPorAluno_DeveProcessarStreamCorretamente() {
        // ARRANGE
        Object[] resultado3 = new Object[]{3L, "Plano Anual", "ATIVO", 36};
        AlunoPlanosDTO planoDto3 = mock(AlunoPlanosDTO.class);

        when(repository.findPlanosByAlunoId(1))
                .thenReturn(List.of(resultadoArray1, resultadoArray2, resultado3));
        when(mapper.toAlunoPlanosDTO(resultadoArray1)).thenReturn(planoDto1);
        when(mapper.toAlunoPlanosDTO(resultadoArray2)).thenReturn(planoDto2);
        when(mapper.toAlunoPlanosDTO(resultado3)).thenReturn(planoDto3);

        // ACT
        List<AlunoPlanosDTO> resultado = alunoPlanosService.buscarPlanosPorAluno(1);

        // ASSERT
        assertEquals(3, resultado.size()); // Stream processou todos
        assertTrue(resultado.contains(planoDto1));
        assertTrue(resultado.contains(planoDto2));
        assertTrue(resultado.contains(planoDto3));
    }
}
