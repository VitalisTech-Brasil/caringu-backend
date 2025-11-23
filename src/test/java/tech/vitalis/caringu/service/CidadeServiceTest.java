package tech.vitalis.caringu.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.vitalis.caringu.dtos.Cidade.CidadeResponseGetDTO;
import tech.vitalis.caringu.entity.Cidade;
import tech.vitalis.caringu.exception.Cidade.CidadeJaExisteException;
import tech.vitalis.caringu.exception.Cidade.CidadeNaoEncontradaException;
import tech.vitalis.caringu.mapper.CidadeMapper;
import tech.vitalis.caringu.repository.CidadeRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CidadeServiceTest {

    @Mock
    private CidadeRepository cidadeRepository;

    @Mock
    private CidadeMapper cidadeMapper;

    @InjectMocks
    private CidadeService cidadeService;

    @Test
    @DisplayName("listar deve retornar lista de DTOs mapeados")
    void listar_DeveRetornarLista() {
        Cidade cidade = new Cidade();
        when(cidadeRepository.findAll()).thenReturn(List.of(cidade));

        CidadeResponseGetDTO dto = mock(CidadeResponseGetDTO.class);
        when(cidadeMapper.toDTO(cidade)).thenReturn(dto);

        List<CidadeResponseGetDTO> resultado = cidadeService.listar();

        assertEquals(1, resultado.size());
        verify(cidadeRepository).findAll();
        verify(cidadeMapper).toDTO(cidade);
    }

    @Test
    @DisplayName("buscarPorId deve retornar DTO quando cidade existir")
    void buscarPorId_ComSucesso() {
        Cidade cidade = new Cidade();
        when(cidadeRepository.findById(1)).thenReturn(Optional.of(cidade));

        CidadeResponseGetDTO dto = mock(CidadeResponseGetDTO.class);
        when(cidadeMapper.toDTO(cidade)).thenReturn(dto);

        CidadeResponseGetDTO resultado = cidadeService.buscarPorId(1);

        assertNotNull(resultado);
        verify(cidadeRepository).findById(1);
        verify(cidadeMapper).toDTO(cidade);
    }

    @Test
    @DisplayName("buscarPorId deve lançar exceção quando cidade não existir")
    void buscarPorId_NaoEncontrada() {
        when(cidadeRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(CidadeNaoEncontradaException.class,
                () -> cidadeService.buscarPorId(1));
    }

    @Test
    @DisplayName("cadastrar deve lançar exceção quando cidade já existir")
    void cadastrar_CidadeJaExiste() {
        Cidade cidade = new Cidade();
        cidade.setNome("São Paulo");

        when(cidadeRepository.existsByNomeIgnoreCase("São Paulo"))
                .thenReturn(true);

        assertThrows(CidadeJaExisteException.class,
                () -> cidadeService.cadastrar(cidade));

        verify(cidadeRepository, never()).save(any());
    }

    @Test
    @DisplayName("deletar deve lançar exceção quando cidade não existir")
    void deletar_NaoEncontrada() {
        when(cidadeRepository.existsById(1)).thenReturn(false);

        assertThrows(CidadeNaoEncontradaException.class,
                () -> cidadeService.deletar(1));
    }
}


