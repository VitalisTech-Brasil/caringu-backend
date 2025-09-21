package tech.vitalis.caringu.dtos.Aula.Response;

import java.util.List;

public record AulaRascunhoResponsePostDTO(
        boolean sucesso,
        List<AulaRascunhoCriadaDTO> aulasCriadas
) {
}