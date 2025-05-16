package tech.vitalis.caringu.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import tech.vitalis.caringu.enums.TipoEvolucaoEnum;
import java.time.LocalDateTime;

@Entity
@Table(name = "evolucao_corporal")
public class EvolucaoCorporal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TipoEvolucaoEnum tipo;

    @Column(name = "url_foto_shape", columnDefinition = "TEXT")
    private String urlFotoShape;

    @NotNull
    @Column(name = "data_envio")
    private LocalDateTime dataEnvio;

    @Positive
    @NotNull
    @Column(name = "periodo_avaliacao")
    private Integer periodoAvaliacao;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "alunos_id")
    private Aluno alunos;
}