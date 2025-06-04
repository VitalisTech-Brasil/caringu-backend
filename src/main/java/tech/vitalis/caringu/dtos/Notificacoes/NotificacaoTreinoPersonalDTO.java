package tech.vitalis.caringu.dtos.Notificacoes;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record NotificacaoTreinoPersonalDTO(
        Integer personalTrainerId,
        String nomePersonal,
        Integer alunoId,
        String alunoNome,
        String nomeTreino,
        String titulo,
        LocalDate dataVencimento,
        LocalDateTime dataCriacao
) {
}
