package tech.vitalis.caringu.service;

import org.springframework.stereotype.Service;
import tech.vitalis.caringu.dtos.Especialidade.EspecialidadeResponseGetDTO;
import tech.vitalis.caringu.mapper.EspecialidadeMapper;
import tech.vitalis.caringu.repository.EspecialidadeRepository;

import java.util.List;

@Service
public class EspecialidadeService {

    private final EspecialidadeRepository repository;
    private final EspecialidadeMapper mapper;

    public EspecialidadeService(EspecialidadeRepository repository, EspecialidadeMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<EspecialidadeResponseGetDTO> listarTodas() {
        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }
}
