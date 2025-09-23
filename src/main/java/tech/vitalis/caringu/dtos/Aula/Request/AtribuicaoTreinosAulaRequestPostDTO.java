package tech.vitalis.caringu.dtos.Aula.Request;

import java.util.List;

public record AtribuicaoTreinosAulaRequestPostDTO(
        List<AtribuicaoTreinosAulaItemDTO> aulasTreinos
) {
}
