package tech.vitalis.caringu.dtos.TreinoFinalizado;

import java.util.List;

public record HorasTreinadasResponseDTO(
        Integer idAluno,
        Integer idExercicio,
        List<HorasTreinadasSemanaMesDTO> dados
) {
}
