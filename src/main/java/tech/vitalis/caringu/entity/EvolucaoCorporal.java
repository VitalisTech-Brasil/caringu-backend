package tech.vitalis.caringu.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import tech.vitalis.caringu.enums.TipoEvolucaoEnum;

import java.time.LocalDateTime;

@Entity
@Table(name = "evolucao_corporal")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class EvolucaoCorporal {

    @Id
    private Integer id;
    @NotNull
    @Enumerated(EnumType.STRING)
    private TipoEvolucaoEnum tipo;
    private String urlFotoShape;
    @NotNull
    private LocalDateTime dataEnvio;
    @Positive
    @NotNull
    private Integer periodoAvaliacao;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "alunos_id")
    private Aluno alunosId;
}
