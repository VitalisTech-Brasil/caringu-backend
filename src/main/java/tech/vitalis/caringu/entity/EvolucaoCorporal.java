package tech.vitalis.caringu.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import tech.vitalis.caringu.enums.EvolucaoCorporal.TipoEvolucaoEnum;
import java.time.LocalDateTime;

@Entity
@Table(name = "evolucao_corporal")
public class EvolucaoCorporal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private TipoEvolucaoEnum tipo;

    @Column(name = "url_foto_shape", columnDefinition = "TEXT")
    private String urlFotoShape;

    @Column(name = "data_envio")
    private LocalDateTime dataEnvio;

    @Positive
    @Column(name = "periodo_avaliacao")
    private Integer periodoAvaliacao;

    @ManyToOne
    @JoinColumn(name = "alunos_id", nullable = false)
    private Aluno aluno;

    public EvolucaoCorporal() {}

    public EvolucaoCorporal(Integer id, TipoEvolucaoEnum tipo,
                            String urlFotoShape, LocalDateTime dataEnvio,
                            Integer periodoAvaliacao, Aluno aluno) {
        this.id = id;
        this.tipo = tipo;
        this.urlFotoShape = urlFotoShape;
        this.dataEnvio = dataEnvio;
        this.periodoAvaliacao = periodoAvaliacao;
        this.aluno = aluno;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TipoEvolucaoEnum getTipo() {
        return tipo;
    }

    public void setTipo(TipoEvolucaoEnum tipo) {
        this.tipo = tipo;
    }

    public String getUrlFotoShape() {
        return urlFotoShape;
    }

    public void setUrlFotoShape(String urlFotoShape) {
        this.urlFotoShape = urlFotoShape;
    }

    public LocalDateTime getDataEnvio() {
        return dataEnvio;
    }

    public void setDataEnvio(LocalDateTime dataEnvio) {
        this.dataEnvio = dataEnvio;
    }

    public Integer getPeriodoAvaliacao() {
        return periodoAvaliacao;
    }

    public void setPeriodoAvaliacao(Integer periodoAvaliacao) {
        this.periodoAvaliacao = periodoAvaliacao;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }
}