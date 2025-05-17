package tech.vitalis.caringu.mapper;

import org.springframework.stereotype.Component;
import tech.vitalis.caringu.dtos.Cidade.CidadeRequestPostDTO;
import tech.vitalis.caringu.dtos.Cidade.CidadeRequestPutDTO;
import tech.vitalis.caringu.dtos.Cidade.CidadeResponseGetDTO;
import tech.vitalis.caringu.entity.Cidade;
import tech.vitalis.caringu.entity.Estado;
import tech.vitalis.caringu.exception.Estado.EstadoNaoEncontradoException;
import tech.vitalis.caringu.repository.EstadoRepository;

@Component
public class CidadeMapper {

    private final EstadoRepository estadoRepository;
    private final EstadoMapper estadoMapper;

    public CidadeMapper(EstadoRepository estadoRepository, EstadoMapper estadoMapper) {
        this.estadoRepository = estadoRepository;
        this.estadoMapper = estadoMapper;
    }

    public Cidade toEntity(CidadeRequestPostDTO dto) {
        Estado estado = estadoRepository.findById(dto.estadoId())
                .orElseThrow(() -> new EstadoNaoEncontradoException("Estado com ID %d não encontrado".formatted(dto.estadoId())));
        Cidade cidade = new Cidade();
        cidade.setNome(dto.nome());
        cidade.setEstado(estado);

        return cidade;
    }

    public Cidade toEntity(CidadeRequestPutDTO dto) {
        Estado estado = estadoRepository.findById(dto.estadoId())
                .orElseThrow(() -> new EstadoNaoEncontradoException("Estado com ID %d não encontrado".formatted(dto.estadoId())));

        Cidade cidade = new Cidade();
        cidade.setNome(dto.nome());
        cidade.setEstado(estado);
        return cidade;
    }

    public CidadeResponseGetDTO toDTO(Cidade cidade) {
        return new CidadeResponseGetDTO(
                cidade.getId(),
                cidade.getNome(),
                estadoMapper.toResponseDTO(cidade.getEstado())
        );
    }
}
