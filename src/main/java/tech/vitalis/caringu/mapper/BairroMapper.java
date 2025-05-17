package tech.vitalis.caringu.mapper;

import org.springframework.stereotype.Component;
import tech.vitalis.caringu.dtos.Bairro.BairroRequestPostDTO;
import tech.vitalis.caringu.dtos.Bairro.BairroRequestPutDTO;
import tech.vitalis.caringu.dtos.Bairro.BairroResponseGetDTO;
import tech.vitalis.caringu.entity.Bairro;
import tech.vitalis.caringu.entity.Cidade;
import tech.vitalis.caringu.exception.Cidade.CidadeNaoEncontradaException;
import tech.vitalis.caringu.repository.CidadeRepository;

@Component
public class BairroMapper {

    private final CidadeRepository cidadeRepository;
    private final CidadeMapper cidadeMapper;

    public BairroMapper(CidadeRepository cidadeRepository, CidadeMapper cidadeMapper) {
        this.cidadeRepository = cidadeRepository;
        this.cidadeMapper = cidadeMapper;
    }

    public Bairro toEntity(BairroRequestPostDTO dto) {
        Cidade cidade = cidadeRepository.findById(dto.cidadeId())
                .orElseThrow(() -> new CidadeNaoEncontradaException("Cidade com ID %d não encontrada".formatted(dto.cidadeId())));

        Bairro bairro = new Bairro();
        bairro.setNome(dto.nome());
        bairro.setCidade(cidade);

        return bairro;
    }

    public Bairro toEntity(BairroRequestPutDTO dto) {
        Cidade cidade = cidadeRepository.findById(dto.cidadeId())
                .orElseThrow(() -> new CidadeNaoEncontradaException("Cidade com ID %d não encontrada".formatted(dto.cidadeId())));

        Bairro bairro = new Bairro();
        bairro.setNome(dto.nome());
        bairro.setCidade(cidade);

        return bairro;
    }

    public BairroResponseGetDTO toDTO(Bairro bairro) {
        return new BairroResponseGetDTO(
                bairro.getId(),
                bairro.getNome(),
                cidadeMapper.toDTO(bairro.getCidade())
        );
    }
}
