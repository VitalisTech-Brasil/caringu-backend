package tech.vitalis.caringu.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.stereotype.Service;
import tech.vitalis.caringu.dtos.PersonalTrainer.PersonalTrainerResponseGetDTO;
import tech.vitalis.caringu.dtos.Plano.PlanoRequisicaoRecord;
import tech.vitalis.caringu.dtos.Plano.PlanoRespostaRecord;
import tech.vitalis.caringu.dtos.Treino.TreinoRequestUpdateDto;
import tech.vitalis.caringu.dtos.Treino.TreinoResponseGetDTO;
import tech.vitalis.caringu.entity.Anamnese;
import tech.vitalis.caringu.entity.PersonalTrainer;
import tech.vitalis.caringu.entity.Plano;
import tech.vitalis.caringu.entity.Treino;
import tech.vitalis.caringu.enums.PeriodoEnum;
import tech.vitalis.caringu.exception.Anamnese.AnamneseNaoEncontradaException;
import tech.vitalis.caringu.exception.ApiExceptions;
import tech.vitalis.caringu.exception.PersonalTrainer.PersonalNaoEncontradoException;
import tech.vitalis.caringu.mapper.PersonalTrainerMapper;
import tech.vitalis.caringu.mapper.PlanoMapper;
import tech.vitalis.caringu.repository.PersonalTrainerRepository;
import tech.vitalis.caringu.repository.PlanoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PlanoService {
    private final PlanoRepository planoRepository;
    private final PlanoMapper planoMapper;
    private final PersonalTrainerService personalTrainerService;
    private final PersonalTrainerMapper personalTrainerMapper;
    private final PersonalTrainerRepository personalTrainerRepository;

    public PlanoService(PlanoRepository planoRepository, PlanoMapper planoMapper, PersonalTrainerService personalTrainerService, PersonalTrainerMapper personalTrainerMapper, PersonalTrainerRepository personalTrainerRepository) {
        this.planoRepository = planoRepository;
        this.planoMapper = planoMapper;
        this.personalTrainerService = personalTrainerService;
        this.personalTrainerMapper = personalTrainerMapper;
        this.personalTrainerRepository = personalTrainerRepository;
    }

    public List<PlanoRespostaRecord> listarPlanosPorPersonal(Integer personalId) {
        boolean personalTrainer = personalTrainerRepository.existsById(personalId);

        if (!personalTrainer) {
            throw new PersonalNaoEncontradoException("Personal Trainer não encontrado");
        }

        List<Plano> listaDePlanos = planoRepository.findByPersonalTrainerId(personalId);
        return planoMapper.PlanoListToResponseList(listaDePlanos);
    }

    public PlanoRespostaRecord cadastrarPlano(Integer personalId, PlanoRequisicaoRecord planoRequisicaoRecord){
        PersonalTrainerResponseGetDTO responseGetDTO = personalTrainerService.buscarPorId(personalId);
        PersonalTrainer personalTrainer = personalTrainerMapper.responseToEntity(responseGetDTO);
        Plano plano = planoMapper.requestToEntity(planoRequisicaoRecord, personalTrainer);
        planoRepository.save(plano);
        return planoMapper.toResponseRecord(plano);
    }

    public PlanoRespostaRecord atualizarPlano(Integer planoId, PlanoRequisicaoRecord planoDto, Integer personalId){
        Plano plano = planoRepository.findByPersonalTrainerIdAndIdEquals(personalId, planoId)
                .orElseThrow(() -> new ApiExceptions.ResourceNotFoundException("Plano com o ID " + planoId + " e o ID de personal " + personalId + " não encontrado"));

        plano.setNome(planoDto.nome());
        plano.setPeriodo(planoDto.periodo());
        plano.setQuantidadeAulas(planoDto.quantidadeAulas());
        plano.setValorAulas(planoDto.valorAulas());

        Plano planoAtualizado = planoRepository.save(plano);
        return planoMapper.toResponseRecord(planoAtualizado);
    }

    public void deletarPlano(Integer personalId, Integer planoId){
        Plano plano = planoRepository.findByPersonalTrainerIdAndIdEquals(personalId, planoId).orElseThrow(() -> new EntityNotFoundException("Plano com o ID " + planoId + " não encontrado"));
            planoRepository.deleteById(planoId);
    }

}
