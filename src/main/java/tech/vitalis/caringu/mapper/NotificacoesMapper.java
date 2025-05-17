package tech.vitalis.caringu.mapper;

import org.springframework.stereotype.Component;
import tech.vitalis.caringu.dtos.Notificacoes.NotificacoesRequestPostDto;
import tech.vitalis.caringu.dtos.Notificacoes.NotificacoesResponseGetDto;
import tech.vitalis.caringu.entity.Notificacoes;

@Component
public class NotificacoesMapper {

    private final PessoaMapper pessoaMapper;

    public NotificacoesMapper(PessoaMapper pessoaMapper) {
        this.pessoaMapper = pessoaMapper;
    }

    public Notificacoes toEntity(NotificacoesRequestPostDto dto){
        if (dto == null) return null;

        Notificacoes notificacoes = new Notificacoes();
        notificacoes.setTipo(dto.tipo());
        notificacoes.setTitulo(dto.titulo());
        notificacoes.setVisualizada(dto.visualizada());
        notificacoes.setDataCriacao(dto.dataCriacao());

        return notificacoes;
    }

    public NotificacoesResponseGetDto toResponseDTO(Notificacoes notificacoes){
        if (notificacoes == null) return null;

        return new NotificacoesResponseGetDto(
          notificacoes.getId(),
          pessoaMapper.toDTO(notificacoes.getPessoa()),
          notificacoes.getTipo(),
          notificacoes.getTitulo(),
          notificacoes.isVisualizada(),
          notificacoes.getDataCriacao()
        );
    }
}
