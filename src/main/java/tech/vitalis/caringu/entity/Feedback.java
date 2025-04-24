package tech.vitalis.caringu.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "feedbacks")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Feedback {

    @Id
    private Integer id;
    @NotBlank
    private String titulo;
    @NotNull
    private LocalDateTime dataCriacao;
    @ManyToOne
    @JoinColumn(name = "alunos_treino_id")
    private AlunoTreino alunosTreino;

}
