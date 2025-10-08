package tech.vitalis.caringu.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "treinos_exercicios")
public class TreinoExercicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "exercicios_id")
    private Exercicio exercicio;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "treinos_id")
    private Treino treino;

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

    @Column(name = "data_modificacao", insertable = false, updatable = false)
    private LocalDateTime dataModificacao;

    public TreinoExercicio() {}

    public TreinoExercicio(
            Integer id, Exercicio exercicio, Treino treino,
            BigDecimal carga, Integer repeticoes, Integer series,
            Integer descanso, String observacoesPersonalizadas
    ) {
        this.id = id;
        this.exercicio = exercicio;
        this.treino = treino;
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

    public Exercicio getExercicio() {
        return exercicio;
    }

    public void setExercicio(Exercicio exercicio) {
        this.exercicio = exercicio;
    }

    public Treino getTreino() {
        return treino;
    }

    public void setTreino(Treino treino) {
        this.treino = treino;
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

    public LocalDateTime getDataModificacao() {
        return dataModificacao;
    }

    public void setDataModificacao(LocalDateTime dataModificacao) {
        this.dataModificacao = dataModificacao;
    }
}

