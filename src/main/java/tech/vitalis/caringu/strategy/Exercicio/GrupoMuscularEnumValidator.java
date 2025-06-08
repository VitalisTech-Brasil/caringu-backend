package tech.vitalis.caringu.strategy.Exercicio;

import tech.vitalis.caringu.enums.Exercicio.GrupoMuscularEnum;
import tech.vitalis.caringu.enums.Exercicio.OrigemEnum;
import tech.vitalis.caringu.exception.ApiExceptions;
import tech.vitalis.caringu.strategy.EnumValidationStrategy;

public class GrupoMuscularEnumValidator implements EnumValidationStrategy {
    @Override
    public void validar(String valorEnum) {

        try {
            GrupoMuscularEnum.valueOf(valorEnum);
        } catch (IllegalArgumentException e) {
            throw new ApiExceptions.BadRequestException("Valor inválido para 'Grupo Muscular'. Valores válidos: PEITORAL, COSTAS, PERNAS, OMBRO, BRACO, CORE, CARDIO");
        }

    }
}
