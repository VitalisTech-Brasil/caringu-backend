package tech.vitalis.caringu.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import tech.vitalis.caringu.dtos.Pessoa.CriacaoPessoaDTO;
import tech.vitalis.caringu.dtos.Pessoa.RespostaPessoaDTO;
import tech.vitalis.caringu.entity.Pessoa;

@Mapper(componentModel = "spring")
public interface PessoaMapper {

    //Usuario toEntity(UsuarioRecord dto);

    Pessoa toEntity(CriacaoPessoaDTO dto);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    @Mapping(target = "email", source = "email")
    RespostaPessoaDTO toDTO(Pessoa usuario);

    void updatePessoaFromDto(CriacaoPessoaDTO dto, @MappingTarget Pessoa usuario);
}