package tech.vitalis.caringu.entity;

import jakarta.persistence.*;
import tech.vitalis.caringu.enums.TreinoExercicio.GrauDificuldadeEnum;
import tech.vitalis.caringu.enums.TreinoExercicio.OrigemTreinoExercicioEnum;

import java.time.LocalDateTime;

@Entity
@Table(name = "treinos_exercicios")
public class TreinoExercicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "treino_id", nullable = false)
    private Treino treinos;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "exercicio_id", nullable = false)
    private Exercicio exercicio;
    private Double carga;
    private Integer repeticoes;
    private Integer series;
    private Integer descanso;
    private LocalDateTime dataHoraCriacao;
    private LocalDateTime dataHoraModificacao;

    @Enumerated(EnumType.STRING)
    @Column(name = "origem", nullable = false)
    private OrigemTreinoExercicioEnum origemTreinoExercicio;

    @Enumerated(EnumType.STRING)
    @Column(name = "grau_dificuldade", nullable = false)
    private GrauDificuldadeEnum grauDificuldade;

    public TreinoExercicio(Integer id, Treino treinos, Exercicio exercicio, Double carga, Integer repeticoes, Integer series, Integer descanso, LocalDateTime dataHoraCriacao, LocalDateTime dataHoraModificacao, OrigemTreinoExercicioEnum origemTreinoExercicio, GrauDificuldadeEnum grauDificuldade) {
        this.id = id;
        this.treinos = treinos;
        this.exercicio = exercicio;
        this.carga = carga;
        this.repeticoes = repeticoes;
        this.series = series;
        this.descanso = descanso;
        this.dataHoraCriacao = dataHoraCriacao;
        this.dataHoraModificacao = dataHoraModificacao;
        this.origemTreinoExercicio = origemTreinoExercicio;
        this.grauDificuldade = grauDificuldade;
    }

    public TreinoExercicio() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Treino getTreinos() {
        return treinos;
    }

    public void setTreinos(Treino treinos) {
        this.treinos = treinos;
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

    public LocalDateTime getDataHoraCriacao() {
        return dataHoraCriacao;
    }

    public void setDataHoraCriacao(LocalDateTime dataHoraCriacao) {
        this.dataHoraCriacao = dataHoraCriacao;
    }

    public LocalDateTime getDataHoraModificacao() {
        return dataHoraModificacao;
    }

    public void setDataHoraModificacao(LocalDateTime dataHoraModificacao) {
        this.dataHoraModificacao = dataHoraModificacao;
    }

    public OrigemTreinoExercicioEnum getOrigemTreinoExercicio() {
        return origemTreinoExercicio;
    }

    public void setOrigemTreinoExercicio(OrigemTreinoExercicioEnum origemTreinoExercicio) {
        this.origemTreinoExercicio = origemTreinoExercicio;
    }

    public GrauDificuldadeEnum getGrauDificuldade() {
        return grauDificuldade;
    }

    public void setGrauDificuldade(GrauDificuldadeEnum grauDificuldade) {
        this.grauDificuldade = grauDificuldade;
    }
}

