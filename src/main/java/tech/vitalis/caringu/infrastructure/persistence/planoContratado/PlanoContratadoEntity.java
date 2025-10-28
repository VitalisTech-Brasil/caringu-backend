package tech.vitalis.caringu.infrastructure.persistence.planoContratado;

import jakarta.persistence.*;
import tech.vitalis.caringu.entity.Aluno;
import tech.vitalis.caringu.entity.Plano;
import tech.vitalis.caringu.core.domain.valueObject.StatusEnum;

import java.time.LocalDate;

@Entity
@Table(name = "planos_contratados")
public class PlanoContratadoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "alunos_id", nullable = false)
    private Aluno aluno;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "planos_id", nullable = false)
    private Plano plano;

    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    @Column(name = "data_contratacao")
    private LocalDate dataContratacao;

    @Column(name = "data_fim")
    private LocalDate dataFim;

    public PlanoContratadoEntity(Integer id, Aluno aluno, Plano plano, StatusEnum status, LocalDate dataContratacao, LocalDate dataFim) {
        this.id = id;
        this.aluno = aluno;
        this.plano = plano;
        this.status = status;
        this.dataContratacao = dataContratacao;
        this.dataFim = dataFim;
    }

    public PlanoContratadoEntity() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Plano getPlano() {
        return plano;
    }

    public void setPlano(Plano plano) {
        this.plano = plano;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public LocalDate getDataContratacao() {
        return dataContratacao;
    }

    public void setDataContratacao(LocalDate dataContratacao) {
        this.dataContratacao = dataContratacao;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

}
