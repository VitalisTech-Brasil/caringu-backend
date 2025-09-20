package tech.vitalis.caringu.entity;

import jakarta.persistence.*;
import tech.vitalis.caringu.enums.Aula.AulaStatusEnum;

import java.time.LocalDateTime;

@Entity
@Table(name = "aulas")
public class Aula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "planos_contratados_id")
    private PlanoContratado planoContratado;

    @Column(name = "data_horario_inicio")
    private LocalDateTime dataHorarioInicio;

    @Column(name = "data_horario_fim")
    private LocalDateTime dataHorarioFim;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AulaStatusEnum status;

    // Setar caso venha nulo (AGENDADO) -> Seguir default do BD
    @PrePersist
    public void prePersist() {
        if (status == null) {
            status = AulaStatusEnum.AGENDADO;
        }
    }

    public Aula() {}

    public Aula(
            Integer id, PlanoContratado planoContratado,
            LocalDateTime dataHorarioInicio,
            LocalDateTime dataHorarioFim,
            AulaStatusEnum status
    ) {
        this.id = id;
        this.planoContratado = planoContratado;
        this.dataHorarioInicio = dataHorarioInicio;
        this.dataHorarioFim = dataHorarioFim;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PlanoContratado getPlanoContratado() {
        return planoContratado;
    }

    public void setPlanoContratado(PlanoContratado planoContratado) {
        this.planoContratado = planoContratado;
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

    public AulaStatusEnum getStatus() {
        return status;
    }

    public void setStatus(AulaStatusEnum status) {
        this.status = status;
    }
}
