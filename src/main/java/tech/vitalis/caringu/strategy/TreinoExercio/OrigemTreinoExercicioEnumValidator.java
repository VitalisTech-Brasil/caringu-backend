package tech.vitalis.caringu.strategy.TreinoExercio;

import tech.vitalis.caringu.enums.TreinoExercicio.OrigemTreinoExercicioEnum;
import tech.vitalis.caringu.exception.ApiExceptions;
import tech.vitalis.caringu.strategy.EnumValidationStrategy;

public class OrigemTreinoExercicioEnumValidator implements EnumValidationStrategy {
    @Override
    public void validar(String valorEnum) {

        try {
            OrigemTreinoExercicioEnum.valueOf(valorEnum);
        } catch (IllegalArgumentException e) {
            throw new ApiExceptions.BadRequestException("Valor inválido para 'OrigemTreinoExercicio'. Valores válidos: BIBLIOTECA, PERSONAL");
        }

    }
}
