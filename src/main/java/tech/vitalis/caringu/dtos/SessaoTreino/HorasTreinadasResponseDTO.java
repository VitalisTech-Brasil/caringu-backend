package tech.vitalis.caringu.dtos.SessaoTreino;

import tech.vitalis.caringu.dtos.SessaoTreino.HorasTreinadasSemanaMesDTO;

import java.util.List;

public record HorasTreinadasResponseDTO(
        Integer idAluno,
        Integer idExercicio,
        List<HorasTreinadasSemanaMesDTO> dados
) {
}
