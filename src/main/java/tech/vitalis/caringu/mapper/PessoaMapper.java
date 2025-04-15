package tech.vitalis.caringu.mapper;

import org.springframework.stereotype.Component;
import tech.vitalis.caringu.dtos.Pessoa.PessoaRequestPostDTO;
import tech.vitalis.caringu.dtos.Pessoa.PessoaResponseGetDTO;
import tech.vitalis.caringu.entity.Pessoa;

@Component
public class PessoaMapper {

    public Pessoa toEntity(PessoaRequestPostDTO dto) {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome(dto.nome());
        pessoa.setEmail(dto.email());
        pessoa.setSenha(dto.senha());
        pessoa.setCelular(dto.celular());
        pessoa.setUrlFotoPerfil(dto.urlFotoPerfil());
        pessoa.setDataNascimento(dto.dataNascimento());
        pessoa.setGenero(dto.genero());
        return pessoa;
    }

    public PessoaResponseGetDTO toDTO(Pessoa entity) {
        return new PessoaResponseGetDTO(
                entity.getId(),
                entity.getNome(),
                entity.getEmail(),
                entity.getCelular(),
                entity.getUrlFotoPerfil(),
                entity.getDataNascimento(),
                entity.getGenero(),
                entity.getDataCadastro()
        );
    }

    public void updatePessoaFromDto(PessoaRequestPostDTO dto, Pessoa pessoa) {
        if (dto.nome() != null) {
            pessoa.setNome(dto.nome());
        }
        if (dto.email() != null) {
            pessoa.setEmail(dto.email());
        }
        if (dto.senha() != null) {
            pessoa.setSenha(dto.senha());
        }
        if (dto.celular() != null) {
            pessoa.setCelular(dto.celular());
        }
        if (dto.urlFotoPerfil() != null) {
            pessoa.setUrlFotoPerfil(dto.urlFotoPerfil());
        }
        if (dto.dataNascimento() != null) {
            pessoa.setDataNascimento(dto.dataNascimento());
        }
        if (dto.genero() != null) {
            pessoa.setGenero(dto.genero());
        }
    }
}