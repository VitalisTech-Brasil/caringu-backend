package tech.vitalis.caringu.infrastructure.web.planoContratado;

import jakarta.validation.constraints.NotNull;
import tech.vitalis.caringu.core.domain.valueObject.StatusEnum;

public record PatchAtualizarStatusRequest(
        @NotNull
        StatusEnum statusEnum
) {
}
