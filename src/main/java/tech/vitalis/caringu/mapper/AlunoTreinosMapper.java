package tech.vitalis.caringu.mapper;

import org.springframework.stereotype.Component;
import tech.vitalis.caringu.dtos.AlunoPlanos.AlunoPlanosDTO;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;

@Component
public class AlunoTreinosMapper {

    public AlunoPlanosDTO toAlunoPlanosDTO(Object[] resultado) {
        AlunoPlanosDTO dto = new AlunoPlanosDTO();

        dto.setPlanoContratadoId(resultado[0] != null ? ((Number) resultado[0]).longValue() : null);
        dto.setStatus(resultado[1] != null ? resultado[1].toString() : null);
        dto.setDataFim(resultado[2] != null ? convertToLocalDate(resultado[2]) : null);

        AlunoPlanosDTO.PlanoInfo planoInfo = new AlunoPlanosDTO.PlanoInfo(
                resultado[3] != null ? resultado[3].toString() : null,
                resultado[4] != null ? ((Number) resultado[4]).intValue() : null,
                resultado[5] != null ? convertToDouble(resultado[5]) : null
        );
        dto.setPlano(planoInfo);

        AlunoPlanosDTO.PersonalInfo personalInfo = new AlunoPlanosDTO.PersonalInfo(
                resultado[6] != null ? ((Number) resultado[6]).intValue() : null,
                resultado[7] != null ? resultado[7].toString() : null,
                resultado[8] != null ? resultado[8].toString() : null,
                resultado[9] != null ? resultado[9].toString() : null
        );
        dto.setPersonalTrainer(personalInfo);

        dto.setLocaisAtendimento(resultado[10] != null ? resultado[10].toString() : "Não informado");

        if (resultado[11] != null) {
            AlunoPlanosDTO.AvaliacaoInfo avaliacaoInfo = new AlunoPlanosDTO.AvaliacaoInfo(
                    ((Number) resultado[11]).intValue(),
                    resultado[12] != null ? convertToDouble(resultado[12]) : null,
                    resultado[13] != null ? resultado[13].toString() : null,
                    resultado[14] != null ? resultado[14].toString() : null
            );
            dto.setAvaliacao(avaliacaoInfo);
        } else {
            dto.setAvaliacao(null);
        }

        return dto;
    }

    private LocalDate convertToLocalDate(Object obj) {
        if (obj instanceof Date) {
            return ((Date) obj).toLocalDate();
        } else if (obj instanceof java.util.Date) {
            return new Date(((java.util.Date) obj).getTime()).toLocalDate();
        } else if (obj instanceof LocalDate) {
            return (LocalDate) obj;
        }
        return null;
    }

    private Double convertToDouble(Object obj) {
        if (obj instanceof BigDecimal) {
            return ((BigDecimal) obj).doubleValue();
        } else if (obj instanceof Number) {
            return ((Number) obj).doubleValue();
        }
        return null;
    }
}
