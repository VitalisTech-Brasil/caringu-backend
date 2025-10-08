package tech.vitalis.caringu.dtos.Notificacoes;

import tech.vitalis.caringu.entity.Aluno;
import tech.vitalis.caringu.entity.PersonalTrainer;
import tech.vitalis.caringu.entity.PlanoContratado;
import tech.vitalis.caringu.enums.StatusEnum;

import java.time.LocalDate;

public record NotificacaoPlanoVencimentoDto(
        PersonalTrainer personalTrainer,
        String nomePersonal,
        Aluno alunoId,
        String alunoNome,
        Integer idPlano,
        String nomePlano,
        PlanoContratado idPlanoContratado,
        StatusEnum status,
        LocalDate dataContratacao,
        LocalDate dataFim,
        Boolean visualizada
) {}

