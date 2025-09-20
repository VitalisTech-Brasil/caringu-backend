package tech.vitalis.caringu.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(
        name = "aulas_treinos_exercicios",
        uniqueConstraints = {
                @UniqueConstraint(name = "uq_aulas_exercicio", columnNames = {"aulas_id", "treinos_exercicios_id"})
        }
)
public class AulaTreinoExercicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "aulas_id")
    private Aula aula;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "treinos_exercicios_id")
    private TreinoExercicio treinoExercicio;

    @Column(nullable = false)
    private Integer ordem;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal carga;

    @Column(nullable = false)
    private Integer repeticoes;

    @Column(nullable = false)
    private Integer series;

    @Column(nullable = false)
    private Integer descanso;

    @Column(name = "observacoes_personalizadas", columnDefinition = "TEXT")
    private String observacoesPersonalizadas;

    public AulaTreinoExercicio() {}

    public AulaTreinoExercicio(
            Integer id, Aula aula, TreinoExercicio treinoExercicio,
            Integer ordem, BigDecimal carga, Integer repeticoes,
            Integer series, Integer descanso,
            String observacoesPersonalizadas
    ) {
        this.id = id;
        this.aula = aula;
        this.treinoExercicio = treinoExercicio;
        this.ordem = ordem;
        this.carga = carga;
        this.repeticoes = repeticoes;
        this.series = series;
        this.descanso = descanso;
        this.observacoesPersonalizadas = observacoesPersonalizadas;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Aula getAula() {
        return aula;
    }

    public void setAula(Aula aula) {
        this.aula = aula;
    }

    public TreinoExercicio getTreinoExercicio() {
        return treinoExercicio;
    }

    public void setTreinoExercicio(TreinoExercicio treinoExercicio) {
        this.treinoExercicio = treinoExercicio;
    }

    public Integer getOrdem() {
        return ordem;
    }

    public void setOrdem(Integer ordem) {
        this.ordem = ordem;
    }

    public BigDecimal getCarga() {
        return carga;
    }

    public void setCarga(BigDecimal carga) {
        this.carga = carga;
    }

    public Integer getRepeticoes() {
        return repeticoes;
    }

    public void setRepeticoes(Integer repeticoes) {
        this.repeticoes = repeticoes;
    }

    public Integer getSeries() {
        return series;
    }

    public void setSeries(Integer series) {
        this.series = series;
    }

    public Integer getDescanso() {
        return descanso;
    }

    public void setDescanso(Integer descanso) {
        this.descanso = descanso;
    }

    public String getObservacoesPersonalizadas() {
        return observacoesPersonalizadas;
    }

    public void setObservacoesPersonalizadas(String observacoesPersonalizadas) {
        this.observacoesPersonalizadas = observacoesPersonalizadas;
    }
}
