package tech.vitalis.caringu.dtos.Plano;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import tech.vitalis.caringu.enums.PeriodoEnum;

public record PlanoRespostaRecord(
        @NotBlank(message = "Nome do plano é obrigatório")
        String nome,

        @NotNull(message = "Período não pode ser nulo")
        @Enumerated(EnumType.STRING)
        PeriodoEnum periodo,

        @Positive
        @NotNull
        Integer quantidadeAulas,

        @Positive
        @NotNull
        Double valorAulas
) {
    public Double getValorPlano() {
        return quantidadeAulas * valorAulas;
    }
}
