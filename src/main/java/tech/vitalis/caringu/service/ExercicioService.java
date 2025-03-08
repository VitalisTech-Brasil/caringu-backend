package tech.vitalis.caringu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.vitalis.caringu.dtos.Exercicio.CriacaoExercicioDTO;
import tech.vitalis.caringu.dtos.Exercicio.RespostaExercicioDTO;
import tech.vitalis.caringu.exception.ApiExceptions;
import tech.vitalis.caringu.mapper.ExercicioMapper;
import tech.vitalis.caringu.model.Exercicio;
import tech.vitalis.caringu.repository.ExercicioRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExercicioService {

    private final ExercicioRepository exercicioRepository;
    private final ExercicioMapper exercicioMapper;

    @Autowired
    public ExercicioService(ExercicioRepository exercicioRepository, ExercicioMapper exercicioMapper) {
        this.exercicioRepository = exercicioRepository;
        this.exercicioMapper = exercicioMapper;
    }

    public RespostaExercicioDTO cadastrar(CriacaoExercicioDTO exercicioDto) {
        if (exercicioDto.getNome() == null || exercicioDto.getNome().trim().isEmpty()) {
            throw new ApiExceptions.BadRequestException("O nome do exercício não pode estar vazio.");
        }

        Exercicio novoExercicio = exercicioMapper.toEntity(exercicioDto);
        Exercicio exercicioSalvo = exercicioRepository.save(novoExercicio);
        return exercicioMapper.toDTO(exercicioSalvo);
    }

    public RespostaExercicioDTO buscarPorId(Long id) {
        Exercicio exercicio = exercicioRepository.findById(id)
                .orElseThrow(() -> new ApiExceptions.ResourceNotFoundException("Exercício com ID " + id + " não encontrado"));
        return exercicioMapper.toDTO(exercicio);
    }

    public List<RespostaExercicioDTO> listarTodos() {
        return exercicioRepository.findAll()
                .stream()
                .map(exercicioMapper::toDTO)
                .collect(Collectors.toList());
    }

    public RespostaExercicioDTO atualizar(Long id, CriacaoExercicioDTO exercicioDto) {
        Exercicio exercicioExistente = exercicioRepository.findById(id)
                .orElseThrow(() -> new ApiExceptions.ResourceNotFoundException("Exercício com ID " + id + " não encontrado"));

        if (exercicioDto.getNome() == null || exercicioDto.getNome().trim().isEmpty()) {
            throw new ApiExceptions.BadRequestException("O nome do exercício não pode estar vazio.");
        }

        exercicioExistente.setNome(exercicioDto.getNome());
        exercicioExistente.setGrupoMuscular(exercicioDto.getGrupoMuscular());

        Exercicio exercicioAtualizado = exercicioRepository.save(exercicioExistente);
        return exercicioMapper.toDTO(exercicioAtualizado);
    }

    public RespostaExercicioDTO editarInfoExercicio(Long id, CriacaoExercicioDTO exercicioDto) {
        Exercicio exercicioExistente = exercicioRepository.findById(id)
                .orElseThrow(() -> new ApiExceptions.ResourceNotFoundException("Exercício com ID " + id + " não encontrado"));

        boolean atualizado = false;

        if (exercicioDto.getNome() != null && !exercicioDto.getNome().trim().isEmpty()) {
            exercicioExistente.setNome(exercicioDto.getNome());
            atualizado = true;
        }
        if (exercicioDto.getGrupoMuscular() != null && !exercicioDto.getGrupoMuscular().trim().isEmpty()) {
            exercicioExistente.setGrupoMuscular(exercicioDto.getGrupoMuscular());
            atualizado = true;
        }

        if (!atualizado) {
            throw new ApiExceptions.BadRequestException("Nenhuma informação válida foi fornecida para atualização.");
        }

        Exercicio exercicioAtualizado = exercicioRepository.save(exercicioExistente);
        return exercicioMapper.toDTO(exercicioAtualizado);
    }

    public void remover(Long id) {
        if (!exercicioRepository.existsById(id)) {
            throw new ApiExceptions.ResourceNotFoundException("Exercício com ID " + id + " não encontrado");
        }
        exercicioRepository.deleteById(id);
    }
}
