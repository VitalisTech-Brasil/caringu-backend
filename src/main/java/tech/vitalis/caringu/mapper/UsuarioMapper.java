package tech.vitalis.caringu.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import tech.vitalis.caringu.dtos.Usuario.CriacaoUsuarioDTO;
import tech.vitalis.caringu.dtos.Usuario.RespostaUsuarioDTO;
import tech.vitalis.caringu.entity.Usuario;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    //Usuario toEntity(UsuarioRecord dto);

    Usuario toEntity(CriacaoUsuarioDTO dto);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    @Mapping(target = "email", source = "email")
    RespostaUsuarioDTO toDTO(Usuario usuario);

    void updateUsuarioFromDto(CriacaoUsuarioDTO dto, @MappingTarget Usuario usuario);
}