package tech.vitalis.caringu.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "alunos_treinos_exercicios")
public class AlunoTreinoExercicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "aluno_treino_id")
    private AlunoTreino alunoTreino;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "treino_id", nullable = false)
    private Treino treino;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "exercicio_id", nullable = false)
    private Exercicio exercicio;

    @Column(nullable = false)
    private Double carga;

    @Column(nullable = false)
    private Integer repeticoes;

    @Column(nullable = false)
    private Integer series;

    @Column(nullable = false)
    private Integer descanso;

    @Column(name = "observacoes_personalizadas")
    private String observacoesPersonalizadas;

    @Column(name = "data_modificacao")
    private LocalDateTime dataModificacao;

    public AlunoTreinoExercicio(
            Integer id, AlunoTreino alunoTreino, Treino treino,
            Exercicio exercicio, Double carga, Integer repeticoes,
            Integer series, Integer descanso,
            String observacoesPersonalizadas, LocalDateTime dataModificacao
    ) {
        this.id = id;
        this.alunoTreino = alunoTreino;
        this.treino = treino;
        this.exercicio = exercicio;
        this.carga = carga;
        this.repeticoes = repeticoes;
        this.series = series;
        this.descanso = descanso;
        this.observacoesPersonalizadas = observacoesPersonalizadas;
        this.dataModificacao = dataModificacao;
    }

    public AlunoTreinoExercicio() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public AlunoTreino getAlunoTreino() {
        return alunoTreino;
    }

    public void setAlunoTreino(AlunoTreino alunoTreino) {
        this.alunoTreino = alunoTreino;
    }

    public Treino getTreino() {
        return treino;
    }

    public void setTreino(Treino treino) {
        this.treino = treino;
    }

    public Exercicio getExercicio() {
        return exercicio;
    }

    public void setExercicio(Exercicio exercicio) {
        this.exercicio = exercicio;
    }

    public Double getCarga() {
        return carga;
    }

    public void setCarga(Double carga) {
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

