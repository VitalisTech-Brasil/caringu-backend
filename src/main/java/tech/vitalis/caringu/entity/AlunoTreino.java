package tech.vitalis.caringu.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDate;
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
    //@FutureOrPresent private LocalDateTime dataHorarioInicio;
    //@Future private LocalDateTime dataHorarioFim;
    @Convert(converter = tech.vitalis.caringu.entity.Converter.StringListConverter.class)
    @Column(columnDefinition = "json", nullable = false)
    private List<String> diasSemana;
    // @Positive private Integer periodoAvaliacao;
    @Future
    private LocalDate dataVencimento;

    /*
    @OneToMany(mappedBy = "alunosTreino")
    private List<Feedback> feedbacks; // Para acessar os feedbacks deste treino
     */

    public AlunoTreino(Integer id, Aluno alunos, TreinoExercicio treinosExercicios, List<String> diasSemana, LocalDate dataVencimento) {
        this.id = id;
        this.alunos = alunos;
        this.treinosExercicios = treinosExercicios;
        this.diasSemana = diasSemana;
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

    public List<String> getDiasSemana() {
        return diasSemana;
    }

    public void setDiasSemana(List<String> diasSemana) {
        this.diasSemana = diasSemana;
    }

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }
}
