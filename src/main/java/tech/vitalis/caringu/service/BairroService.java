package tech.vitalis.caringu.service;

import org.springframework.stereotype.Service;
import tech.vitalis.caringu.dtos.Bairro.BairroResponseGetDTO;
import tech.vitalis.caringu.entity.Bairro;
import tech.vitalis.caringu.exception.Bairro.BairroJaExisteException;
import tech.vitalis.caringu.exception.Bairro.BairroNaoEncontradoException;
import tech.vitalis.caringu.mapper.BairroMapper;
import tech.vitalis.caringu.repository.BairroRepository;

import java.util.List;

@Service
public class BairroService {

    private final BairroRepository repository;
    private final BairroMapper mapper;

    public BairroService(BairroRepository repository, BairroMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<BairroResponseGetDTO> listar() {
        return repository.findAll().stream()
                .map(mapper::toDTO)
                .toList();
    }

    public BairroResponseGetDTO buscarPorId(Integer id) {
        Bairro bairro = repository.findById(id)
                .orElseThrow(() -> new BairroNaoEncontradoException("Bairro com ID %d não encontrado".formatted(id)));

        return mapper.toDTO(bairro);
    }

    public List<BairroResponseGetDTO> buscarPorNome(String nome) {
        return repository.findByNomeContainingIgnoreCase(nome)
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    public BairroResponseGetDTO cadastrar(Bairro bairro) {
        boolean existe = repository.existsByNomeIgnoreCaseAndCidadeId(bairro.getNome(), bairro.getCidade().getId());

        if (existe) {
            throw new BairroJaExisteException("Já existe um bairro com esse nome na cidade informada");
        }

        return mapper.toDTO(repository.save(bairro));
    }

    public BairroResponseGetDTO atualizar(Integer id, Bairro novoBairro) {
        Bairro existente = repository.findById(id)
                .orElseThrow(() -> new BairroNaoEncontradoException("Bairro com ID %d não encontrado".formatted(id)));

        boolean nomeDuplicado = repository.existsByNomeIgnoreCaseAndCidadeId(novoBairro.getNome(), novoBairro.getCidade().getId())
                && !existente.getId().equals(novoBairro.getId());

        if (nomeDuplicado) {
            throw new BairroJaExisteException("Já existe um bairro com esse nome na cidade informada.");
        }

        existente.setNome(novoBairro.getNome());
        existente.setCidade(novoBairro.getCidade());

        return mapper.toDTO(repository.save(existente));
    }

    public void deletar(Integer id) {
        if (!repository.existsById(id)) {
            throw new BairroNaoEncontradoException("Bairro com ID %d não encontrado".formatted(id));
        }

        repository.deleteById(id);
    }


}
