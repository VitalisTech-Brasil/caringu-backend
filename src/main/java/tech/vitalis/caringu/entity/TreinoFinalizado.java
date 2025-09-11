package tech.vitalis.caringu.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "treinos_finalizados")
public class TreinoFinalizado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDateTime dataHorarioInicio;
    private LocalDateTime dataHorarioFim;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "alunos_treinos_id")
    private AlunoTreino alunoTreino;

    public TreinoFinalizado(Integer id, LocalDateTime dataHorarioInicio, LocalDateTime dataHorarioFim, AlunoTreino alunoTreino) {
        this.id = id;
        this.dataHorarioInicio = dataHorarioInicio;
        this.dataHorarioFim = dataHorarioFim;
        this.alunoTreino = alunoTreino;
    }

    public TreinoFinalizado() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public AlunoTreino getAlunoTreino() {
        return alunoTreino;
    }

    public void setAlunoTreino(AlunoTreino alunoTreino) {
        this.alunoTreino = alunoTreino;
    }
}
