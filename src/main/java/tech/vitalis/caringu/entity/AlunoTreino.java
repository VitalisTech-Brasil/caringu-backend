package tech.vitalis.caringu.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;

import java.time.LocalDate;

@Entity
@Table(name = "alunos_treinos")
public class AlunoTreino {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "aluno_id")
    private Aluno alunos;

    @FutureOrPresent
    @Column(name = "data_inicio")
    private LocalDate dataInicio;

    @Future
    @Column(name = "data_vencimento")
    private LocalDate dataVencimento;

    public AlunoTreino(LocalDate dataVencimento, LocalDate dataInicio, Aluno alunos, Integer id) {
        this.dataVencimento = dataVencimento;
        this.dataInicio = dataInicio;
        this.alunos = alunos;
        this.id = id;
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

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }
}
