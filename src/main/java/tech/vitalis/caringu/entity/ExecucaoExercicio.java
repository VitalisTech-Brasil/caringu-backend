package tech.vitalis.caringu.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "execucoes_exercicios")
public class ExecucaoExercicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sessao_treino_id", nullable = false)
    private SessaoTreino sessaoTreino;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "alunos_treinos_exercicios_id", nullable = false)
    private AlunoTreinoExercicio alunoTreinoExercicio;

    @Column(name = "carga_executada")
    private Double cargaExecutada;

    @Column(name = "repeticoes_executadas")
    private Integer repeticoesExecutadas;

    @Column(name = "series_executadas")
    private Integer seriesExecutadas;

    @Column(name = "descanso_executado")
    private Integer descansoExecutado;

    public ExecucaoExercicio(
            Integer id, SessaoTreino sessaoTreino,
            AlunoTreinoExercicio alunoTreinoExercicio,
            Double cargaExecutada, Integer repeticoesExecutadas,
            Integer seriesExecutadas, Integer descansoExecutado
    ) {
        this.id = id;
        this.sessaoTreino = sessaoTreino;
        this.alunoTreinoExercicio = alunoTreinoExercicio;
        this.cargaExecutada = cargaExecutada;
        this.repeticoesExecutadas = repeticoesExecutadas;
        this.seriesExecutadas = seriesExecutadas;
        this.descansoExecutado = descansoExecutado;
    }

    public ExecucaoExercicio() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public SessaoTreino getSessaoTreino() {
        return sessaoTreino;
    }

    public void setSessaoTreino(SessaoTreino sessaoTreino) {
        this.sessaoTreino = sessaoTreino;
    }

    public AlunoTreinoExercicio getAlunoTreinoExercicio() {
        return alunoTreinoExercicio;
    }

    public void setAlunoTreinoExercicio(AlunoTreinoExercicio alunoTreinoExercicio) {
        this.alunoTreinoExercicio = alunoTreinoExercicio;
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
