package tech.vitalis.caringu.dtos.PreferenciaNotificacao;

import tech.vitalis.caringu.enums.PreferenciaNotificacao.TipoPreferenciaEnum;

public record PreferenciaNotificacaoResponseGetDTO(
        TipoPreferenciaEnum tipo,
        boolean ativada
) {
}
