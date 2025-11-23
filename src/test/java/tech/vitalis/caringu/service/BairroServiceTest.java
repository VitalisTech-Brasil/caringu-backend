package tech.vitalis.caringu.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.vitalis.caringu.dtos.Bairro.BairroResponseGetDTO;
import tech.vitalis.caringu.entity.Bairro;
import tech.vitalis.caringu.entity.Cidade;
import tech.vitalis.caringu.exception.Bairro.BairroJaExisteException;
import tech.vitalis.caringu.exception.Bairro.BairroNaoEncontradoException;
import tech.vitalis.caringu.mapper.BairroMapper;
import tech.vitalis.caringu.repository.BairroRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BairroServiceTest {

    @Mock
    private BairroRepository bairroRepository;

    @Mock
    private BairroMapper bairroMapper;

    @InjectMocks
    private BairroService bairroService;

    @Test
    @DisplayName("listar deve retornar lista de DTOs mapeados")
    void listar_DeveRetornarLista() {
        Bairro bairro = new Bairro();
        when(bairroRepository.findAll()).thenReturn(List.of(bairro));

        BairroResponseGetDTO dto = mock(BairroResponseGetDTO.class);
        when(bairroMapper.toDTO(bairro)).thenReturn(dto);

        List<BairroResponseGetDTO> resultado = bairroService.listar();

        assertEquals(1, resultado.size());
        verify(bairroRepository).findAll();
        verify(bairroMapper).toDTO(bairro);
    }

    @Test
    @DisplayName("buscarPorId deve retornar DTO quando bairro existir")
    void buscarPorId_ComSucesso() {
        Bairro bairro = new Bairro();
        when(bairroRepository.findById(1)).thenReturn(Optional.of(bairro));

        BairroResponseGetDTO dto = mock(BairroResponseGetDTO.class);
        when(bairroMapper.toDTO(bairro)).thenReturn(dto);

        BairroResponseGetDTO resultado = bairroService.buscarPorId(1);

        assertNotNull(resultado);
        verify(bairroRepository).findById(1);
        verify(bairroMapper).toDTO(bairro);
    }

    @Test
    @DisplayName("buscarPorId deve lançar exceção quando bairro não existir")
    void buscarPorId_NaoEncontrado() {
        when(bairroRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(BairroNaoEncontradoException.class,
                () -> bairroService.buscarPorId(1));
    }

    @Test
    @DisplayName("cadastrar deve lançar exceção quando bairro já existe na mesma cidade")
    void cadastrar_BairroJaExiste() {
        Cidade cidade = new Cidade();
        cidade.setId(10);

        Bairro bairro = new Bairro();
        bairro.setNome("Centro");
        bairro.setCidade(cidade);

        when(bairroRepository.existsByNomeIgnoreCaseAndCidadeId("Centro", 10))
                .thenReturn(true);

        assertThrows(BairroJaExisteException.class,
                () -> bairroService.cadastrar(bairro));

        verify(bairroRepository, never()).save(any());
    }

    @Test
    @DisplayName("deletar deve lançar exceção quando bairro não existir")
    void deletar_NaoEncontrado() {
        when(bairroRepository.existsById(1)).thenReturn(false);

        assertThrows(BairroNaoEncontradoException.class,
                () -> bairroService.deletar(1));
    }
}


