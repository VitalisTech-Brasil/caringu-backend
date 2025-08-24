package tech.vitalis.caringu.entity;

import jakarta.persistence.*;
import tech.vitalis.caringu.enums.SessaoTreino.StatusSessaoTreinoEnum;

import java.time.LocalDateTime;

@Entity
@Table(name = "sessao_treinos")
public class SessaoTreino {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "alunos_treinos_id", nullable = false)
    private AlunoTreino alunoTreino;

    @Column(name = "data_horario_previsto", nullable = false)
    private LocalDateTime dataHorarioPrevisto;

    @Column(name = "data_horario_inicio")
    private LocalDateTime dataHorarioInicio;

    @Column(name = "data_horario_fim")
    private LocalDateTime dataHorarioFim;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusSessaoTreinoEnum status;

    @Column(columnDefinition = "TEXT")
    private String observacao;

    public SessaoTreino(
            Integer id, AlunoTreino alunoTreino,
            LocalDateTime dataHorarioPrevisto,
            LocalDateTime dataHorarioInicio,
            LocalDateTime dataHorarioFim,
            StatusSessaoTreinoEnum status,
            String observacao) {
        this.id = id;
        this.alunoTreino = alunoTreino;
        this.dataHorarioPrevisto = dataHorarioPrevisto;
        this.dataHorarioInicio = dataHorarioInicio;
        this.dataHorarioFim = dataHorarioFim;
        this.status = status;
        this.observacao = observacao;
    }

    public SessaoTreino() {}

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

    public LocalDateTime getDataHorarioPrevisto() {
        return dataHorarioPrevisto;
    }

    public void setDataHorarioPrevisto(LocalDateTime dataHorarioPrevisto) {
        this.dataHorarioPrevisto = dataHorarioPrevisto;
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

    public StatusSessaoTreinoEnum getStatus() {
        return status;
    }

    public void setStatus(StatusSessaoTreinoEnum status) {
        this.status = status;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
}
