package tech.vitalis.caringu.dtos.PerfilAluno;

import tech.vitalis.caringu.enums.Aluno.NivelAtividadeEnum;
import tech.vitalis.caringu.enums.Aluno.NivelExperienciaEnum;

public record AlunoGetPerfilDetalhesDTO(
        Double peso,
        Double altura,
        NivelAtividadeEnum nivelAtividade,
        NivelExperienciaEnum nivelExperiencia
) {
}
