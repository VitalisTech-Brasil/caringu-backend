package tech.vitalis.caringu.mapper;

import org.springframework.stereotype.Component;
import tech.vitalis.caringu.dtos.Cidade.CidadeRequestPostDTO;
import tech.vitalis.caringu.dtos.Cidade.CidadeRequestPutDTO;
import tech.vitalis.caringu.dtos.Cidade.CidadeResponseGetDTO;
import tech.vitalis.caringu.entity.Cidade;
@Component
public class CidadeMapper {


    public Cidade toEntity(CidadeRequestPostDTO dto) {
        Cidade cidade = new Cidade();
        cidade.setNome(dto.nome());

        return cidade;
    }

    public Cidade toEntity(CidadeRequestPutDTO dto) {

        Cidade cidade = new Cidade();
        cidade.setNome(dto.nome());
        return cidade;
    }

    public CidadeResponseGetDTO toDTO(Cidade cidade) {
        return new CidadeResponseGetDTO(
                cidade.getId(),
                cidade.getNome()
        );
    }
}
