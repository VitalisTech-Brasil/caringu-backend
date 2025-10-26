package tech.vitalis.caringu.strategy.PlanoContratado;

import tech.vitalis.caringu.core.domain.valueObject.StatusEnum;
import tech.vitalis.caringu.exception.ApiExceptions;
import tech.vitalis.caringu.strategy.EnumValidationStrategy;

public class StatusEnumValidationStrategy implements EnumValidationStrategy {

    @Override
    public void validar(String valorEnum) {

        try {
            StatusEnum.valueOf(valorEnum);
        } catch (IllegalArgumentException e) {
            throw new ApiExceptions.BadRequestException("Valor inválido para 'Status'. Valores válidos: ATIVO, PENDENTE, INATIVO, EM_PROCESSO, CANCELADO");
        }

    }
}
