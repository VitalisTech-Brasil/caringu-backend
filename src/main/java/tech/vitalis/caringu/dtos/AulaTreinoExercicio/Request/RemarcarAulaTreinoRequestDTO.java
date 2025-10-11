package tech.vitalis.caringu.dtos.AulaTreinoExercicio.Request;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public record RemarcarAulaTreinoRequestDTO(
        @NotNull(message = "O id da aula é obrigatório.")
        @Positive(message = "O id da aula deve ser positivo.")
        Integer idAula,

        @NotNull(message = "O id do novo treino é obrigatório.")
        @Positive(message = "O id do novo treino deve ser positivo.")
        Integer idTreinoNovo,

        @NotNull(message = "O novo horário de início é obrigatório")
        @Future(message = "O horário de início deve estar no futuro")
        LocalDateTime novoHorarioInicio,

        @NotNull(message = "O novo horário de fim é obrigatório")
        @Future(message = "O horário de fim deve estar no futuro")
        LocalDateTime novoHorarioFim
) {
    @AssertTrue(message = "Data/hora de fim deve ser após a data/hora de início.")
    public boolean isDataFimDepoisDeInicio() {
        if (novoHorarioInicio == null || novoHorarioFim == null) {
            return true;
        }
        return novoHorarioFim.isAfter(novoHorarioInicio);
    }
}


