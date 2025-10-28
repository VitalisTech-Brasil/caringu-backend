package tech.vitalis.caringu.dtos.PlanoContratado;

import tech.vitalis.caringu.core.domain.valueObject.StatusEnum;

public record AtualizarStatusPlanoDTO(
        StatusEnum status
) {
}