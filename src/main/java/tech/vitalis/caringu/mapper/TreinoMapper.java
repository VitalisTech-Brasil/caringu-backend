package tech.vitalis.caringu.mapper;

import org.springframework.stereotype.Component;
import tech.vitalis.caringu.dtos.Treino.TreinoRequestPostDTO;
import tech.vitalis.caringu.dtos.Treino.TreinoResponseGetDTO;
import tech.vitalis.caringu.entity.Treino;

@Component
public class TreinoMapper {

    public static Treino toEntity(TreinoRequestPostDTO dto){
        if (dto == null) return null;

        Treino treino = new Treino();
        treino.setNome(dto.nome());
        treino.setDescricao(dto.descricao());
        return treino;
    }

    public TreinoResponseGetDTO toResponseDTO(Treino treino){
        if (treino == null) return null;

        return new TreinoResponseGetDTO(
                treino.getId(),
                treino.getNome(),
                treino.getDescricao(),
                PersonalTrainerMapper.toResponseDTO(treino.getPersonal())
        );
    }

}
