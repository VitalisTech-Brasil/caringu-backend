package tech.vitalis.caringu.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "execucoes_exercicios")
public class ExecucaoExercicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "aulas_treinos_exercicios_id")
    private AulaTreinoExercicio aulaTreinoExercicio;

    @Column(name = "carga_executada", nullable = false, precision = 5, scale = 2)
    private BigDecimal cargaExecutada;

    @Column(name = "repeticoes_executadas")
    private Integer repeticoesExecutadas;

    @Column(name = "series_executadas")
    private Integer seriesExecutadas;

    @Column(name = "descanso_executado")
    private Integer descansoExecutado;

    @Column(nullable = false)
    private Boolean finalizado;

    public ExecucaoExercicio() {}

    public ExecucaoExercicio(
            Integer id, AulaTreinoExercicio aulaTreinoExercicio,
            BigDecimal cargaExecutada, Integer repeticoesExecutadas,
            Integer seriesExecutadas, Integer descansoExecutado,
            Boolean finalizado
    ) {
        this.id = id;
        this.aulaTreinoExercicio = aulaTreinoExercicio;
        this.cargaExecutada = cargaExecutada;
        this.repeticoesExecutadas = repeticoesExecutadas;
        this.seriesExecutadas = seriesExecutadas;
        this.descansoExecutado = descansoExecutado;
        this.finalizado = finalizado;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public AulaTreinoExercicio getAulaTreinoExercicio() {
        return aulaTreinoExercicio;
    }

    public void setAulaTreinoExercicio(AulaTreinoExercicio aulaTreinoExercicio) {
        this.aulaTreinoExercicio = aulaTreinoExercicio;
    }

    public BigDecimal getCargaExecutada() {
        return cargaExecutada;
    }

    public void setCargaExecutada(BigDecimal cargaExecutada) {
        this.cargaExecutada = cargaExecutada;
    }

    public Integer getRepeticoesExecutadas() {
        return repeticoesExecutadas;
    }

    public void setRepeticoesExecutadas(Integer repeticoesExecutadas) {
        this.repeticoesExecutadas = repeticoesExecutadas;
    }

    public Integer getSeriesExecutadas() {
        return seriesExecutadas;
    }

    public void setSeriesExecutadas(Integer seriesExecutadas) {
        this.seriesExecutadas = seriesExecutadas;
    }

    public Integer getDescansoExecutado() {
        return descansoExecutado;
    }

    public void setDescansoExecutado(Integer descansoExecutado) {
        this.descansoExecutado = descansoExecutado;
    }

    public Boolean getFinalizado() {
        return finalizado;
    }

    public void setFinalizado(Boolean finalizado) {
        this.finalizado = finalizado;
    }
}
