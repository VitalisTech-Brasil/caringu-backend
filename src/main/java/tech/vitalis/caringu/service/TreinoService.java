package tech.vitalis.caringu.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import tech.vitalis.caringu.dtos.Treino.TreinoRequestPostDTO;
import tech.vitalis.caringu.dtos.Treino.TreinoRequestUpdateDto;
import tech.vitalis.caringu.dtos.Treino.TreinoResponseGetDTO;
import tech.vitalis.caringu.entity.PersonalTrainer;
import tech.vitalis.caringu.entity.Pessoa;
import tech.vitalis.caringu.entity.Treino;
import tech.vitalis.caringu.entity.TreinoExercicio;
import tech.vitalis.caringu.exception.ApiExceptions;
import tech.vitalis.caringu.mapper.TreinoMapper;
import tech.vitalis.caringu.repository.PersonalTrainerRepository;
import tech.vitalis.caringu.repository.TreinoExercicioRepository;
import tech.vitalis.caringu.repository.TreinoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TreinoService {

    private final TreinoRepository treinoRepository;
    private final TreinoExercicioRepository treinoExercicioRepository;
    private final TreinoMapper treinoMapper;
    private final PersonalTrainerRepository personalRepository;

    public TreinoService(TreinoRepository treinoRepository, TreinoExercicioRepository treinoExercicioRepository, TreinoMapper treinoMapper, PersonalTrainerRepository personalRepository) {
        this.treinoRepository = treinoRepository;
        this.treinoExercicioRepository = treinoExercicioRepository;
        this.treinoMapper = treinoMapper;
        this.personalRepository = personalRepository;
    }


    public TreinoResponseGetDTO cadastrar(TreinoRequestPostDTO treinoDto){
        // já está validando se existe o personal
        PersonalTrainer personal = personalRepository.findById(treinoDto.personalId())
                .orElseThrow(() -> new ApiExceptions.BadRequestException("Personal Trainer com o ID " + treinoDto.personalId() + " não encontrado."));

        // agora sim seta o personal completo
        Treino novoTreino = treinoMapper.toEntity(treinoDto);
        novoTreino.setPersonal(personal);

        Treino treinoSalvo = treinoRepository.save(novoTreino);

        return treinoMapper.toResponseDTO(treinoSalvo);
    }

    public TreinoResponseGetDTO buscarPorId(Integer id){
        Treino treino = treinoRepository.findById(id)
                .orElseThrow(() -> new ApiExceptions.ResourceNotFoundException("Treino com ID " + id + " não encontrado"));
        return treinoMapper.toResponseDTO(treino);
    }

    public Integer obterQuantidadeTreinosCriados(Integer personalId) {
        return treinoRepository.countByPersonalId(personalId);
    }

    public List<TreinoResponseGetDTO> listarTodos(){
        return treinoRepository.findAll()
                .stream()
                .map(treinoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public TreinoResponseGetDTO atualizar(Integer id, TreinoRequestUpdateDto treinoDto, Integer personalId){
        Treino treinoExistente = treinoRepository.findById(id)
                .orElseThrow(() -> new ApiExceptions.ResourceNotFoundException("Treino com ID " + id + " não encontrado"));

        PersonalTrainer personal = personalRepository.findById(personalId)
                .orElseThrow(() -> new ApiExceptions.ResourceNotFoundException("Personal Trainer com ID " + personalId + " não encontrado"));

        treinoExistente.setNome(treinoDto.nome());
        treinoExistente.setDescricao(treinoDto.descricao());
        treinoExistente.setPersonal(personal);

        Treino treinoAtualizado = treinoRepository.save(treinoExistente);
        return treinoMapper.toResponseDTO(treinoAtualizado);
    }

    public void remover(Integer id){
        Treino treinoExistente = treinoRepository.findById(id)
                .orElseThrow(() -> new ApiExceptions.ResourceNotFoundException("Treino com ID " + id + " não encontrado"));

        treinoExistente.setPersonal(null);
        treinoRepository.deleteById(id);
    }

    public void removerAssociacaoComPersonal(Integer id) {
        Treino treinoExistente = treinoRepository.findById(id)
                .orElseThrow(() -> new ApiExceptions.ResourceNotFoundException("Treino com ID " + id + " não encontrado"));

        treinoExistente.setPersonal(null); // desassocia
        treinoRepository.save(treinoExistente); // salva o treino atualizado
    }

    @Transactional
    public void removerComDesassociacao(Integer id) {
        Treino treino = treinoRepository.findById(id)
                .orElseThrow(() -> new ApiExceptions.ResourceNotFoundException("Treino com ID " + id + " não encontrado"));

        // Buscar todos os treinos_exercicios desse treino
        List<TreinoExercicio> exercicios = treinoExercicioRepository.findByTreinosId(id);

        for (TreinoExercicio ex : exercicios) {
            ex.setTreinos(null); // desassociar
            treinoExercicioRepository.save(ex);
        }

        treino.setPersonal(null); // opcional
        treinoRepository.save(treino);
        treinoRepository.delete(treino);
    }

    @Transactional
    public void atualizarFavorito(Integer treinoId, boolean favorito) {
        Treino favoritoTreino = treinoRepository.findById(treinoId)
                .orElseThrow(() -> new ApiExceptions.ResourceNotFoundException("Treino não encontrado"));

        favoritoTreino.setFavorito(favorito);

    }

    public List<TreinoResponseGetDTO> buscarTreinoPorNome(String nome){
        List<Treino> treinos = treinoRepository.findByNomeContainingIgnoreCase(nome)
                .orElseThrow(() -> new ApiExceptions.ResourceNotFoundException("Treino não encontrado com NOME: " + nome));

        List<TreinoResponseGetDTO> listaTreinos = new ArrayList<>();

        for (Treino treino : treinos){
            TreinoResponseGetDTO treinoDto = treinoMapper.toResponseDTO(treino);
            listaTreinos.add(treinoDto);
        }

        return listaTreinos;
    }

}
