package tech.vitalis.caringu.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import tech.vitalis.caringu.dtos.Treino.TreinoRequestPostDTO;
import tech.vitalis.caringu.dtos.Treino.TreinoRequestUpdateDto;
import tech.vitalis.caringu.dtos.Treino.TreinoResponseGetDTO;
import tech.vitalis.caringu.entity.PersonalTrainer;
import tech.vitalis.caringu.entity.Treino;
import tech.vitalis.caringu.entity.TreinoExercicio;
import tech.vitalis.caringu.exception.ApiExceptions;
import tech.vitalis.caringu.mapper.TreinoMapper;
import tech.vitalis.caringu.repository.PersonalTrainerRepository;
import tech.vitalis.caringu.repository.TreinoRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TreinoService {

    private final TreinoRepository treinoRepository;
    private final TreinoMapper treinoMapper;
    private final PersonalTrainerRepository personalRepository;

    public TreinoService(TreinoRepository treinoRepository, TreinoMapper treinoMapper, PersonalTrainerRepository personalRepository) {
        this.treinoRepository = treinoRepository;
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

    public void removerComDesassociacao(Integer id) {
        Treino treino = treinoRepository.findById(id)
                .orElseThrow(() -> new ApiExceptions.ResourceNotFoundException("Treino com ID " + id + " não encontrado"));

        treino.setPersonal(null);
        treinoRepository.save(treino); // desassocia
        treinoRepository.deleteById(id); // deleta
    }

    @Transactional
    public void atualizarFavorito(Integer treinoId, boolean favorito) {
        Treino favoritoTreino = treinoRepository.findById(treinoId)
                .orElseThrow(() -> new ApiExceptions.ResourceNotFoundException("Treino não encontrado"));

        favoritoTreino.setFavorito(favorito);

    }

}
