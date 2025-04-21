package tech.vitalis.caringu.service;

import org.springframework.stereotype.Service;
import tech.vitalis.caringu.dtos.TreinoExericio.TreinoExercicioRequestPostDto;
import tech.vitalis.caringu.dtos.TreinoExericio.TreinoExercicioRequestUpdateDto;
import tech.vitalis.caringu.dtos.TreinoExericio.TreinoExercicioResponseGetDto;
import tech.vitalis.caringu.entity.Exercicio;
import tech.vitalis.caringu.entity.Treino;
import tech.vitalis.caringu.entity.TreinoExercicio;
import tech.vitalis.caringu.enums.TreinoExercicio.GrauDificuldadeValidatorEnum;
import tech.vitalis.caringu.exception.ApiExceptions;
import tech.vitalis.caringu.mapper.TreinoExercicioMapper;
import tech.vitalis.caringu.repository.ExercicioRepository;
import tech.vitalis.caringu.repository.TreinoExercicioRepository;
import tech.vitalis.caringu.repository.TreinoRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TreinoExercicioService {

    private final TreinoExercicioRepository treinoExercicioRepository;
    private final TreinoExercicioMapper treinoExercicioMapper;
    private final TreinoRepository treinoRepository;
    private final ExercicioRepository exercicioRepository;
    private final GrauDificuldadeValidatorEnum grauDificuldadeValidatorEnum;

    public TreinoExercicioService(TreinoExercicioRepository treinoExercicioRepository, TreinoExercicioMapper treinoExercicioMapper, TreinoRepository treinoRepository, ExercicioRepository exercicioRepository, GrauDificuldadeValidatorEnum grauDificuldadeValidatorEnum) {
        this.treinoExercicioRepository = treinoExercicioRepository;
        this.treinoExercicioMapper = treinoExercicioMapper;
        this.treinoRepository = treinoRepository;
        this.exercicioRepository = exercicioRepository;
        this.grauDificuldadeValidatorEnum = grauDificuldadeValidatorEnum;
    }

    public TreinoExercicioResponseGetDto cadastrar(TreinoExercicioRequestPostDto treinoDTO){
        Treino treino = treinoRepository.findById(treinoDTO.treinosId()).
                orElseThrow(() -> new ApiExceptions.BadRequestException("Treino com o ID " + treinoDTO.treinosId() + " não encontrado."));

        Exercicio exercicio = exercicioRepository.findById(treinoDTO.exercicioId()).
                orElseThrow(() -> new ApiExceptions.BadRequestException("Exercício com o ID " + treinoDTO.exercicioId() + " não encontrado."));

        grauDificuldadeValidatorEnum.validar(treinoDTO.grauDificuldade());
        TreinoExercicio novoTreino = treinoExercicioMapper.toEntity(treinoDTO);
        novoTreino.setTreinos(treino);
        novoTreino.setExercicio(exercicio);

        TreinoExercicio treinoSalvo = treinoExercicioRepository.save(novoTreino);

        return treinoExercicioMapper.toResponseDTO(treinoSalvo);
    }

    public TreinoExercicioResponseGetDto buscarPorId(Integer id){
        TreinoExercicio treinoExercicio = treinoExercicioRepository.findById(id)
                .orElseThrow(() -> new ApiExceptions.ResourceNotFoundException("Treino com ID " + id + " não encontrado"));

        return treinoExercicioMapper.toResponseDTO(treinoExercicio);
    }

    public List<TreinoExercicioResponseGetDto> listarTodos(){
        return treinoExercicioRepository.findAll()
                .stream()
                .map(treinoExercicioMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public TreinoExercicioResponseGetDto atualizar(Integer id, TreinoExercicioRequestUpdateDto treinoDTO, Integer exercicioId, Integer treinoId){
        Treino treinoExistente = treinoRepository.findById(treinoId).
                orElseThrow(() -> new ApiExceptions.BadRequestException("Treino com o ID " + treinoId + " não encontrado."));

        Exercicio exercicioExistente = exercicioRepository.findById(exercicioId).
                orElseThrow(() -> new ApiExceptions.BadRequestException("Exercício com o ID " + exercicioId + " não encontrado."));

        TreinoExercicio treinoExercicioExistente = treinoExercicioRepository.findById(id).
                orElseThrow(() -> new ApiExceptions.BadRequestException("Exercício com o ID " + id + " não encontrado."));

        grauDificuldadeValidatorEnum.validar(treinoDTO.grauDificuldade());

        treinoExercicioExistente.setExercicio(exercicioExistente);
        treinoExercicioExistente.setTreinos(treinoExistente);
        treinoExercicioExistente.setCarga(treinoDTO.carga());
        treinoExercicioExistente.setRepeticoes(treinoDTO.repeticoes());
        treinoExercicioExistente.setSeries(treinoDTO.series());
        treinoExercicioExistente.setDescanso(treinoDTO.descanso());
        treinoExercicioExistente.setDataHoraCriacao(treinoDTO.dataHoraCriacao());
        treinoExercicioExistente.setDataHoraModificacao(treinoDTO.dataHoraModificacao());
        treinoExercicioExistente.setFavorito(treinoDTO.favorito());
        treinoExercicioExistente.setGrauDificuldade(treinoDTO.grauDificuldade());

        TreinoExercicio treinoExercicioAtualizado = treinoExercicioRepository.save(treinoExercicioExistente);
        return treinoExercicioMapper.toResponseDTO(treinoExercicioAtualizado);
    }

    public void remover(Integer id){
        TreinoExercicio treinoExercicioExistente = treinoExercicioRepository.findById(id).
                orElseThrow(() -> new ApiExceptions.BadRequestException("Exercício com o ID " + id + " não encontrado."));

        treinoExercicioExistente.setTreinos(null);
        treinoExercicioExistente.setExercicio(null);
        treinoExercicioRepository.deleteById(id);
    }

    public void removerAssociacaoComTreino(Integer id){
        TreinoExercicio treinoExistente = treinoExercicioRepository.findById(id)
                .orElseThrow(() -> new ApiExceptions.ResourceNotFoundException("Treino com ID " + id + " não encontrado"));

        treinoExistente.setTreinos(null);
        treinoExercicioRepository.save(treinoExistente);
    }

    public void removerAssociacaoComExercicio(Integer id){
        TreinoExercicio treinoExistente = treinoExercicioRepository.findById(id)
                .orElseThrow(() -> new ApiExceptions.ResourceNotFoundException("Treino com ID " + id + " não encontrado"));

        treinoExistente.setExercicio(null);
        treinoExercicioRepository.save(treinoExistente);
    }

    public void removerComDesassociacao(Integer id) {
        TreinoExercicio treinoExistente = treinoExercicioRepository.findById(id)
                .orElseThrow(() -> new ApiExceptions.ResourceNotFoundException("Treino com ID " + id + " não encontrado"));

        treinoExistente.setTreinos(null);
        treinoExistente.setExercicio(null);
        treinoExercicioRepository.save(treinoExistente); // desassocia
        treinoExercicioRepository.deleteById(id); // deleta
    }
}
