package tech.vitalis.caringu.strategy.SessaoTreino;

import tech.vitalis.caringu.enums.Aula.AulaStatusEnum;
import tech.vitalis.caringu.exception.ApiExceptions;
import tech.vitalis.caringu.strategy.EnumValidationStrategy;

public class StatusSessaoTreinoValidationStrategy implements EnumValidationStrategy {

    @Override
    public void validar(String valorEnum) {

        try {
            AulaStatusEnum.valueOf(valorEnum);
        } catch (IllegalArgumentException e) {
            throw new ApiExceptions.BadRequestException("Valor inválido para 'Status' do Sessão Treino. " +
                    "Valores válidos: AGENDADO, REALIZADO, CANCELADO, REAGENDADO");
        }
    }
}
