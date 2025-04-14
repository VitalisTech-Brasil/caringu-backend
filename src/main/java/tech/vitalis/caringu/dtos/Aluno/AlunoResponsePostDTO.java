package tech.vitalis.caringu.dtos.Aluno;

import tech.vitalis.caringu.enums.Aluno.NivelAtividadeEnum;
import tech.vitalis.caringu.enums.Aluno.NivelExperienciaEnum;
import tech.vitalis.caringu.enums.Pessoa.GeneroEnum;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record AlunoResponsePostDTO(
        Integer id,
        String nome,
        String email,
        String celular,
        LocalDate dataNascimento,
        GeneroEnum genero,
        Double peso,
        Double altura,
        NivelAtividadeEnum nivelAtividade,
        NivelExperienciaEnum nivelExperiencia,
        LocalDateTime dataCadastro
) {
}
