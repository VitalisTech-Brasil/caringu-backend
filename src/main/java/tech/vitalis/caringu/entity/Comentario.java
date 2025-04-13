package tech.vitalis.caringu.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import tech.vitalis.caringu.enums.IntensidadeComentarioEnum;
import tech.vitalis.caringu.enums.TipoAutorEnum;

import java.time.LocalDateTime;

@Entity
@Table(name = "comentarios")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "feedback_id", nullable = false)
    private Feedback feedback;
    @ManyToOne
    @JoinColumn(name = "pessoas_id", nullable = false)
    private Pessoa pessoas;
    @NotBlank
    private String descricao;
    @NotNull
    private LocalDateTime dataCriacao;
    private TipoAutorEnum tipoAutor;
    private IntensidadeComentarioEnum intensidade;

}
