package tech.vitalis.caringu.dtos.Aula.Request;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public record AtribuicaoTreinosAulaItemDTO(

        @NotNull(message = "O id do aluno é obrigatório")
        @Positive(message = "O id do aluno deve ser positivo")
        Integer idAluno,

        @NotNull(message = "O id do treino é obrigatório")
        @Positive(message = "O id do treino deve ser positivo")
        Integer idTreinoExercicio,

        @NotNull(message = "O id do plano contratado é obrigatório")
        @Positive(message = "O id do plano contratado deve ser positivo")
        Integer idPlanoContratado,

        @NotNull(message = "A data/hora de início é obrigatória")
        LocalDateTime dataHorarioInicio,

        @NotNull(message = "A data/hora de fim é obrigatória")
        LocalDateTime dataHorarioFim
) {
    @AssertTrue(message = "A data/hora de fim deve ser após a data/hora de início")
    public boolean isDataFimDepoisDeInicio() {
        if (dataHorarioInicio == null || dataHorarioFim == null) {
            return true; // deixa os @NotNull cuidarem disso
        }
        return dataHorarioFim.isAfter(dataHorarioInicio);
    }
}