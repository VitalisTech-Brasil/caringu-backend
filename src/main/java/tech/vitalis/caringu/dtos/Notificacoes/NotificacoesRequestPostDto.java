package tech.vitalis.caringu.dtos.Notificacoes;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import tech.vitalis.caringu.enums.Notificacoes.TipoNotificacaoEnum;

import java.time.LocalDateTime;

public record NotificacoesRequestPostDto(
        Integer pessoaId,
        @NotNull(message = "O campo 'tipo' não pode estar nulo.")
        TipoNotificacaoEnum tipo,
        @NotBlank(message = "O campo 'titulo' não pode estar nulo.")
        String titulo,
        @NotNull(message = "O campo 'visualizada' não pode estar nulo.")
        boolean visualizada,
        @FutureOrPresent
        LocalDateTime dataCriacao
) {
}
