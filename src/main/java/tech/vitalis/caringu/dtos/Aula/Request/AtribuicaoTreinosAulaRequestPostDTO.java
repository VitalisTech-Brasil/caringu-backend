package tech.vitalis.caringu.dtos.Aula.Request;

import jakarta.validation.Valid;

import java.util.List;

public record AtribuicaoTreinosAulaRequestPostDTO(
        List<@Valid AtribuicaoTreinosAulaItemDTO> aulasTreinos
) {
}
