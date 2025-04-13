package tech.vitalis.caringu.dtos.Aluno;

import tech.vitalis.caringu.enums.Aluno.NivelAtividadeEnum;
import tech.vitalis.caringu.enums.Aluno.NivelExperienciaEnum;
import tech.vitalis.caringu.enums.Pessoa.GeneroEnum;

import java.time.LocalDateTime;
import java.util.Date;

public record AlunoRespostaDTO(
        Integer id,
        String nome,
        String email,
        String celular,
        Date dataNascimento,
        GeneroEnum genero,
        Double peso,
        Double altura,
        NivelAtividadeEnum nivelAtividade,
        NivelExperienciaEnum nivelExperiencia,
        LocalDateTime dataCadastro
) {
}
