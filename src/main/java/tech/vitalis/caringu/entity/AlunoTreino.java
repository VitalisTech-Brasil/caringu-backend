package tech.vitalis.caringu.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "alunos_treinos")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class AlunoTreino {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "alunos_id")
    private Aluno alunos;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "treinos_exercicios_id")
    private TreinoExercicio treinosExercicios;
    @FutureOrPresent
    private LocalDateTime dataHorarioInicio;
    @Future
    private LocalDateTime dataHorarioFim;
    @Convert(converter = tech.vitalis.caringu.entity.Converter.StringListConverter.class)
    @Column(columnDefinition = "json", nullable = false)
    private List<String> diasSemana;
    @Positive
    private Integer periodoAvaliacao;
    @Future
    private LocalDateTime dataVencimento;

    /*
    @OneToMany(mappedBy = "alunosTreino")
    private List<Feedback> feedbacks; // Para acessar os feedbacks deste treino
     */

    public AlunoTreino(Integer id, Aluno alunos, TreinoExercicio treinosExercicios, LocalDateTime dataHorarioInicio, LocalDateTime dataHorarioFim, List<String> diasSemana, Integer periodoAvaliacao, LocalDateTime dataVencimento) {
        this.id = id;
        this.alunos = alunos;
        this.treinosExercicios = treinosExercicios;
        this.dataHorarioInicio = dataHorarioInicio;
        this.dataHorarioFim = dataHorarioFim;
        this.diasSemana = diasSemana;
        this.periodoAvaliacao = periodoAvaliacao;
        this.dataVencimento = dataVencimento;
    }

    public AlunoTreino() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Aluno getAlunos() {
        return alunos;
    }

    public void setAlunos(Aluno alunos) {
        this.alunos = alunos;
    }

    public TreinoExercicio getTreinosExercicios() {
        return treinosExercicios;
    }

    public void setTreinosExercicios(TreinoExercicio treinosExercicios) {
        this.treinosExercicios = treinosExercicios;
    }

    public LocalDateTime getDataHorarioInicio() {
        return dataHorarioInicio;
    }

    public void setDataHorarioInicio(LocalDateTime dataHorarioInicio) {
        this.dataHorarioInicio = dataHorarioInicio;
    }

    public LocalDateTime getDataHorarioFim() {
        return dataHorarioFim;
    }

    public void setDataHorarioFim(LocalDateTime dataHorarioFim) {
        this.dataHorarioFim = dataHorarioFim;
    }

    public List<String> getDiasSemana() {
        return diasSemana;
    }

    public void setDiasSemana(List<String> diasSemana) {
        this.diasSemana = diasSemana;
    }

    public Integer getPeriodoAvaliacao() {
        return periodoAvaliacao;
    }

    public void setPeriodoAvaliacao(Integer periodoAvaliacao) {
        this.periodoAvaliacao = periodoAvaliacao;
    }

    public LocalDateTime getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(LocalDateTime dataVencimento) {
        this.dataVencimento = dataVencimento;
    }
}
