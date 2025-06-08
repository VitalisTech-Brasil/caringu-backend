package tech.vitalis.caringu.strategy.TreinoExercio;

import tech.vitalis.caringu.enums.TreinoExercicio.GrauDificuldadeEnum;
import tech.vitalis.caringu.enums.TreinoExercicio.OrigemTreinoExercicioEnum;
import tech.vitalis.caringu.exception.ApiExceptions;
import tech.vitalis.caringu.strategy.EnumValidationStrategy;

public class GrauDificuldadeEnumValidator implements EnumValidationStrategy {
    @Override
    public void validar(String valorEnum) {

        try {
            GrauDificuldadeEnum.valueOf(valorEnum);
        } catch (IllegalArgumentException e) {
            throw new ApiExceptions.BadRequestException("Valor inválido para 'GrauDificuldadeEnum'. Valores válidos: INICIANTE, INTERMEDIARIO, AVANCADO");
        }

    }
}
