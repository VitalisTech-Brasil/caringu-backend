package tech.vitalis.caringu.dtos.Notificacoes;

import java.time.LocalDate;

public record NotificacaoTreinoPersonalDTO(
        Integer personalTrainerId,
        String cref,
        Integer alunoId,
        LocalDate dataVencimento
) {
}
