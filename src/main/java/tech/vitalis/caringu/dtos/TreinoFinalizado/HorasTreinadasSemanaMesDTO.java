package tech.vitalis.caringu.dtos.TreinoFinalizado;

public record HorasTreinadasSemanaMesDTO(
        Integer idAluno,
        String nomeAluno,
        Integer idExercicio,
        String nomeExercicio,
        Integer ano,
        Integer mes,
        Integer anoSemana,
        Double horasTreinadas
) {
}
