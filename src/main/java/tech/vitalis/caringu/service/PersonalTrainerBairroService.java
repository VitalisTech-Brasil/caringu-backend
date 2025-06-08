package tech.vitalis.caringu.service;

import org.springframework.stereotype.Service;
import tech.vitalis.caringu.dtos.PersonalTrainerBairro.PersonalTrainerBairroRequestPostDTO;
import tech.vitalis.caringu.dtos.PersonalTrainerBairro.PersonalTrainerBairroResponseGetDTO;
import tech.vitalis.caringu.entity.Bairro;
import tech.vitalis.caringu.entity.PersonalTrainer;
import tech.vitalis.caringu.entity.PersonalTrainerBairro;
import tech.vitalis.caringu.exception.Bairro.BairroNaoEncontradoException;
import tech.vitalis.caringu.exception.PersonalTrainer.PersonalNaoEncontradoException;
import tech.vitalis.caringu.exception.PersonalTrainerBairro.AssociacaoJaExisteException;
import tech.vitalis.caringu.mapper.PersonalTrainerBairroMapper;
import tech.vitalis.caringu.repository.BairroRepository;
import tech.vitalis.caringu.repository.PersonalTrainerBairroRepository;
import tech.vitalis.caringu.repository.PersonalTrainerRepository;

import java.util.List;

@Service
public class PersonalTrainerBairroService {

    private final PersonalTrainerBairroMapper mapper;
    private final PersonalTrainerBairroRepository repository;
    private final PersonalTrainerRepository ptRepository;
    private final BairroRepository bairroRepository;

    public PersonalTrainerBairroService(PersonalTrainerBairroMapper mapper,
                                        PersonalTrainerBairroRepository repository,
                                        PersonalTrainerRepository ptRepository,
                                        BairroRepository bairroRepository) {
        this.mapper = mapper;
        this.repository = repository;
        this.ptRepository = ptRepository;
        this.bairroRepository = bairroRepository;
    }

    public PersonalTrainerBairroResponseGetDTO associar(PersonalTrainerBairroRequestPostDTO dto) {
        if (repository.existsByPersonalTrainerIdAndBairroId(dto.personalTrainerId(), dto.bairroId())) {
            throw new AssociacaoJaExisteException();
        }

        PersonalTrainer pt = ptRepository.findById(dto.personalTrainerId())
                .orElseThrow(() -> new PersonalNaoEncontradoException("Personal Trainer com ID %d não encontrado".formatted(dto.personalTrainerId())));

        Bairro bairro = bairroRepository.findById(dto.bairroId())
                .orElseThrow(() -> new BairroNaoEncontradoException("Bairro com ID %d não encontrado".formatted(dto.bairroId())));

        PersonalTrainerBairro associacao = new PersonalTrainerBairro();
        associacao.setPersonalTrainer(pt);
        associacao.setBairro(bairro);

        PersonalTrainerBairro salvo = repository.save(associacao);

        return mapper.toResponseDTO(salvo);
    }

    public List<PersonalTrainerBairroResponseGetDTO> buscarPorPersonalTrainerId(Integer personalId) {
        return repository.findAll().stream()
                .filter(p -> p.getPersonalTrainer().getId().equals(personalId))
                .map(mapper::toResponseDTO)
                .toList();
    }

    public List<PersonalTrainerBairroResponseGetDTO> buscarPorBairroId(Integer bairroId) {
        return repository.findAll().stream()
                .filter(p -> p.getBairro().getId().equals(bairroId))
                .map(mapper::toResponseDTO)
                .toList();
    }
}
