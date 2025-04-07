package tech.vitalis.caringu.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import tech.vitalis.caringu.enums.GrupoMuscularEnum;

@Entity
@Table(name = "treinos")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Treino {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank
    private String nome;
    @NotNull
    @Enumerated(EnumType.STRING)
    private GrupoMuscularEnum grupoMuscular;
    @NotBlank
    private String descricao;
    @Positive
    @NotNull
    private Integer personalId;

}
