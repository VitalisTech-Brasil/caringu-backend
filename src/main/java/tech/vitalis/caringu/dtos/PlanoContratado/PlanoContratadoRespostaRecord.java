package tech.vitalis.caringu.dtos.PlanoContratado;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import tech.vitalis.caringu.entity.Aluno;
import tech.vitalis.caringu.entity.Plano;
import tech.vitalis.caringu.core.domain.valueObject.StatusEnum;
import java.time.LocalDate;

public record PlanoContratadoRespostaRecord(
        @NotNull(message = "Aluno não pode ser nulo")
        Aluno aluno,

        @NotNull(message = "Plano não pode ser nulo")
        Plano plano,

        @Enumerated(EnumType.STRING)
        StatusEnum status,

        @FutureOrPresent(message = "Data de contratação nao pode estar no passado.")
        LocalDate dataContratacao,

        LocalDate dataFim
) {
}
