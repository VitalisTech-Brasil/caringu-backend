package tech.vitalis.caringu.mapper;

import org.springframework.stereotype.Component;
import tech.vitalis.caringu.dtos.Aula.Request.AulaRascunhoItemDTO;
import tech.vitalis.caringu.dtos.Aula.Response.AulaRascunhoCriadaDTO;
import tech.vitalis.caringu.entity.Aula;
import tech.vitalis.caringu.entity.PlanoContratado;
import tech.vitalis.caringu.enums.Aula.AulaStatusEnum;

@Component
public class AulaMapper {

    public Aula toEntity(AulaRascunhoItemDTO dto, PlanoContratado plano) {
        Aula aula = new Aula();
        aula.setPlanoContratado(plano);
        aula.setDataHorarioInicio(dto.dataHorarioInicio());
        aula.setDataHorarioFim(dto.dataHorarioFim());
        aula.setStatus(AulaStatusEnum.RASCUNHO);
        return aula;
    }

    public AulaRascunhoCriadaDTO toResponse(Aula aula) {
        return new AulaRascunhoCriadaDTO(
                aula.getId(),
                aula.getDataHorarioInicio(),
                aula.getDataHorarioFim(),
                aula.getStatus().name()
        );
    }
}
