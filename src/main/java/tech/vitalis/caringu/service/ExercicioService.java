package tech.vitalis.caringu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.vitalis.caringu.dtos.Exercicio.ExercicioRequestPostDTO;
import tech.vitalis.caringu.dtos.Exercicio.ExercicioResponseGetDTO;
import tech.vitalis.caringu.enums.Exercicio.GrupoMuscularValidatorEnum;
import tech.vitalis.caringu.enums.Exercicio.OrigemValidatorEnum;
import tech.vitalis.caringu.exception.ApiExceptions;
import tech.vitalis.caringu.mapper.ExercicioMapper;
import tech.vitalis.caringu.entity.Exercicio;
import tech.vitalis.caringu.repository.ExercicioRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExercicioService {

    private final ExercicioRepository exercicioRepository;
    private final ExercicioMapper exercicioMapper;
    private final GrupoMuscularValidatorEnum grupoMuscularValidatorEnum;
    private final OrigemValidatorEnum origemValidatorEnum;

    @Autowired
    public ExercicioService(ExercicioRepository exercicioRepository, ExercicioMapper exercicioMapper, GrupoMuscularValidatorEnum grupoMuscularValidatorEnum, OrigemValidatorEnum origemValidatorEnum) {
        this.exercicioRepository = exercicioRepository;
        this.exercicioMapper = exercicioMapper;
        this.grupoMuscularValidatorEnum = grupoMuscularValidatorEnum;
        this.origemValidatorEnum = origemValidatorEnum;
    }

    public ExercicioResponseGetDTO cadastrar(ExercicioRequestPostDTO exercicioDto) {
        grupoMuscularValidatorEnum.validar(exercicioDto.grupoMuscular());
        origemValidatorEnum.validar(exercicioDto.origem());
        Exercicio novoExercicio = exercicioMapper.toEntity(exercicioDto);

        Exercicio exercicioSalvo = exercicioRepository.save(novoExercicio);

        return exercicioMapper.toResponseDTO(exercicioSalvo);
    }

    public ExercicioResponseGetDTO buscarPorId(Integer id) {
        Exercicio exercicio = exercicioRepository.findById(id)
                .orElseThrow(() -> new ApiExceptions.ResourceNotFoundException("Exercício com ID " + id + " não encontrado"));
        return exercicioMapper.toResponseDTO(exercicio);
    }

    public List<ExercicioResponseGetDTO> listarTodos() {
        return exercicioRepository.findAll()
                .stream()
                .map(exercicioMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public ExercicioResponseGetDTO atualizar(Integer id, ExercicioRequestPostDTO exercicioDto) {
        Exercicio exercicioExistente = exercicioRepository.findById(id)
                .orElseThrow(() -> new ApiExceptions.ResourceNotFoundException("Exercício com ID " + id + " não encontrado"));


        exercicioExistente.setNome(exercicioDto.nome());
        exercicioExistente.setGrupoMuscular(exercicioDto.grupoMuscular());
        exercicioExistente.setUrlVideo(exercicioDto.urlVideo());
        exercicioExistente.setObservacoes(exercicioDto.observacoes());
        exercicioExistente.setFavorito(exercicioDto.favorito());
        exercicioExistente.setOrigem(exercicioDto.origem());

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
        if (exercicioDto.grupoMuscular() != null && !exercicioDto.grupoMuscular().trim().isEmpty()) {
            exercicioExistente.setGrupoMuscular(exercicioDto.grupoMuscular());
            atualizado = true;
        }

        if (!atualizado) {
            throw new ApiExceptions.BadRequestException("Nenhuma informação válida foi fornecida para atualização.");
        }

        Exercicio exercicioAtualizado = exercicioRepository.save(exercicioExistente);
        return exercicioMapper.toResponseDTO(exercicioAtualizado);
    }

    public void remover(Integer id) {
        if (!exercicioRepository.existsById(id)) {
            throw new ApiExceptions.ResourceNotFoundException("Exercício com ID " + id + " não encontrado");
        }
        exercicioRepository.deleteById(id);
    }
}
