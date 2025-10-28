package tech.vitalis.caringu.core.domain.entity;

import tech.vitalis.caringu.core.domain.valueObject.StatusEnum;
import tech.vitalis.caringu.entity.Aluno;
import tech.vitalis.caringu.entity.Plano;
import tech.vitalis.caringu.enums.PeriodoEnum;

import java.time.LocalDate;

public record PlanoContratado (
        Integer id,
        Aluno aluno,
        Plano plano,
        StatusEnum status,
        LocalDate dataContratacao,
        LocalDate dataFim
){
    public static PlanoContratado novo(Aluno aluno, Plano plano){
            return new PlanoContratado(
                    null,
                    aluno,
                    plano,
                    StatusEnum.PENDENTE,
                    null,
                    null
            );
    }

    public PlanoContratado ativar(){
        LocalDate hoje = LocalDate.now();
        LocalDate dataFim = calcularDataFim(plano.getPeriodo(), hoje);

        return new PlanoContratado(
                this.id,
                this.aluno,
                this.plano,
                StatusEnum.ATIVO,
                hoje,
                dataFim
        );
    }

    public PlanoContratado emProcesso(){
        return new PlanoContratado(
                this.id,
                this.aluno,
                this.plano,
                StatusEnum.EM_PROCESSO,
                this.dataContratacao,
                this.dataFim
        );
    }

    public PlanoContratado cancelar(){
        return new PlanoContratado(
                this.id,
                this.aluno,
                this.plano,
                StatusEnum.CANCELADO,
                this.dataContratacao,
                LocalDate.now()
        );
    }

    private static LocalDate calcularDataFim(PeriodoEnum periodoEnum, LocalDate dataInicio){
        return switch (periodoEnum){
            case AVULSO -> null;
            case MENSAL -> dataInicio.plusMonths(1);
            case SEMESTRAL -> dataInicio.plusMonths(6);
        };
    }

    public boolean proximoVencimento(LocalDate dataLimite){
        if (dataFim == null) return false;
        return dataFim.isBefore(dataLimite) || dataFim.isEqual(dataLimite);
    }

}
