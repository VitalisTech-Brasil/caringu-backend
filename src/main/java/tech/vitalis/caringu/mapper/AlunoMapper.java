package tech.vitalis.caringu.mapper;


import tech.vitalis.caringu.dtos.Aluno.AlunoRequestPatchDTO;
import tech.vitalis.caringu.dtos.Aluno.AlunoRequestPostDTO;
import tech.vitalis.caringu.dtos.Aluno.AlunoResponseGetDTO;
import tech.vitalis.caringu.entity.Aluno;

public class AlunoMapper {

    public static Aluno toEntity(AlunoRequestPostDTO cadastroDTO) {
        Aluno aluno = new Aluno();

        aluno.setNome(cadastroDTO.nome());
        aluno.setEmail(cadastroDTO.email());
        aluno.setSenha(cadastroDTO.senha());
        aluno.setCelular(cadastroDTO.celular());
        aluno.setDataNascimento(cadastroDTO.dataNascimento());
        aluno.setGenero(cadastroDTO.genero());

        aluno.setPeso(cadastroDTO.peso());
        aluno.setAltura(cadastroDTO.altura());
        aluno.setNivelAtividade(cadastroDTO.nivelAtividade());
        aluno.setNivelExperiencia(cadastroDTO.nivelExperiencia());

        return aluno;
    }

    public static AlunoResponseGetDTO toRespostaDTO(Aluno aluno) {

        return new AlunoResponseGetDTO(
                aluno.getId(),
                aluno.getNome(),
                aluno.getEmail(),
                aluno.getCelular(),
                aluno.getDataNascimento(),
                aluno.getGenero(),
                aluno.getPeso(),
                aluno.getAltura(),
                aluno.getNivelAtividade(),
                aluno.getNivelExperiencia(),
                aluno.getDataCadastro()
        );
    }

    public static void atualizarAlunoComDTOParcial(Aluno aluno, AlunoRequestPatchDTO dto) {
        if (dto.nome() != null) aluno.setNome(dto.nome());
        if (dto.email() != null) aluno.setEmail(dto.email());
        if (dto.senha() != null) aluno.setSenha(dto.senha());
        if (dto.celular() != null) aluno.setCelular(dto.celular());
        if (dto.dataNascimento() != null) aluno.setDataNascimento(dto.dataNascimento());
        if (dto.genero() != null) aluno.setGenero(dto.genero());
        if (dto.peso() != null) aluno.setPeso(dto.peso());
        if (dto.altura() != null) aluno.setAltura(dto.altura());
        if (dto.nivelAtividade() != null) aluno.setNivelAtividade(dto.nivelAtividade());
        if (dto.nivelExperiencia() != null) aluno.setNivelExperiencia(dto.nivelExperiencia());
    }
}
