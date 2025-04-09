package tech.vitalis.caringu.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import tech.vitalis.caringu.enums.TreinoSemanaEnum;

@Entity
@Table(name = "treino")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Anamnese {

    @Id
    private Integer id;
    @OneToOne
    @JoinColumn(name = "alunos_id", nullable = false)
    private Aluno alunos;
    @NotBlank
    private String objetivoTreino;
    @NotNull
    private boolean lesao;
    private String lesaoDescricao;
    @NotNull
    @Enumerated(EnumType.STRING)
    private TreinoSemanaEnum treinoSemana;
    @NotNull
    private boolean expericencia;
    private String experienciaDescricao;
    private boolean desconforto;
    @NotNull
    private String desconfortoDescricao;
    @NotNull
    private boolean fumante;
    @NotNull
    private boolean proteses;
    private String protesesDescricao;
    @NotNull
    private boolean doencaMetabolica;
    private String doenncaMetabolicaDescricao;
}
