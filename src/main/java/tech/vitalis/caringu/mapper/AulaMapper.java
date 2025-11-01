package tech.vitalis.caringu.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tech.vitalis.caringu.dtos.Aula.Request.AulaRascunhoItemDTO;
import tech.vitalis.caringu.dtos.Aula.Response.AulaRascunhoCriadaDTO;
import tech.vitalis.caringu.dtos.SessaoTreino.SessaoAulasAgendadasResponseDTO;
import tech.vitalis.caringu.entity.Aula;
import tech.vitalis.caringu.infrastructure.persistence.planoContratado.PlanoContratadoEntity;
import tech.vitalis.caringu.enums.Aula.AulaStatusEnum;
import tech.vitalis.caringu.service.ArmazenamentoFotos.ArmazenamentoService;

import java.time.Duration;

@Component
public class AulaMapper {

    @Autowired
    private ArmazenamentoService armazenamentoInterface;

    public Aula toEntity(AulaRascunhoItemDTO dto, PlanoContratadoEntity plano) {
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

    public SessaoAulasAgendadasResponseDTO toSessaoAulasAgendadasResponseDTOComUrlPreAssinada(
            SessaoAulasAgendadasResponseDTO responseDTO
    ) {
        String urlFotoAluno = responseDTO.urlFotoPerfilAluno() != null
                ? armazenamentoInterface.gerarUrlPreAssinada(responseDTO.urlFotoPerfilAluno(), Duration.ofMinutes(5))
                : null;

        String urlFotoPerfilPersonal = responseDTO.urlFotoPerfilPersonal() != null
                ? armazenamentoInterface.gerarUrlPreAssinada(responseDTO.urlFotoPerfilPersonal(), Duration.ofMinutes(5))
                : null;

        return new SessaoAulasAgendadasResponseDTO(
                responseDTO.idAluno(),
                responseDTO.nomeAluno(),
                urlFotoAluno,
                responseDTO.idPersonal(),
                responseDTO.nomePersonal(),
                urlFotoPerfilPersonal,
                responseDTO.idAula(),
                responseDTO.dataHorarioInicio(),
                responseDTO.dataHorarioFim(),
                responseDTO.status()
        );
    }
}
