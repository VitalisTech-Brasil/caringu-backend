package tech.vitalis.caringu.dtos.PreferenciaNotificacao;

import jakarta.validation.constraints.NotNull;
import tech.vitalis.caringu.enums.PreferenciaNotificacao.TipoPreferenciaEnum;

public record PreferenciaNotificacaoRequestPutDTO(

        @NotNull(message = "O campo 'tipo' é obrigatório.")
        TipoPreferenciaEnum tipo,

        @NotNull(message = "O campo 'ativada' é obrigatório.")
        boolean ativada
) {
}
