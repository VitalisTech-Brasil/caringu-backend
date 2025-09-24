package tech.vitalis.caringu.dtos.AulaTreinoExercicio.Response;

import java.util.List;

public record AtribuicaoTreinosAulaTreinoResponseDTO(
        Integer idAluno,
        Integer idPlanoContratado,
        Integer idTreino,
        List<AulaCriadaDTO> aulasCriadas
) {}