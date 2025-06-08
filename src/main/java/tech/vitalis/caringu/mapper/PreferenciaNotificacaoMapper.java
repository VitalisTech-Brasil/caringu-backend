package tech.vitalis.caringu.mapper;

import org.springframework.stereotype.Component;
import tech.vitalis.caringu.dtos.PreferenciaNotificacao.PreferenciaNotificacaoResponseGetDTO;
import tech.vitalis.caringu.entity.PreferenciaNotificacao;

@Component
public class PreferenciaNotificacaoMapper {

    public PreferenciaNotificacaoResponseGetDTO toResponseDTO(PreferenciaNotificacao entity) {
        return new PreferenciaNotificacaoResponseGetDTO(
                entity.getId(),
                entity.getTipo(),
                entity.isAtivada());
    }
}
