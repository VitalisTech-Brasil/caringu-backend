package tech.vitalis.caringu.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import tech.vitalis.caringu.enums.AlunoTreino.StatusAlunoTreino;

import java.time.LocalDate;

@Entity
@Table(name = "alunos_treinos")
public class AlunoTreino {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "alunos_id")
    private Aluno alunos;

    @FutureOrPresent
    @Column(name = "data_inicio")
    private LocalDate dataInicio;

    @Future
    @Column(name = "data_vencimento")
    private LocalDate dataVencimento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusAlunoTreino status;

    public AlunoTreino() {}

    public AlunoTreino(
            Integer id, Aluno alunos,
            LocalDate dataInicio,
            LocalDate dataVencimento,
            StatusAlunoTreino status
    ) {
        this.id = id;
        this.alunos = alunos;
        this.dataInicio = dataInicio;
        this.dataVencimento = dataVencimento;
        this.status = status;
    }

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

    public StatusAlunoTreino getStatus() {
        return status;
    }

    public void setStatus(StatusAlunoTreino status) {
        this.status = status;
    }
}
