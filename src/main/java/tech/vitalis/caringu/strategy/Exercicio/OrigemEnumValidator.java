package tech.vitalis.caringu.strategy.Exercicio;

import tech.vitalis.caringu.enums.Exercicio.OrigemEnum;
import tech.vitalis.caringu.enums.TreinoExercicio.GrauDificuldadeEnum;
import tech.vitalis.caringu.exception.ApiExceptions;
import tech.vitalis.caringu.strategy.EnumValidationStrategy;

public class OrigemEnumValidator implements EnumValidationStrategy {
    @Override
    public void validar(String valorEnum) {

        try {
            OrigemEnum.valueOf(valorEnum);
        } catch (IllegalArgumentException e) {
            throw new ApiExceptions.BadRequestException("Valor inválido para 'Origem Exercicio'. Valores válidos: BIBLIOTECA, PERSONAL");
        }

    }
}
