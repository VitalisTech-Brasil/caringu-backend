package tech.vitalis.caringu.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import tech.vitalis.caringu.dtos.Exercicio.CriacaoExercicioDTO;
import tech.vitalis.caringu.dtos.Exercicio.RespostaExercicioDTO;
import tech.vitalis.caringu.model.Exercicio;

@Mapper(componentModel = "spring")
public interface ExercicioMapper {


    @Mapping(target = "id", ignore = true) // Garante que o ID não cause problemas
    @Mapping(target = "nome", source = "nome")
    @Mapping(target = "grupoMuscular", source = "grupoMuscular")
    @Mapping(target = "favorito", source = "favorito")
    @Mapping(target = "origem", source = "origem")
    Exercicio toEntity(CriacaoExercicioDTO dto);

    RespostaExercicioDTO toDTO(Exercicio exercicio);

    void updateExercicioFromDto(CriacaoExercicioDTO exercicioDto, @MappingTarget Exercicio exercicioExistente);
}