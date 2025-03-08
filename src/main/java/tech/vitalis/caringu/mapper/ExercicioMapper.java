package tech.vitalis.caringu.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import tech.vitalis.caringu.dtos.Exercicio.CriacaoExercicioDTO;
import tech.vitalis.caringu.dtos.Exercicio.RespostaExercicioDTO;
import tech.vitalis.caringu.model.Exercicio;

@Mapper(componentModel = "spring")
public interface ExercicioMapper {

    Exercicio toEntity(CriacaoExercicioDTO dto);

    @Mapping(target = "id", source = "id")
    RespostaExercicioDTO toDTO(Exercicio exercicio);

    void updateExercicioFromDto(CriacaoExercicioDTO exercicioDto, @MappingTarget Exercicio exercicioExistente);
}