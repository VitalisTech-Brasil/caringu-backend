package tech.vitalis.caringu.dtos.Aula.Request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record AulaRascunhoItemDTO(

        @NotNull(message = "Data e horário de início são obrigatórios")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime dataHorarioInicio,

        @NotNull(message = "Data e horário de fim são obrigatórios")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime dataHorarioFim
) {
    @AssertTrue(message = "Data/hora de fim deve ser após a data/hora de início")
    public boolean isDataFimDepoisDeInicio() {
        if (dataHorarioInicio == null || dataHorarioFim == null) {
            return true; // deixa o @NotNull cuidar
        }
        return dataHorarioFim.isAfter(dataHorarioInicio);
    }
}
