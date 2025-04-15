package tech.vitalis.caringu.strategy.Aluno;

import tech.vitalis.caringu.enums.Aluno.NivelExperienciaEnum;
import tech.vitalis.caringu.exception.ApiExceptions;
import tech.vitalis.caringu.strategy.EnumValidationStrategy;

public class NivelExperienciaEnumValidationStrategy implements EnumValidationStrategy {

    @Override
    public void validar(String valorEnum) {

        try {
            NivelExperienciaEnum.valueOf(valorEnum);
        } catch (IllegalArgumentException e) {
            throw new ApiExceptions.BadRequestException("Valor inválido para 'NivelExperiencia'. Valores válidos: INICIANTE, INTERMEDIARIO, AVANCADO");
        }

    }
}
