package tech.vitalis.caringu.service;

import org.springframework.stereotype.Service;
import tech.vitalis.caringu.dtos.Aluno.AlunoRespostaDTO;
import tech.vitalis.caringu.entity.Aluno;
import tech.vitalis.caringu.mapper.AlunoMapper;
import tech.vitalis.caringu.repository.AlunoRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class AlunoService {

    private final AlunoRepository repository;

    public AlunoService(AlunoRepository repository) {
        this.repository = repository;
    }

    public List<AlunoRespostaDTO> listar() {
        List<Aluno> listaAlunos = repository.findAll();
        List<AlunoRespostaDTO> listaRespostaAlunos = new ArrayList<>();

        for (Aluno aluno : listaAlunos) {
            AlunoRespostaDTO respostaDTO = AlunoMapper.toRespostaDTO(aluno);
            listaRespostaAlunos.add(respostaDTO);
        }

        return listaRespostaAlunos;
    }

    public AlunoRespostaDTO cadastrar(Aluno aluno) {
        repository.save(aluno);

        AlunoRespostaDTO respostaDTO = AlunoMapper.toRespostaDTO(aluno);
        return respostaDTO;
    }
}
