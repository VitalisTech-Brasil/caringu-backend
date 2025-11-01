package tech.vitalis.caringu.infrastructure.web.planoContratado;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import tech.vitalis.caringu.core.domain.valueObject.StatusEnum;

import java.time.LocalDate;

public record PostPlanoContratadoRequest (
        @NotNull(message = "Aluno não pode ser nulo")
        Integer aluno,

        @NotNull(message = "Plano não pode ser nulo")
        Integer plano,

        @Enumerated(EnumType.STRING)
        StatusEnum status,

        @FutureOrPresent(message = "Data de contratação nao pode estar no passado.")
        LocalDate dataContratacao,

        LocalDate dataFim
){
}
