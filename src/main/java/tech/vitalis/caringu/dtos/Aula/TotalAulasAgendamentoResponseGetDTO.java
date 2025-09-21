package tech.vitalis.caringu.dtos.Aula;

import tech.vitalis.caringu.enums.Aluno.NivelAtividadeEnum;

public record TotalAulasAgendamentoResponseGetDTO(
        Integer idAluno,
        String nomeAluno,
        String telefone,
        String objetivo,
        NivelAtividadeEnum nivelAtividade,
        String nomePlano,
        Integer totalAulasPlano,
        Long aulasConfirmadas,
        Long aulasRascunho,
        Long aulasRestantes
) {
}
