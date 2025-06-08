package tech.vitalis.caringu.strategy.Notificacoes;

import tech.vitalis.caringu.enums.Notificacoes.TipoNotificacaoEnum;
import tech.vitalis.caringu.enums.TreinoExercicio.GrauDificuldadeEnum;
import tech.vitalis.caringu.exception.ApiExceptions;
import tech.vitalis.caringu.strategy.EnumValidationStrategy;

public class NotificacoesEnumValidator implements EnumValidationStrategy {
    @Override
    public void validar(String valorEnum) {

        try {
            TipoNotificacaoEnum.valueOf(valorEnum);
        } catch (IllegalArgumentException e) {
            throw new ApiExceptions.BadRequestException("Valor inválido para 'TipoNotificacaoEnum'. Valores válidos: FEEDBACK_TREINO, PAGAMENTO_REALIZADO, PLANO_PROXIMO_VENCIMENTO, NOVA_FOTO_PROGRESSO, TREINO_PROXIMO_VENCIMENTO");
        }

    }
}
