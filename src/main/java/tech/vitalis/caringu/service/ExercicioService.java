package tech.vitalis.caringu.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tech.vitalis.caringu.dtos.Exercicio.ExercicioRequestPostDTO;
import tech.vitalis.caringu.dtos.Exercicio.ExercicioRequestPostFuncionalDTO;
import tech.vitalis.caringu.dtos.Exercicio.ExercicioResponseGetDTO;
import tech.vitalis.caringu.dtos.Exercicio.ExercicioResponseTotalExercicioOrigemDTO;
import tech.vitalis.caringu.dtos.TreinoExercicio.TreinoExercicioResumoDTO;
import tech.vitalis.caringu.dtos.TreinoExercicio.TreinoExercicioResumoModeloCruQuerySqlDTO;
import tech.vitalis.caringu.entity.AlunoTreinoExercicio;
import tech.vitalis.caringu.entity.Exercicio;
import tech.vitalis.caringu.entity.PersonalTrainer;
import tech.vitalis.caringu.enums.Exercicio.OrigemEnum;
import tech.vitalis.caringu.exception.ApiExceptions;
import tech.vitalis.caringu.mapper.ExercicioMapper;
import tech.vitalis.caringu.repository.AlunoTreinoExercicioRepository;
import tech.vitalis.caringu.repository.ExercicioRepository;
import tech.vitalis.caringu.repository.PersonalTrainerRepository;
import tech.vitalis.caringu.strategy.Exercicio.GrupoMuscularEnumValidator;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static tech.vitalis.caringu.strategy.EnumValidador.validarEnums;

@Service
public class ExercicioService {

    private final AlunoTreinoExercicioRepository alunoTreinoExercicioRepository;
    private final PersonalTrainerRepository personalTrainerRepository;
    private final ExercicioRepository exercicioRepository;
    private final ExercicioMapper exercicioMapper;

    @Autowired
    public ExercicioService(
            AlunoTreinoExercicioRepository alunoTreinoExercicioRepository, PersonalTrainerRepository personalTrainerRepository,
            ExercicioRepository exercicioRepository, ExercicioMapper exercicioMapper
    ) {
        this.alunoTreinoExercicioRepository = alunoTreinoExercicioRepository;
        this.personalTrainerRepository = personalTrainerRepository;
        this.exercicioRepository = exercicioRepository;
        this.exercicioMapper = exercicioMapper;
    }

    public List<ExercicioResponseGetDTO> listarExerciciosPorIdPersonal() {
        return exercicioRepository.findAll()
                .stream()
                .map(exercicioMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public ExercicioResponseGetDTO buscarPorId(Integer id) {
        Exercicio exercicio = exercicioRepository.findById(id)
                .orElseThrow(() -> new ApiExceptions.ResourceNotFoundException("Exercício com ID " + id + " não encontrado"));
        return exercicioMapper.toResponseDTO(exercicio);
    }

    public List<ExercicioResponseGetDTO> listarExerciciosPorIdPersonal(Integer idPersonal) {
        return exercicioRepository.findAllByPersonal_IdOrPersonalIsNull(idPersonal)
                .stream()
                .map(exercicioMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public Page<ExercicioResponseGetDTO> paginarExerciciosPorIdPersonal(Integer idPersonal, Pageable pageable) {
        return exercicioRepository.findAllByPersonal_IdOrPersonalIsNull(idPersonal, pageable)
                .map(exercicioMapper::toResponseDTO);
    }

    public List<ExercicioResponseTotalExercicioOrigemDTO> buscarTotalExercicioOrigem(Integer idPersonal) {
        return exercicioRepository.buscarTotalExercicioOrigem(idPersonal);
    }

    public ExercicioResponseGetDTO cadastrar(ExercicioRequestPostFuncionalDTO exercicioDto) {
        validarEnums(Map.of(
                new GrupoMuscularEnumValidator(), exercicioDto.grupoMuscular()
        ));

        Exercicio novoExercicio = exercicioMapper.toEntity(exercicioDto);

        Optional<PersonalTrainer> personalExistente = personalTrainerRepository.findById(exercicioDto.idPersonal());
        personalExistente.ifPresent(novoExercicio::setPersonal);
        novoExercicio.setOrigem(OrigemEnum.PERSONAL);

        Exercicio exercicioSalvo = exercicioRepository.save(novoExercicio);

        return exercicioMapper.toResponseDTO(exercicioSalvo);
    }

    public ExercicioResponseGetDTO atualizar(Integer idExercicio, ExercicioRequestPostFuncionalDTO exercicioDto) {
        Exercicio exercicioExistente = exercicioRepository.findById(idExercicio)
                .orElseThrow(() -> new ApiExceptions.ResourceNotFoundException("Exercício com ID " + idExercicio + " não encontrado"));


        exercicioExistente.setNome(exercicioDto.nome());
        exercicioExistente.setGrupoMuscular(exercicioDto.grupoMuscular());
        exercicioExistente.setUrlVideo(exercicioDto.urlVideo());
        exercicioExistente.setObservacoes(exercicioDto.observacoes());

        Exercicio exercicioAtualizado = exercicioRepository.save(exercicioExistente);
        return exercicioMapper.toResponseDTO(exercicioAtualizado);
    }

    public ExercicioResponseGetDTO editarInfoExercicio(Integer id, ExercicioRequestPostDTO exercicioDto) {
        Exercicio exercicioExistente = exercicioRepository.findById(id)
                .orElseThrow(() -> new ApiExceptions.ResourceNotFoundException("Exercício com ID " + id + " não encontrado"));

        boolean atualizado = false;

        if (exercicioDto.nome() != null && !exercicioDto.nome().trim().isEmpty()) {
            exercicioExistente.setNome(exercicioDto.nome());
            atualizado = true;
        }
        if (exercicioDto.grupoMuscular() != null) {
            exercicioExistente.setGrupoMuscular(exercicioDto.grupoMuscular());
            atualizado = true;
        }

        if (!atualizado) {
            throw new ApiExceptions.BadRequestException("Nenhuma informação válida foi fornecida para atualização.");
        }

        Exercicio exercicioAtualizado = exercicioRepository.save(exercicioExistente);
        return exercicioMapper.toResponseDTO(exercicioAtualizado);
    }

    @Transactional
    public void atualizarFavorito(Integer id, boolean favorito) {
        Exercicio exercicioFavorito = exercicioRepository.findById(id)
                .orElseThrow(() -> new ApiExceptions.ResourceNotFoundException("Exercicio não encontrado"));

        exercicioFavorito.setFavorito(favorito);
    }

    @Transactional
    public void remover(Integer id) {

        if (!exercicioRepository.existsById(id)) {
            throw new ApiExceptions.ResourceNotFoundException("Exercício com ID " + id + " não encontrado");
        }

        List<AlunoTreinoExercicio> alunoTreinoExercicios = alunoTreinoExercicioRepository.findAllByExercicioId(id);

        for (AlunoTreinoExercicio alunoTreinoExercicio : alunoTreinoExercicios) {
            alunoTreinoExercicio.setExercicio(null);
        }

        exercicioRepository.deleteById(id);
    }
}
