package tech.vitalis.caringu.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import tech.vitalis.caringu.dtos.Avaliacao.AvaliacaoRequestDTO;
import tech.vitalis.caringu.dtos.Avaliacao.AvaliacaoResponseDTO;
import tech.vitalis.caringu.dtos.Avaliacao.FiltroAvaliacaoResponseDTO;
import tech.vitalis.caringu.entity.Aluno;
import tech.vitalis.caringu.entity.Avaliacao;
import tech.vitalis.caringu.entity.PersonalTrainer;
import tech.vitalis.caringu.exception.Aluno.AlunoNaoEncontradoException;
import tech.vitalis.caringu.exception.PersonalTrainer.PersonalNaoEncontradoException;
import tech.vitalis.caringu.mapper.AvaliacaoMapper;
import tech.vitalis.caringu.repository.AlunoRepository;
import tech.vitalis.caringu.repository.AvaliacaoRepository;
import tech.vitalis.caringu.repository.PersonalTrainerRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AvaliacaoServiceTest {

    @Mock
    private AvaliacaoRepository avaliacaoRepository;

    @Mock
    private PersonalTrainerRepository personalTrainerRepository;

    @Mock
    private AvaliacaoMapper avaliacaoMapper;

    @Mock
    private AlunoRepository alunoRepository;

    @InjectMocks
    private AvaliacaoService avaliacaoService;

    @Test
    @DisplayName("listarAvaliacoesPorPersonal deve retornar 204 quando lista vazia")
    void listarAvaliacoesPorPersonal_SemConteudo() {
        PersonalTrainer p = new PersonalTrainer();
        when(personalTrainerRepository.findById(1)).thenReturn(Optional.of(p));
        when(avaliacaoRepository.findByPersonalTrainer(p)).thenReturn(List.of());

        ResponseEntity<List<AvaliacaoResponseDTO>> resposta =
                avaliacaoService.listarAvaliacoesPorPersonal(1);

        assertEquals(204, resposta.getStatusCodeValue());
        verify(avaliacaoRepository).findByPersonalTrainer(p);
    }

    @Test
    @DisplayName("listarAvaliacoesPorPersonal deve retornar 200 com lista de DTOs")
    void listarAvaliacoesPorPersonal_ComSucesso() {
        PersonalTrainer p = new PersonalTrainer();
        when(personalTrainerRepository.findById(1)).thenReturn(Optional.of(p));

        Avaliacao avaliacao = new Avaliacao();
        when(avaliacaoRepository.findByPersonalTrainer(p))
                .thenReturn(List.of(avaliacao));

        AvaliacaoResponseDTO dto = mock(AvaliacaoResponseDTO.class);
        when(avaliacaoMapper.toDto(List.of(avaliacao)))
                .thenReturn(List.of(dto));

        ResponseEntity<List<AvaliacaoResponseDTO>> resposta =
                avaliacaoService.listarAvaliacoesPorPersonal(1);

        assertEquals(200, resposta.getStatusCodeValue());
        assertNotNull(resposta.getBody());
        assertEquals(1, resposta.getBody().size());
    }

    @Test
    @DisplayName("listarAvaliacoesDoPersonalPorNota deve mapear com URL pré-assinada")
    void listarAvaliacoesDoPersonalPorNota_ComSucesso() {
        PersonalTrainer p = new PersonalTrainer();
        when(personalTrainerRepository.findById(1)).thenReturn(Optional.of(p));

        FiltroAvaliacaoResponseDTO base = mock(FiltroAvaliacaoResponseDTO.class);
        when(avaliacaoRepository.findAvaliacoesByPersonalAndNota(1, 4.5))
                .thenReturn(List.of(base));

        FiltroAvaliacaoResponseDTO comUrl = mock(FiltroAvaliacaoResponseDTO.class);
        when(avaliacaoMapper.toFiltroAvaliacaoResponseDTOComUrlPreAssinada(base))
                .thenReturn(comUrl);

        ResponseEntity<List<FiltroAvaliacaoResponseDTO>> resposta =
                avaliacaoService.listarAvaliacoesDoPersonalPorNota(1, 4.5);

        assertEquals(200, resposta.getStatusCodeValue());
        assertNotNull(resposta.getBody());
        assertEquals(1, resposta.getBody().size());
        verify(avaliacaoMapper).toFiltroAvaliacaoResponseDTOComUrlPreAssinada(base);
    }

    @Test
    @DisplayName("cadastrarAvaliacao deve salvar nova avaliação e retornar 201")
    void cadastrarAvaliacao_ComSucesso() {
        // AvaliacaoRequestDTO(personalId, alunoId, nota, comentario)
        AvaliacaoRequestDTO req = new AvaliacaoRequestDTO(
                2, // personalId
                1, // alunoId
                5.0,
                "Excelente"
        );

        PersonalTrainer personal = new PersonalTrainer();
        when(personalTrainerRepository.findById(2)).thenReturn(Optional.of(personal));

        Aluno aluno = new Aluno();
        when(alunoRepository.findById(1)).thenReturn(Optional.of(aluno));

        Avaliacao entity = new Avaliacao();
        when(avaliacaoMapper.toEntity(req, aluno, personal)).thenReturn(entity);
        when(avaliacaoRepository.save(entity)).thenReturn(entity);

        AvaliacaoResponseDTO dto = mock(AvaliacaoResponseDTO.class);
        when(avaliacaoMapper.toDto(entity)).thenReturn(dto);

        ResponseEntity<AvaliacaoResponseDTO> resposta =
                avaliacaoService.cadastrarAvaliacao(req);

        assertEquals(201, resposta.getStatusCodeValue());
        assertNotNull(resposta.getBody());
    }

    @Test
    @DisplayName("cadastrarAvaliacao deve lançar PersonalNaoEncontradoException quando personal não existir")
    void cadastrarAvaliacao_PersonalNaoEncontrado() {
        AvaliacaoRequestDTO req = new AvaliacaoRequestDTO(2, 1, 5.0, "Ok");

        when(personalTrainerRepository.findById(2)).thenReturn(Optional.empty());

        assertThrows(PersonalNaoEncontradoException.class,
                () -> avaliacaoService.cadastrarAvaliacao(req));
    }

    @Test
    @DisplayName("criarOuAtualizarAvaliacao deve atualizar avaliação existente")
    void criarOuAtualizarAvaliacao_AtualizarExistente() {
        AvaliacaoRequestDTO req = new AvaliacaoRequestDTO(2, 1, 3.0, "Bom");

        PersonalTrainer personal = new PersonalTrainer();
        Aluno aluno = new Aluno();
        when(personalTrainerRepository.findById(2)).thenReturn(Optional.of(personal));
        when(alunoRepository.findById(1)).thenReturn(Optional.of(aluno));

        Avaliacao existente = new Avaliacao();
        existente.setNota(1.0);
        existente.setComentario("Antigo");
        when(avaliacaoRepository.findByAlunoIdAndPersonalTrainerId(1, 2))
                .thenReturn(Optional.of(existente));

        when(avaliacaoRepository.save(existente)).thenReturn(existente);
        AvaliacaoResponseDTO dto = mock(AvaliacaoResponseDTO.class);
        when(avaliacaoMapper.toDto(existente)).thenReturn(dto);

        ResponseEntity<AvaliacaoResponseDTO> resposta =
                avaliacaoService.criarOuAtualizarAvaliacao(req);

        assertEquals(200, resposta.getStatusCodeValue());
        assertEquals(3.0, existente.getNota());
        assertEquals("Bom", existente.getComentario());
    }

    @Test
    @DisplayName("criarOuAtualizarAvaliacao deve criar nova avaliação quando não existir")
    void criarOuAtualizarAvaliacao_CriarNova() {
        AvaliacaoRequestDTO req = new AvaliacaoRequestDTO(2, 1, 4.0, "Legal");

        PersonalTrainer personal = new PersonalTrainer();
        Aluno aluno = new Aluno();
        when(personalTrainerRepository.findById(2)).thenReturn(Optional.of(personal));
        when(alunoRepository.findById(1)).thenReturn(Optional.of(aluno));

        when(avaliacaoRepository.findByAlunoIdAndPersonalTrainerId(1, 2))
                .thenReturn(Optional.empty());

        Avaliacao nova = new Avaliacao();
        when(avaliacaoMapper.toEntity(req, aluno, personal)).thenReturn(nova);
        when(avaliacaoRepository.save(nova)).thenReturn(nova);

        AvaliacaoResponseDTO dto = mock(AvaliacaoResponseDTO.class);
        when(avaliacaoMapper.toDto(nova)).thenReturn(dto);

        ResponseEntity<AvaliacaoResponseDTO> resposta =
                avaliacaoService.criarOuAtualizarAvaliacao(req);

        assertEquals(200, resposta.getStatusCodeValue());
        assertNotNull(resposta.getBody());
    }

    @Test
    @DisplayName("criarOuAtualizarAvaliacao deve lançar AlunoNaoEncontradoException quando aluno não existir")
    void criarOuAtualizarAvaliacao_AlunoNaoEncontrado() {
        AvaliacaoRequestDTO req = new AvaliacaoRequestDTO(2, 1, 3.0, "Bom");

        PersonalTrainer personal = new PersonalTrainer();
        when(personalTrainerRepository.findById(2)).thenReturn(Optional.of(personal));
        when(alunoRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(AlunoNaoEncontradoException.class,
                () -> avaliacaoService.criarOuAtualizarAvaliacao(req));
    }
}
