package tech.vitalis.caringu.service;

import org.springframework.stereotype.Service;
import tech.vitalis.caringu.dtos.AlunoPlanos.AlunoPlanosDTO;
import tech.vitalis.caringu.mapper.AlunoTreinosMapper;
import tech.vitalis.caringu.repository.AlunoPlanosRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlunoPlanosService {

    private final AlunoPlanosRepository repository;
    private final AlunoTreinosMapper mapper;

    public AlunoPlanosService(AlunoPlanosRepository repository, AlunoTreinosMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<AlunoPlanosDTO> buscarPlanosPorAluno(Integer idAluno) {
        List<Object[]> resultados = repository.findPlanosByAlunoId(idAluno);

        if (resultados.isEmpty()) {
            throw new RuntimeException("Nenhum plano encontrado para o aluno com ID: " + idAluno);
        }

        return resultados.stream()
                .map(mapper::toAlunoPlanosDTO)
                .collect(Collectors.toList());
    }
}

