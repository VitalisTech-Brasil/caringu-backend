package tech.vitalis.caringu.dtos.SessaoTreino;

import tech.vitalis.caringu.enums.SessaoTreino.StatusSessaoTreinoEnum;

public record AtualizarStatusSessaoTreinoDTO(
        StatusSessaoTreinoEnum status
) {
}
