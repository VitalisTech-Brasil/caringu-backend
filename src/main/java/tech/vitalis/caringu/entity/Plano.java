package tech.vitalis.caringu.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import tech.vitalis.caringu.enums.PeriodoEnum;
import tech.vitalis.caringu.enums.StatusEnum;
import tech.vitalis.caringu.id.PlanosId;

import java.util.Date;

@Entity
@Table(name = "planos")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Plano {

    @EmbeddedId
    private PlanosId id;
    @NotBlank
    private String nome;
    @NotNull
    @Enumerated(EnumType.STRING)
    private StatusEnum status; // Coloca como String na DTO e valida se ta certo ou não fica melhor.
    @NotNull
    @Enumerated(EnumType.STRING)
    private PeriodoEnum periodo;
    @Positive
    @NotNull
    private Integer quantidadeAulas;
    @Positive
    @NotNull
    private Double valorAulas;
    @FutureOrPresent
    private Date dataInicio;
    @Future
    private Date dataFim;


}
