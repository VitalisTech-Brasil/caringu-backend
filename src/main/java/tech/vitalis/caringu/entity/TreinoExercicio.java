package tech.vitalis.caringu.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import tech.vitalis.caringu.enums.NivelTreinoExercicioEnum;

import java.time.LocalDateTime;

@Entity
@Table(name = "treinos_exercicios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TreinoExercicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Positive
    @NotNull
    private Integer treinosId;
    @Positive
    @NotNull
    private Integer exercicioId;
    @Positive
    @NotNull
    private Double carga;
    @Positive
    @NotNull
    private Integer repeticoes;
    @Positive
    @NotNull
    private Integer series;
    @Positive
    @NotNull
    private Integer descanso;
    @NotNull
    private LocalDateTime dataHoraCriacao;
    private LocalDateTime dataHoraModificacao;
    private boolean favorito;
    @NotNull
    @Enumerated(EnumType.STRING)
    private NivelTreinoExercicioEnum nivel;
}
