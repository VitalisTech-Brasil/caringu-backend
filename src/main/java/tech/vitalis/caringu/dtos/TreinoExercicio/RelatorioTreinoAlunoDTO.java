package tech.vitalis.caringu.dtos.TreinoExercicio;

public record RelatorioTreinoAlunoDTO(
        Integer idAluno,
        String nomeAluno,

        Integer idTreino,
        String nomeTreino,

        Integer idPersonal,
        String nomePersonal,
        Long qtdVezesConcluidos
) {
}
