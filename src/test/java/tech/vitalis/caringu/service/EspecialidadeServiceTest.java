package tech.vitalis.caringu.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.vitalis.caringu.dtos.Especialidade.EspecialidadeResponseGetDTO;
import tech.vitalis.caringu.entity.Especialidade;
import tech.vitalis.caringu.mapper.EspecialidadeMapper;
import tech.vitalis.caringu.repository.EspecialidadeRepository;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EspecialidadeServiceTest {

    @Mock
    private EspecialidadeRepository especialidadeRepository;

    @Mock
    private EspecialidadeMapper especialidadeMapper;

    @InjectMocks
    private EspecialidadeService especialidadeService;

    private Especialidade musculacao;
    private Especialidade crossfit;

    @BeforeEach
    void setUp() {
        musculacao = new Especialidade(1, "Musculação", new ArrayList<>());
        crossfit = new Especialidade(2, "Crossfit", new ArrayList<>());
    }

    @Test
    @DisplayName("listarTodas deve retornar lista mapeada quando há especialidades")
    void listarTodas_DeveRetornarListaMapeada() {
        // ARRANGE
        when(especialidadeRepository.findAll()).thenReturn(List.of(musculacao, crossfit));

        EspecialidadeResponseGetDTO dto1 = new EspecialidadeResponseGetDTO(1, "Musculação");
        EspecialidadeResponseGetDTO dto2 = new EspecialidadeResponseGetDTO(2, "Crossfit");
        when(especialidadeMapper.toDTO(musculacao)).thenReturn(dto1);
        when(especialidadeMapper.toDTO(crossfit)).thenReturn(dto2);

        // ACT
        List<EspecialidadeResponseGetDTO> resultado = especialidadeService.listarTodas();

        // ASSERT
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertIterableEquals(List.of(dto1, dto2), resultado);
        verify(especialidadeRepository).findAll();
        verify(especialidadeMapper).toDTO(musculacao);
        verify(especialidadeMapper).toDTO(crossfit);
        verifyNoMoreInteractions(especialidadeRepository, especialidadeMapper);
    }

    @Test
    @DisplayName("listarTodas deve retornar lista vazia quando não existem especialidades")
    void listarTodas_DeveRetornarListaVazia() {
        // ARRANGE
        when(especialidadeRepository.findAll()).thenReturn(List.of());

        // ACT
        List<EspecialidadeResponseGetDTO> resultado = especialidadeService.listarTodas();

        // ASSERT
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(especialidadeRepository).findAll();
        verifyNoInteractions(especialidadeMapper);
    }

    @Test
    @DisplayName("listarTodas deve mapear todas as especialidades de lista maior")
    void listarTodas_DeveMapearTodosItens() {
        // ARRANGE
        Especialidade yoga = new Especialidade(3, "Yoga", new ArrayList<>());
        when(especialidadeRepository.findAll()).thenReturn(List.of(musculacao, crossfit, yoga));

        EspecialidadeResponseGetDTO dto1 = new EspecialidadeResponseGetDTO(1, "Musculação");
        EspecialidadeResponseGetDTO dto2 = new EspecialidadeResponseGetDTO(2, "Crossfit");
        EspecialidadeResponseGetDTO dto3 = new EspecialidadeResponseGetDTO(3, "Yoga");
        when(especialidadeMapper.toDTO(musculacao)).thenReturn(dto1);
        when(especialidadeMapper.toDTO(crossfit)).thenReturn(dto2);
        when(especialidadeMapper.toDTO(yoga)).thenReturn(dto3);

        // ACT
        List<EspecialidadeResponseGetDTO> resultado = especialidadeService.listarTodas();

        // ASSERT
        assertEquals(3, resultado.size());
        assertIterableEquals(List.of(dto1, dto2, dto3), resultado);
        verify(especialidadeRepository).findAll();
        verify(especialidadeMapper).toDTO(musculacao);
        verify(especialidadeMapper).toDTO(crossfit);
        verify(especialidadeMapper).toDTO(yoga);
        verifyNoMoreInteractions(especialidadeRepository, especialidadeMapper);
    }
}
