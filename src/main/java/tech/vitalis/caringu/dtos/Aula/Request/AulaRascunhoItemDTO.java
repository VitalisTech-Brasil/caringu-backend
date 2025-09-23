package tech.vitalis.caringu.dtos.Aula.Request;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record AulaRascunhoItemDTO(

        @NotNull(message = "Data e horário de início são obrigatórios")
        @FutureOrPresent(message = "A data/hora de início deve ser no presente ou futuro")
        LocalDateTime dataHorarioInicio,

        @NotNull(message = "Data e horário de fim são obrigatórios")
        @FutureOrPresent(message = "A data/hora de fim deve ser no presente ou futuro")
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
