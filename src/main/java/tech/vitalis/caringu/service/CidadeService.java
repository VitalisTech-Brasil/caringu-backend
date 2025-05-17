package tech.vitalis.caringu.service;

import org.springframework.stereotype.Service;
import tech.vitalis.caringu.dtos.Cidade.CidadeResponseGetDTO;
import tech.vitalis.caringu.entity.Cidade;
import tech.vitalis.caringu.exception.Cidade.CidadeJaExisteException;
import tech.vitalis.caringu.exception.Cidade.CidadeNaoEncontradaException;
import tech.vitalis.caringu.mapper.CidadeMapper;
import tech.vitalis.caringu.repository.CidadeRepository;

import java.util.List;

@Service
public class CidadeService {

    private final CidadeRepository repository;
    private final CidadeMapper mapper;

    public CidadeService(CidadeRepository repository, CidadeMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<CidadeResponseGetDTO> listar() {
        return repository.findAll().stream()
                .map(mapper::toDTO)
                .toList();
    }

    public CidadeResponseGetDTO buscarPorId(Integer id) {
        Cidade cidade = repository.findById(id)
                .orElseThrow(() -> new CidadeNaoEncontradaException("Cidade com ID %d não encontrada".formatted(id)));

        return mapper.toDTO(cidade);
    }

    public List<CidadeResponseGetDTO> buscarPorNome(String nome) {
        return repository.findByNomeContainingIgnoreCase(nome)
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    public CidadeResponseGetDTO cadastrar(Cidade cidade) {
        boolean existe = repository.existsByNomeIgnoreCaseAndEstadoId(cidade.getNome(), cidade.getEstado().getId());

        if (existe) {
            throw new CidadeJaExisteException("Já existe uma cidade com esse nome no estado informado.");
        }

        return mapper.toDTO(repository.save(cidade));
    }

    public CidadeResponseGetDTO atualizar(Integer id, Cidade novaCidade) {
        Cidade existente = repository.findById(id)
                .orElseThrow(() -> new CidadeNaoEncontradaException("Cidade com ID %d não encontrada".formatted(id)));

        boolean nomeDuplicado = repository.existsByNomeIgnoreCaseAndEstadoId(novaCidade.getNome(), novaCidade.getEstado().getId())
                && !existente.getId().equals(novaCidade.getId());

        if (nomeDuplicado) {
            throw new CidadeJaExisteException("Já existe uma cidade com esse nome no estado informado.");
        }

        existente.setNome(novaCidade.getNome());
        existente.setEstado(novaCidade.getEstado());

        return mapper.toDTO(repository.save(existente));
    }

    public void deletar(Integer id) {
        if (!repository.existsById(id)) {
            throw new CidadeNaoEncontradaException("Cidade com ID %d não encontrada".formatted(id));
        }
        repository.deleteById(id);
    }
}
