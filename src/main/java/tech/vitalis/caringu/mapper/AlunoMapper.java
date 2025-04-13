package tech.vitalis.caringu.mapper;


import tech.vitalis.caringu.dtos.Aluno.AlunoCadastroDTO;
import tech.vitalis.caringu.dtos.Aluno.AlunoRespostaDTO;
import tech.vitalis.caringu.entity.Aluno;

public class AlunoMapper {

    public static Aluno toEntity(AlunoCadastroDTO cadastroDTO) {
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

    public static AlunoRespostaDTO toRespostaDTO(Aluno aluno) {

        return new AlunoRespostaDTO(
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
}
