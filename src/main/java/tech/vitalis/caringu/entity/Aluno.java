package tech.vitalis.caringu.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import tech.vitalis.caringu.enums.FrequenciaTreinoEnum;
import tech.vitalis.caringu.enums.NivelAtividadeEnum;
import tech.vitalis.caringu.enums.NivelExpericenciaEnum;

@Entity
@Table(name = "alunos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Aluno extends Pessoas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Positive
    private Double peso;
    @Positive
    private Double altura;
    @NotNull
    @Enumerated(EnumType.STRING)
    private NivelAtividadeEnum nivelAtividade;
    @NotNull
    @Enumerated(EnumType.STRING)
    private FrequenciaTreinoEnum frequenciaTreino;
    private Boolean fumante;
    @NotNull
    @Enumerated(EnumType.STRING)
    private NivelExpericenciaEnum nivelExpericencia;

}
