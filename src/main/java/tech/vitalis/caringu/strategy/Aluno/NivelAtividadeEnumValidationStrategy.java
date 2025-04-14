package tech.vitalis.caringu.strategy.Aluno;

import tech.vitalis.caringu.enums.Aluno.NivelAtividadeEnum;
import tech.vitalis.caringu.exception.ApiExceptions;
import tech.vitalis.caringu.strategy.EnumValidationStrategy;

public class NivelAtividadeEnumValidationStrategy implements EnumValidationStrategy {

    @Override
    public void validar(String valorEnum) {
        if (valorEnum == null) return;

        try {
            NivelAtividadeEnum.valueOf(valorEnum);
        } catch (IllegalArgumentException e) {
            throw new ApiExceptions.BadRequestException("Valor inválido para 'NivelAtividade'. Valores válidos: SEDENTARIO, LEVEMENTE_ATIVO, MODERADAMENTE_ATIVO, MUITO_ATIVO, EXTREMAMENTE_ATIVO");
        }

    }
}
