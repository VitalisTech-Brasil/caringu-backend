package tech.vitalis.caringu.service;

import org.springframework.stereotype.Service;
import tech.vitalis.caringu.dtos.TreinoExercicio.*;
import tech.vitalis.caringu.entity.Exercicio;
import tech.vitalis.caringu.entity.Treino;
import tech.vitalis.caringu.entity.TreinoExercicio;
import tech.vitalis.caringu.exception.ApiExceptions;
import tech.vitalis.caringu.mapper.TreinoExercicioMapper;
import tech.vitalis.caringu.repository.ExercicioRepository;
import tech.vitalis.caringu.repository.TreinoExercicioRepository;
import tech.vitalis.caringu.repository.TreinoRepository;
import tech.vitalis.caringu.strategy.TreinoExercio.GrauDificuldadeEnumValidator;
import tech.vitalis.caringu.strategy.TreinoExercio.OrigemTreinoExercicioEnumValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static tech.vitalis.caringu.strategy.EnumValidador.validarEnums;

@Service
public class TreinoExercicioService {

    private final TreinoExercicioRepository treinoExercicioRepository;
    private final TreinoExercicioMapper treinoExercicioMapper;
    private final TreinoRepository treinoRepository;
    private final ExercicioRepository exercicioRepository;

    public TreinoExercicioService(TreinoExercicioRepository treinoExercicioRepository, TreinoExercicioMapper treinoExercicioMapper, TreinoRepository treinoRepository, ExercicioRepository exercicioRepository) {
        this.treinoExercicioRepository = treinoExercicioRepository;
        this.treinoExercicioMapper = treinoExercicioMapper;
        this.treinoRepository = treinoRepository;
        this.exercicioRepository = exercicioRepository;
    }

    /*
    public TreinoExercicioResponseGetDto cadastrar(TreinoExercicioRequestPostDto treinoDTO){
        validarEnums(Map.of(
                new OrigemTreinoExercicioEnumValidator(), treinoDTO.origemTreinoExercicio(),
                new GrauDificuldadeEnumValidator(), treinoDTO.grauDificuldade()
        ));

        Treino treino = treinoRepository.findById(treinoDTO.treinosId()).
                orElseThrow(() -> new ApiExceptions.BadRequestException("Treino com o ID " + treinoDTO.treinosId() + " não encontrado."));

        Exercicio exercicio = exercicioRepository.findById(treinoDTO.exercicioId()).
                orElseThrow(() -> new ApiExceptions.BadRequestException("Exercício com o ID " + treinoDTO.exercicioId() + " não encontrado."));

        TreinoExercicio novoTreino = treinoExercicioMapper.toEntity(treinoDTO);
        novoTreino.setTreinos(treino);
        novoTreino.setExercicio(exercicio);

        TreinoExercicio treinoSalvo = treinoExercicioRepository.save(novoTreino);

        return treinoExercicioMapper.toResponseDTO(treinoSalvo);
    }

     */

    public TreinoExercicioResponseGetDto buscarPorId(Integer id){
        TreinoExercicio treinoExercicio = treinoExercicioRepository.findById(id)
                .orElseThrow(() -> new ApiExceptions.ResourceNotFoundException("Treino com ID " + id + " não encontrado"));

        return treinoExercicioMapper.toResponseDTO(treinoExercicio);
    }

    public List<TreinoExercicioResumoDTO> listarPorPersonal(Integer personalId) {
        List<TreinoExercicioResumoDTO> resumosSemQtd = treinoExercicioRepository.findResumosSemQuantidade(personalId);

        Map<Integer, Long> quantidadePorTreino = treinoExercicioRepository.countExerciciosPorTreino().stream()
                .collect(Collectors.toMap(
                        row -> (Integer) row[0],
                        row -> (Long) row[1]
                ));

        return resumosSemQtd.stream()
                .map(dto -> new TreinoExercicioResumoDTO(
                        dto.treinoExercicioId(),
                        dto.treinoId(),
                        dto.nomeTreino(),
                        dto.grauDificuldade(),
                        dto.favorito(),
                        dto.origemTreinoExercicio(),
                        quantidadePorTreino.getOrDefault(dto.treinoId(), 0L).intValue()
                ))
                .toList();
    }

    public List<TreinoExercicioResponseGetDto> listarTodos(){
        return treinoExercicioRepository.findAll()
                .stream()
                .map(treinoExercicioMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public TreinoExercicioResponseGetDto atualizar(Integer id, TreinoExercicioRequestUpdateDto treinoDTO, Integer exercicioId, Integer treinoId){
        validarEnums(Map.of(
                new OrigemTreinoExercicioEnumValidator(), treinoDTO.origemTreinoExercicio(),
                new GrauDificuldadeEnumValidator(), treinoDTO.grauDificuldade()
        ));

        Treino treinoExistente = treinoRepository.findById(treinoId).
                orElseThrow(() -> new ApiExceptions.BadRequestException("Treino com o ID " + treinoId + " não encontrado."));

        Exercicio exercicioExistente = exercicioRepository.findById(exercicioId).
                orElseThrow(() -> new ApiExceptions.BadRequestException("Exercício com o ID " + exercicioId + " não encontrado."));

        TreinoExercicio treinoExercicioExistente = treinoExercicioRepository.findById(id).
                orElseThrow(() -> new ApiExceptions.BadRequestException("Exercício com o ID " + id + " não encontrado."));


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

    public void removerAssociacao(Integer id) {
        TreinoExercicio treinoExistente = treinoExercicioRepository.findById(id)
                .orElseThrow(() -> new ApiExceptions.ResourceNotFoundException("Treino com ID " + id + " não encontrado"));

        treinoExistente.setTreinos(null);
        treinoExistente.setExercicio(null);
        treinoExercicioRepository.save(treinoExistente); // desassocia
    }

    public List<TreinoExercicioResponseGetDto> cadastrarComVariosExercicios(TreinoExercicioAssociacaoRequestDTO treinosDto){

        Treino treinoExistente = treinoRepository.findById(treinosDto.treinoId()).
                orElseThrow(() -> new ApiExceptions.BadRequestException("Treino com o ID " + treinosDto.treinoId() + " não encontrado."));

        List<TreinoExercicio> treinoExercicios = new ArrayList<>();

        for (TreinoExercicioRequestPostDto dto : treinosDto.exercicios()){
            validarEnums(Map.of(
                    new OrigemTreinoExercicioEnumValidator(), dto.origemTreinoExercicio(),
                    new GrauDificuldadeEnumValidator(), dto.grauDificuldade()
            ));

            Exercicio exercicioExistente = exercicioRepository.findById(dto.exercicioId()).
                    orElseThrow(() -> new ApiExceptions.BadRequestException("Exercício com o ID " + dto.exercicioId() + " não encontrado."));

            boolean jaAssociado = treinoExercicioRepository.existsByTreinos_IdAndExercicio_Id(treinosDto.treinoId(), dto.exercicioId());
            if (jaAssociado){
                throw new ApiExceptions.BadRequestException("Exercício com o ID " + dto.exercicioId() + " já está associado ao treino com ID " + treinosDto.treinoId());
            }

            TreinoExercicio novoTreino = treinoExercicioMapper.toEntity(dto);
            novoTreino.setTreinos(treinoExistente);
            novoTreino.setExercicio(exercicioExistente);

            treinoExercicios.add(novoTreino);
        }

        List<TreinoExercicio> salvos = treinoExercicioRepository.saveAll(treinoExercicios);

        return salvos.stream()
                .map(treinoExercicioMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}
