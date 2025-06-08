package tech.vitalis.caringu.entity;

import tech.vitalis.caringu.entity.Converter.StringListConverter;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "personal_trainers", schema = "vitalis")
@PrimaryKeyJoinColumn(name = "id")
public class PersonalTrainer extends Pessoa {

    @Column(nullable = false, length = 20)
    private String cref;

    private Integer experiencia;

    @OneToMany(mappedBy = "personalTrainer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PersonalTrainerEspecialidade> especialidades = new ArrayList<>();

    public PersonalTrainer() {}

    public PersonalTrainer(String cref, Integer experiencia, List<PersonalTrainerEspecialidade> especialidades) {
        this.cref = cref;
        this.experiencia = experiencia;
        this.especialidades = especialidades;
    }

    public String getCref() {
        return cref;
    }

    public void setCref(String cref) {
        this.cref = cref;
    }

    public Integer getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(Integer experiencia) {
        this.experiencia = experiencia;
    }

    public List<PersonalTrainerEspecialidade> getEspecialidades() {
        return especialidades;
    }

    public void setEspecialidades(List<PersonalTrainerEspecialidade> especialidades) {
        this.especialidades = especialidades;
    }
}
