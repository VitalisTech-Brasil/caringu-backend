package tech.vitalis.caringu.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.stereotype.Service;
import tech.vitalis.caringu.dtos.Aluno.AlunoResponseGetDTO;
import tech.vitalis.caringu.dtos.PersonalTrainer.PersonalTrainerResponseGetDTO;
import tech.vitalis.caringu.dtos.Plano.PlanoRequisicaoRecord;
import tech.vitalis.caringu.dtos.Plano.PlanoRespostaRecord;
import tech.vitalis.caringu.dtos.PlanoContratado.PlanoContratadoRespostaRecord;
import tech.vitalis.caringu.dtos.Treino.TreinoRequestUpdateDto;
import tech.vitalis.caringu.dtos.Treino.TreinoResponseGetDTO;

import tech.vitalis.caringu.entity.*;
import tech.vitalis.caringu.enums.PeriodoEnum;
import tech.vitalis.caringu.enums.StatusEnum;
import tech.vitalis.caringu.exception.ApiExceptions;
import tech.vitalis.caringu.mapper.AlunoMapper;

import tech.vitalis.caringu.mapper.PersonalTrainerMapper;
import tech.vitalis.caringu.mapper.PlanoContratadoMapper;
import tech.vitalis.caringu.mapper.PlanoMapper;

import tech.vitalis.caringu.repository.AlunoRepository;
import tech.vitalis.caringu.repository.PlanoContratadoRepository;

import tech.vitalis.caringu.repository.PlanoRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PlanoService {
    private final PlanoRepository planoRepository;
    private final PlanoMapper planoMapper;

    private final PlanoContratadoRepository planoContratadoRepository;
    private final PlanoContratadoMapper planoContratadoMapper;

    private final PersonalTrainerService personalTrainerService;
    private final PersonalTrainerMapper personalTrainerMapper;
    private final PersonalTrainerRepository personalTrainerRepository;

    private final AlunoService alunoService;
    private final AlunoMapper alunoMapper;
    private final AlunoRepository alunoRepository;

    public PlanoService(PlanoRepository planoRepository, PlanoMapper planoMapper, PlanoContratadoRepository planoContratadoRepository, PlanoContratadoMapper planoContratadoMapper, PersonalTrainerService personalTrainerService, PersonalTrainerMapper personalTrainerMapper, AlunoService alunoService, AlunoMapper alunoMapper, AlunoRepository alunoRepository) {
        this.planoRepository = planoRepository;
        this.planoMapper = planoMapper;
        this.planoContratadoRepository = planoContratadoRepository;
        this.planoContratadoMapper = planoContratadoMapper;
        this.personalTrainerService = personalTrainerService;
        this.personalTrainerMapper = personalTrainerMapper;
        this.alunoService = alunoService;
        this.alunoMapper = alunoMapper;
        this.alunoRepository = alunoRepository;
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

    @Transactional
    public PlanoContratadoRespostaRecord contratarPlano(Integer alunoId, Integer planoId){
        Aluno aluno = alunoRepository.findById(alunoId).get();

        Plano plano = planoRepository.findById(planoId).get();

        LocalDate dataFim = null;

        PlanoContratado planoContratado = new PlanoContratado();
        planoContratado.setAluno(aluno);
        planoContratado.setPlano(plano);
        planoContratado.setStatus(StatusEnum.PENDENTE);
        planoContratado.setDataContratacao(LocalDate.now());

        if (plano.getPeriodo().equals(PeriodoEnum.MENSAL)){
            dataFim = LocalDate.now().plusMonths(1);
        } else if (plano.getPeriodo().equals(PeriodoEnum.SEMESTRAL)){
            dataFim = LocalDate.now().plusMonths(6);
        } else if (plano.getPeriodo().equals(PeriodoEnum.AVULSO)){
            if (planoContratado.getDataFim().isAfter(planoContratado.getDataContratacao())){
                dataFim = LocalDate.now();
                planoContratado.setStatus(StatusEnum.INATIVO);
            }
        }

        planoContratado.setDataFim(dataFim);

        planoContratadoRepository.save(planoContratado);

        return planoContratadoMapper.toResponseRecord(planoContratado);
    }
}
