package tech.vitalis.caringu.strategy.Plano;

import tech.vitalis.caringu.enums.Pessoa.GeneroEnum;
import tech.vitalis.caringu.exception.ApiExceptions;
import tech.vitalis.caringu.strategy.EnumValidationStrategy;

public class PeriodoEnumValidationStrategy implements EnumValidationStrategy {
    @Override
    public void validar(String valorEnum) {
        try {
            GeneroEnum.valueOf(valorEnum);
        } catch (IllegalArgumentException e) {
            throw new ApiExceptions.BadRequestException("Valor inválido para 'Período do plano'. Valores válidos: AVULSO, MENSAL, SEMESTRAL");
        }
    }
}
