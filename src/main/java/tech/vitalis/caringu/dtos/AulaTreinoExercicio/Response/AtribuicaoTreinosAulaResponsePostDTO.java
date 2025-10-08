package tech.vitalis.caringu.dtos.AulaTreinoExercicio.Response;

import java.util.List;

public record AtribuicaoTreinosAulaResponsePostDTO(
        List<AtribuicaoTreinosAulaTreinoResponseDTO> aulasTreinos
) {}