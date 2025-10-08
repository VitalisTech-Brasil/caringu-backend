package tech.vitalis.caringu.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tech.vitalis.caringu.dtos.Avaliacao.AvaliacaoRequestDTO;
import tech.vitalis.caringu.dtos.Avaliacao.AvaliacaoResponseDTO;
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

@Service
public class AvaliacaoService {
    @Autowired
    private AvaliacaoRepository avaliacaoRepository;
    @Autowired
    private PersonalTrainerRepository personalTrainerRepository;
    @Autowired
    private AvaliacaoMapper avaliacaoMapper;
    @Autowired
    private AlunoRepository alunoRepository;

    public ResponseEntity<List<AvaliacaoResponseDTO>> listarAvaliacoesPorPersonal(Integer idPersonal) {
        PersonalTrainer p = personalTrainerRepository.findById(idPersonal)
                .orElseThrow(()-> new PersonalNaoEncontradoException("Personal Trainer não encontrado com o ID: " + idPersonal));

        List<Avaliacao> avaliacaoList = avaliacaoRepository.findByPersonalTrainer(p);

        if (avaliacaoList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(avaliacaoMapper.toDto(avaliacaoList));
    }

    public ResponseEntity<AvaliacaoResponseDTO> cadastrarAvaliacao(AvaliacaoRequestDTO avaliacaoRequestDTO) {
        PersonalTrainer p = personalTrainerRepository.findById(avaliacaoRequestDTO.personalId())
                .orElseThrow(()-> new PersonalNaoEncontradoException("Personal Trainer não encontrado com o ID: " + avaliacaoRequestDTO.personalId()));

        Aluno a = alunoRepository.findById(avaliacaoRequestDTO.alunoId())
                .orElseThrow(()-> new AlunoNaoEncontradoException("Aluno não encontrado com o ID: " + avaliacaoRequestDTO.alunoId()));

        Avaliacao novaAvaliacao = avaliacaoMapper.toEntity(avaliacaoRequestDTO, a, p);
        Avaliacao avaliacaoSalva = avaliacaoRepository.save(novaAvaliacao);
        AvaliacaoResponseDTO responseDTO = avaliacaoMapper.toDto(avaliacaoSalva);

        return ResponseEntity.status(201).body(responseDTO);
    }
}
