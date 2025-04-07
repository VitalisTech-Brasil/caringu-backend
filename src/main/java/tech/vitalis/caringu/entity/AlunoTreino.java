package tech.vitalis.caringu.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import tech.vitalis.caringu.enums.DiasDaSemanaEnum;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "alunos_treinos")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class AlunoTreino {

    @Id
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "alunos_id", nullable = false)
    private Aluno alunos;
    @ManyToOne
    @JoinColumn(name = "treinos_exercicios_id")
    private TreinoExercicio treinosExercicios;
    @FutureOrPresent
    private LocalDateTime dataHoraInicio;
    @Future
    private LocalDateTime dataHoraFim;
    @NotNull
    @Enumerated(EnumType.STRING)
    private DiasDaSemanaEnum diasSemana;
    @Positive
    private Integer periodoAvaliacao;

    @OneToMany(mappedBy = "alunosTreino")
    private List<Feedback> feedbacks; // Para acessar os feedbacks deste treino
}
