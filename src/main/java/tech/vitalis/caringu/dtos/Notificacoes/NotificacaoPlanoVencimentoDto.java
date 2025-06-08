package tech.vitalis.caringu.dtos.Notificacoes;

import tech.vitalis.caringu.enums.StatusEnum;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record NotificacaoPlanoVencimentoDto(
        Integer personalTrainerId,
        String nomePersonal,
        Integer alunoId,
        String alunoNome,
        Integer idPlano,
        String nomePlano,
        Integer idPlanoContratado,
        StatusEnum status,
        LocalDate dataContratacao,
        LocalDate dataFim,
        Boolean visualizada
) {}

