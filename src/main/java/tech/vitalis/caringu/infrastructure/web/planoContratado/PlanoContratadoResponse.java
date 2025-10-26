package tech.vitalis.caringu.infrastructure.web.planoContratado;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import tech.vitalis.caringu.core.domain.valueObject.StatusEnum;
import tech.vitalis.caringu.dtos.Aluno.AlunoResponseGetDTO;
import tech.vitalis.caringu.dtos.Plano.PlanoRespostaRecord;

import java.time.LocalDate;

public record PlanoContratadoResponse (
        Integer id,
        @NotNull(message = "Aluno não pode ser nulo")
        AlunoResponseGetDTO aluno,

        @NotNull(message = "Plano não pode ser nulo")
        PlanoRespostaRecord plano,

        @Enumerated(EnumType.STRING)
        StatusEnum statusEnum,

        @FutureOrPresent(message = "Data de contratação nao pode estar no passado.")
        LocalDate dataContratacao,
        LocalDate dataFim
){
}
