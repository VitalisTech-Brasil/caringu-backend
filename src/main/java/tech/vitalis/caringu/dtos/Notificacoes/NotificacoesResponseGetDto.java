package tech.vitalis.caringu.dtos.Notificacoes;

import tech.vitalis.caringu.dtos.Pessoa.PessoaResponseGetDTO;
import tech.vitalis.caringu.enums.Notificacoes.TipoNotificacaoEnum;

import java.time.LocalDateTime;

public record NotificacoesResponseGetDto(
        Integer id,
        PessoaResponseGetDTO pessoaId,
        TipoNotificacaoEnum tipo,
        String titulo,
        Boolean visualizada,
        LocalDateTime dataCriacao
) {
}
