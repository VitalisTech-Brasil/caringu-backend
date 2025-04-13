package tech.vitalis.caringu.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "personal_trainers")
@PrimaryKeyJoinColumn(name = "id")
public class PersonalTrainer extends Pessoa {

    @NotBlank
    @Column(nullable = false, length = 20)
    private String cref;

    @NotBlank
    @Column(length = 100)
    private String especialidade;

    @Positive
    @NotNull
    private Integer experiencia;

    public PersonalTrainer() {}

    public PersonalTrainer(String cref, String especialidade, Integer experiencia) {
        this.cref = cref;
        this.especialidade = especialidade;
        this.experiencia = experiencia;
    }

    public String getCref() {
        return cref;
    }

    public void setCref(String cref) {
        this.cref = cref;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public Integer getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(Integer experiencia) {
        this.experiencia = experiencia;
    }
}
