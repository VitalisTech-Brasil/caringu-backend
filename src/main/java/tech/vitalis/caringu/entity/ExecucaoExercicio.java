package tech.vitalis.caringu.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "execucoes_exercicios")
public class ExecucaoExercicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sessao_treinos_exercicios_id")
    private SessaoTreinoExercicio sessaoTreinoExercicio;

    @Column(name = "carga_executada")
    private Double cargaExecutada;

    @Column(name = "repeticoes_executadas")
    private Integer repeticoesExecutadas;

    @Column(name = "series_executadas")
    private Integer seriesExecutadas;

    @Column(name = "descanso_executado")
    private Integer descansoExecutado;

    public ExecucaoExercicio() {}

    public ExecucaoExercicio(
            Integer id, SessaoTreinoExercicio sessaoTreinoExercicio,
            Double cargaExecutada, Integer repeticoesExecutadas,
            Integer seriesExecutadas, Integer descansoExecutado
    ) {
        this.id = id;
        this.sessaoTreinoExercicio = sessaoTreinoExercicio;
        this.cargaExecutada = cargaExecutada;
        this.repeticoesExecutadas = repeticoesExecutadas;
        this.seriesExecutadas = seriesExecutadas;
        this.descansoExecutado = descansoExecutado;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public SessaoTreinoExercicio getSessaoTreinoExercicio() {
        return sessaoTreinoExercicio;
    }

    public void setSessaoTreinoExercicio(SessaoTreinoExercicio sessaoTreinoExercicio) {
        this.sessaoTreinoExercicio = sessaoTreinoExercicio;
    }

    public Double getCargaExecutada() {
        return cargaExecutada;
    }

    public void setCargaExecutada(Double cargaExecutada) {
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
}
