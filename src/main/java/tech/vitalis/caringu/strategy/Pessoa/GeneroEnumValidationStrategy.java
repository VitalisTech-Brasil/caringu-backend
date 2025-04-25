package tech.vitalis.caringu.strategy.Pessoa;

import tech.vitalis.caringu.enums.Pessoa.GeneroEnum;
import tech.vitalis.caringu.exception.ApiExceptions;
import tech.vitalis.caringu.strategy.EnumValidationStrategy;

public class GeneroEnumValidationStrategy implements EnumValidationStrategy {
    @Override
    public void validar(String valorEnum) {

        try {
            GeneroEnum.valueOf(valorEnum);
        } catch (IllegalArgumentException e) {
            throw new ApiExceptions.BadRequestException("Valor inválido para 'Genero'. Valores válidos: HOMEM_CISGENERO, HOMEM_TRANSGENERO, MULHER_CISGENERO, MULHER_TRANSGENERO, NAO_BINARIO");
        }

    }
}
