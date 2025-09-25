package tech.vitalis.caringu.dtos.AulaTreinoExercicio.Request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record AtribuicaoTreinosAulaRequestPostDTO(
        @NotEmpty(message = "É necessário informar pelo menos uma atribuição de treino.")
        List<@Valid AtribuicaoTreinosAulaTreinoDTO> aulasTreinos
) {
}
