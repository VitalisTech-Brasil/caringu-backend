package tech.vitalis.caringu.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import tech.vitalis.caringu.dtos.Anamnese.AnamneseRequestPatchDTO;
import tech.vitalis.caringu.dtos.Anamnese.AnamneseRequestPostDTO;
import tech.vitalis.caringu.dtos.Anamnese.AnamneseResponseGetDTO;
import tech.vitalis.caringu.dtos.Anamnese.AnamneseResponsePatchDTO;
import tech.vitalis.caringu.dtos.PerfilAluno.AnamneseGetPerfilDetalhesDTO;
import tech.vitalis.caringu.entity.Aluno;
import tech.vitalis.caringu.entity.Anamnese;
import tech.vitalis.caringu.enums.Anamnese.FrequenciaTreinoEnum;
import tech.vitalis.caringu.exception.Aluno.AlunoNaoEncontradoException;
import tech.vitalis.caringu.exception.Anamnese.AnamneseJaCadastradaException;
import tech.vitalis.caringu.exception.Anamnese.AnamneseNaoEncontradaException;
import tech.vitalis.caringu.exception.ApiExceptions;
import tech.vitalis.caringu.mapper.AnamneseMapper;
import tech.vitalis.caringu.repository.AlunoRepository;
import tech.vitalis.caringu.repository.AnamneseRepository;

import java.util.Optional;

@Service
public class AnamneseService {
    private final AlunoRepository alunoRepository;
    private final AnamneseRepository anamneseRepository;
    private final AnamneseMapper anamneseMapper;
    private final AlunoService alunoService;

    public AnamneseService(AlunoRepository alunoRepository, AnamneseRepository anamneseRepository,
                           AnamneseMapper anamneseMapper,
                           AlunoService alunoService) {
        this.alunoRepository = alunoRepository;
        this.anamneseRepository = anamneseRepository;
        this.anamneseMapper = anamneseMapper;
        this.alunoService = alunoService;
    }

    public AnamneseGetPerfilDetalhesDTO obterDetalhes(Integer alunoId) {

        Optional<Anamnese> optAnamnese = anamneseRepository.findByAlunoId(alunoId);

        if (optAnamnese.isPresent()) {
            return anamneseMapper.toResponsePerfilDetalhesDTO(optAnamnese.get());
        }

        Aluno aluno = alunoRepository.findById(alunoId)
                .orElseThrow(() -> new AlunoNaoEncontradoException("Aluno não encontrado"));

        return anamneseMapper.toResponsePerfilDetalhesDTOSemAnamnese(aluno);
    }

    public Integer contarAnamnesesPendentes(Integer personalId) {
        return anamneseRepository.countAnamnesesPendentesByPersonalId(personalId);
    }

    public AnamneseResponseGetDTO cadastrar(AnamneseRequestPostDTO requestDTO) {
        Aluno aluno = alunoService.buscarAlunoOuLancarExcecao(requestDTO.alunoId());

        if (anamneseRepository.existsByAlunoId(aluno.getId())) {
            throw new AnamneseJaCadastradaException("Este aluno já possui uma anamnese cadastrada.");
        }

        try {
            FrequenciaTreinoEnum.fromValor(requestDTO.frequenciaTreino());
        } catch (IllegalArgumentException e) {
            throw new ApiExceptions.BadRequestException("Valor inválido para 'FrequenciaTreino'. Valores válidos: '1', '2', '3', '4', '5', '6', '7'");
        }

        Anamnese anamnese = anamneseMapper.toEntity(requestDTO, aluno);
        Anamnese salvo = anamneseRepository.save(anamnese);
        return anamneseMapper.toResponseDTO(salvo);
    }

    @Transactional
    public AnamneseResponsePatchDTO atualizarParcialmente(Integer anamneseId, AnamneseRequestPatchDTO dto) {
        Anamnese anamnese = anamneseRepository.findById(anamneseId)
                .orElseThrow(() -> new AnamneseNaoEncontradaException("Anamnese com ID " + anamneseId + " não encontrada."));

        anamneseMapper.updateAnamneseFromDto(dto, anamnese);

        Anamnese atualizado = anamneseRepository.save(anamnese);
        return anamneseMapper.toDto(atualizado);
    }

}
