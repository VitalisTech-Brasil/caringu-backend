package tech.vitalis.caringu.dtos.Aula;

public record TotalAulasAgendamentoResponseGetDTO(
        Integer idAluno,
        String nomeAluno,
        Integer totalAulasPlano,
        Long aulasConfirmadas,
        Long aulasRascunho,
        Long aulasRestantes
) {
}
