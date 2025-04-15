package tech.vitalis.caringu.strategy;

import java.util.Map;

public class EnumValidador {

    public static void validarEnums(Map<EnumValidationStrategy, Enum<?>> validacoes) {
        EnumValidationContext contexto = new EnumValidationContext();

//      Aqui é um loop aprimorado (ou for each) de pares chave-valor
//      o 'validacoes.entrySet()', retorna o conjunto dos pares chave-valor
        for ( Map.Entry<EnumValidationStrategy, Enum<?> > entrada : validacoes.entrySet()) {

            contexto.setStrategy(entrada.getKey());
            contexto.validar(entrada.getValue().name());

        }
    }
}
