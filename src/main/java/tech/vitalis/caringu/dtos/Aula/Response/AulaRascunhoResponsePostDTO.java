package tech.vitalis.caringu.dtos.Aula.Response;

import java.util.List;

public record AulaRascunhoResponsePostDTO(
        List<AulaRascunhoCriadaDTO> aulasCriadas
) {
}