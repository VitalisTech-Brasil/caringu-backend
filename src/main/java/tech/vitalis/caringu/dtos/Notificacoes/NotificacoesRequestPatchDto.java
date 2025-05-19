package tech.vitalis.caringu.dtos.Notificacoes;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import tech.vitalis.caringu.enums.Notificacoes.TipoNotificacaoEnum;

import java.time.LocalDateTime;

public record NotificacoesRequestPatchDto (
        @NotNull
        TipoNotificacaoEnum tipo,
        @NotBlank
        String titulo,
        @NotNull
        boolean visualizada,
        @FutureOrPresent
        LocalDateTime dataCriacao
) {
}
