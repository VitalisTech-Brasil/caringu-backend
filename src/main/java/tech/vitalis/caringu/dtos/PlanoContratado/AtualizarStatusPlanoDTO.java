package tech.vitalis.caringu.dtos.PlanoContratado;

import tech.vitalis.caringu.enums.StatusEnum;

public record AtualizarStatusPlanoDTO(
        StatusEnum status
) {
}