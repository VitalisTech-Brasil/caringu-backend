package tech.vitalis.caringu.mapper;

import org.springframework.stereotype.Component;
import tech.vitalis.caringu.dtos.Exercicio.ExercicioRequestPostDTO;
import tech.vitalis.caringu.dtos.Exercicio.ExercicioRequestPostFuncionalDTO;
import tech.vitalis.caringu.dtos.Exercicio.ExercicioResponseGetDTO;
import tech.vitalis.caringu.entity.Exercicio;

@Component
public class ExercicioMapper {

    public Exercicio toEntity(ExercicioRequestPostDTO dto){
        if (dto == null) return null;

        Exercicio exercicio = new Exercicio();
        exercicio.setNome(dto.nome());
        exercicio.setGrupoMuscular(dto.grupoMuscular());
        exercicio.setUrlVideo(dto.urlVideo());
        exercicio.setObservacoes(dto.observacoes());
        exercicio.setFavorito(dto.favorito());
        exercicio.setOrigem(dto.origem());
        return exercicio;
    }

    public Exercicio toEntity(ExercicioRequestPostFuncionalDTO dto){
        if (dto == null) return null;

        Exercicio exercicio = new Exercicio();
        exercicio.setNome(dto.nome());
        exercicio.setGrupoMuscular(dto.grupoMuscular());
        exercicio.setUrlVideo(dto.urlVideo());
        exercicio.setObservacoes(dto.observacoes());
        return exercicio;
    }

    public ExercicioResponseGetDTO toResponseDTO(Exercicio exercicio){
        if (exercicio == null) return null;

        return new ExercicioResponseGetDTO(
                exercicio.getId(),
                exercicio.getPersonal() != null ? exercicio.getPersonal().getId() : null,
                exercicio.getNome(),
                exercicio.getGrupoMuscular(),
                exercicio.getUrlVideo(),
                exercicio.getObservacoes(),
                exercicio.getFavorito(),
                exercicio.getOrigem()
        );
    }

}