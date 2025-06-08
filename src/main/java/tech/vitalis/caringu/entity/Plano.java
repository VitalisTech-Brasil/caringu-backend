package tech.vitalis.caringu.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import tech.vitalis.caringu.enums.PeriodoEnum;

@Entity
@Table(name = "planos")
public class Plano {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "personal_trainers_id")
    private PersonalTrainer personalTrainer;

    private String nome;

    @Enumerated(EnumType.STRING)
    private PeriodoEnum periodo;

    private Integer quantidadeAulas;

    private Double valorAulas;

    public Plano() {
    }

    public Plano(PersonalTrainer personalTrainer, String nome, PeriodoEnum periodo, Integer quantidadeAulas, Double valorAulas) {
        this.personalTrainer = personalTrainer;
        this.nome = nome;
        this.periodo = periodo;
        this.quantidadeAulas = quantidadeAulas;
        this.valorAulas = valorAulas;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PersonalTrainer getPersonalTrainer() {
        return personalTrainer;
    }

    public void setPersonalTrainer(PersonalTrainer personalTrainer) {
        this.personalTrainer = personalTrainer;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public PeriodoEnum getPeriodo() {
        return periodo;
    }

    public void setPeriodo(PeriodoEnum periodo) {
        this.periodo = periodo;
    }

    public Integer getQuantidadeAulas() {
        return quantidadeAulas;
    }

    public void setQuantidadeAulas(Integer quantidadeAulas) {
        this.quantidadeAulas = quantidadeAulas;
    }

    public Double getValorAulas() {
        return valorAulas;
    }

    public void setValorAulas(Double valorAulas) {
        this.valorAulas = valorAulas;
    }
}


