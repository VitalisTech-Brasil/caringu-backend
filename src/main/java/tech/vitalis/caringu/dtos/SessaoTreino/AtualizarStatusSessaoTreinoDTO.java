package tech.vitalis.caringu.dtos.SessaoTreino;

import tech.vitalis.caringu.enums.Aula.AulaStatusEnum;

public record AtualizarStatusSessaoTreinoDTO(
        AulaStatusEnum status
) {
}
