package tech.vitalis.caringu.service;

import org.springframework.stereotype.Service;
import tech.vitalis.caringu.dtos.EvolucaoCorporal.EvolucaoCorporalRequestPostDTO;
import tech.vitalis.caringu.dtos.EvolucaoCorporal.EvolucaoCorporalResponseGetDTO;
import tech.vitalis.caringu.entity.Aluno;
import tech.vitalis.caringu.entity.EvolucaoCorporal;
import tech.vitalis.caringu.exception.Aluno.AlunoNaoEncontradoException;
import tech.vitalis.caringu.exception.EvolucaoCorporal.EvolucaoCorporalJaExisteException;
import tech.vitalis.caringu.exception.EvolucaoCorporal.EvolucaoCorporalNaoEncontradaException;
import tech.vitalis.caringu.mapper.EvolucaoCorporalMapper;
import tech.vitalis.caringu.repository.AlunoRepository;
import tech.vitalis.caringu.repository.EvolucaoCorporalRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EvolucaoCorporalService {

    private final EvolucaoCorporalRepository repository;
    private final AlunoRepository alunoRepository;
    private final EvolucaoCorporalMapper mapper;

    public EvolucaoCorporalService(EvolucaoCorporalRepository repository,
                                   AlunoRepository alunoRepository,
                                   EvolucaoCorporalMapper mapper) {
        this.repository = repository;
        this.alunoRepository = alunoRepository;
        this.mapper = mapper;
    }

    public List<EvolucaoCorporalResponseGetDTO> listar() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponseDTO)
                .toList();
    }

    public List<EvolucaoCorporalResponseGetDTO> listarPorAluno(Integer alunoId) {
        alunoRepository.findById(alunoId)
                .orElseThrow(() -> new AlunoNaoEncontradoException("Aluno com ID %d não encontrado".formatted(alunoId)));

        return repository.findByAlunoId(alunoId).stream()
                .map(mapper::toResponseDTO)
                .toList();
    }

    public EvolucaoCorporalResponseGetDTO cadastrar(EvolucaoCorporalRequestPostDTO dto) {
        Aluno aluno = alunoRepository.findById(dto.alunoId())
                .orElseThrow(() -> new AlunoNaoEncontradoException(
                        "Aluno com ID %d não encontrado".formatted(dto.alunoId()))
                );

        boolean duplicado = repository.existsByAlunoIdAndTipoAndPeriodoAvaliacao(
                dto.alunoId(), dto.tipo(), dto.periodoAvaliacao()
        );

        if (duplicado) {
            throw new EvolucaoCorporalJaExisteException("Já existe uma evolução corporal com esse tipo e período para o aluno.");
        }

        EvolucaoCorporal nova = mapper.toEntity(dto, aluno);
        nova.setAluno(aluno);
        nova.setDataEnvio(LocalDateTime.now());

        EvolucaoCorporal salvo = repository.save(nova);
        return mapper.toResponseDTO(salvo);
    }

    public void deletar(Integer id) {
        boolean existe = repository.existsById(id);

        if (!existe) {
            throw new EvolucaoCorporalNaoEncontradaException("Evolução corporal com ID %d não encontrada.".formatted(id));
        }

        repository.deleteById(id);
    }
}
