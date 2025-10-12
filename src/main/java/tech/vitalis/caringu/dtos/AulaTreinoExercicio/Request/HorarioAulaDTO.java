package tech.vitalis.caringu.dtos.AulaTreinoExercicio.Request;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record HorarioAulaDTO(

        @NotNull(message = "Data e horário de início são obrigatórios.")
        LocalDateTime dataHorarioInicio,

        @NotNull(message = "Data e horário de fim são obrigatórios.")
        LocalDateTime dataHorarioFim
) {
    @AssertTrue(message = "Data/hora de fim deve ser após a data/hora de início.")
    public boolean isDataFimDepoisDeInicio() {
        if (dataHorarioInicio == null || dataHorarioFim == null) {
            return true;
        }
        return dataHorarioFim.isAfter(dataHorarioInicio);
    }
}