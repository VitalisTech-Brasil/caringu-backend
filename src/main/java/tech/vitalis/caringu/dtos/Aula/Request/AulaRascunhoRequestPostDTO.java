package tech.vitalis.caringu.dtos.Aula.Request;

import java.util.List;

public record AulaRascunhoRequestPostDTO(
        List<AulaRascunhoItemDTO> aulas
) {
}
