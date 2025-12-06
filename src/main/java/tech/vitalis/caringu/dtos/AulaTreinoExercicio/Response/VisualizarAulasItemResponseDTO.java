package tech.vitalis.caringu.dtos.AulaTreinoExercicio.Response;

public record VisualizarAulasItemResponseDTO(
        Integer idAula,
        Integer idExecucaoExercicio,
        String nomeExercicio,
        String carga,
        String descanso,
        String repeticoesSeries,
        String grupoMuscular,
        Integer idAulaTreinoExercicio,
        String observacoes,
        String urlVideoExecucao,
        boolean aulaRealizada,
        boolean exerciciosFinalizados
) {
}
