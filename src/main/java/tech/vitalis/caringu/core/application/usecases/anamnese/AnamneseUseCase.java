package tech.vitalis.caringu.core.application.usecases.anamnese;

import tech.vitalis.caringu.core.application.gateways.anamnese.AnamneseGateway;
import tech.vitalis.caringu.core.domain.entity.Anamnese;
import tech.vitalis.caringu.core.domain.valueObject.FrequenciaTreinoEnum;
import tech.vitalis.caringu.core.exceptions.ApiExceptions;
import tech.vitalis.caringu.core.exceptions.aluno.AlunoNaoEncontradoException;
import tech.vitalis.caringu.core.exceptions.anamnese.AnamneseJaCadastradaException;
import tech.vitalis.caringu.core.exceptions.anamnese.AnamneseNaoEncontradaException;
import tech.vitalis.caringu.entity.Aluno;
import tech.vitalis.caringu.entity.PersonalTrainer;
import tech.vitalis.caringu.exception.PersonalTrainer.PersonalNaoEncontradoException;
import tech.vitalis.caringu.infrastructure.gateways.anamnese.AnamneseEntityMapper;
import tech.vitalis.caringu.repository.AlunoRepository;
import tech.vitalis.caringu.repository.PersonalTrainerRepository;

public class AnamneseUseCase {
    private final AnamneseGateway anamneseGateway;
    private final AlunoRepository alunoRepository;
    private final AnamneseEntityMapper anamneseMapper;
    private final PersonalTrainerRepository personalTrainerRepository;

    public AnamneseUseCase(AnamneseGateway anamneseGateway, AlunoRepository alunoRepository, AnamneseEntityMapper anamneseMapper, PersonalTrainerRepository personalTrainerRepository) {
        this.anamneseGateway = anamneseGateway;
        this.alunoRepository = alunoRepository;
        this.anamneseMapper = anamneseMapper;
        this.personalTrainerRepository = personalTrainerRepository;
    }

    public Anamnese createAnamnese(Anamnese anamnese){
        Aluno aluno = alunoRepository.findById(anamnese.aluno().getId())
                .orElseThrow(() -> new AlunoNaoEncontradoException("Aluno não encontrado com ID: " + anamnese.aluno()));

        anamneseGateway.getAnamneseByAlunoId(aluno.getId())
                .ifPresent(anamneseExistente -> {
                    throw new AnamneseJaCadastradaException("Este aluno já possui uma anamnese cadastrada.");
                });

        try {
            FrequenciaTreinoEnum.fromValor(String.valueOf(anamnese.frequenciaTreino()));
        }catch (Exception e){
            throw new ApiExceptions.BadRequestException("Valor inválido para 'FrequenciaTreino'. Valores válidos: '1', '2', '3', '4', '5', '6', '7'");
        }

        return anamneseGateway.createAnamnese(anamnese);
    }

    public Anamnese getAnamnese(Integer alunoId){
        Aluno aluno = alunoRepository.findById(alunoId)
                .orElseThrow(() -> new AlunoNaoEncontradoException("Aluno não encontrado com ID: " + alunoId));

        return anamneseGateway.getAnamneseByAlunoId(alunoId)
                .orElseGet(() -> anamneseMapper.toResponsePerfilDetalhesDTOSemAnamnese(aluno));
    }

    public Integer countAnamnesesPendentesByPersonal(Integer personalId){
        PersonalTrainer personalTrainer = personalTrainerRepository.findById(personalId)
                .orElseThrow(() -> new PersonalNaoEncontradoException("Personal Trainer não encontrado com ID: " + personalId));

        return anamneseGateway.countAnamnesesPendentes(personalId);
    }

    public Anamnese patchAnamnese(Integer anamneseId, Anamnese anamnese){
        Anamnese anamneseExistente = anamneseGateway.getAnamneseById(anamneseId)
                .orElseThrow(() -> new AnamneseNaoEncontradaException("Anamnese com ID " + anamneseId + " não encontrada."));

        return anamneseGateway.patchAnamnese(anamneseId, anamnese);
    }
}
