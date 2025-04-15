package tech.vitalis.caringu.entity;

import tech.vitalis.caringu.converter.StringListConverter;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "personal_trainers", schema = "vitalis")
@PrimaryKeyJoinColumn(name = "id")
public class PersonalTrainer extends Pessoa {

    @Column(nullable = false, length = 20)
    private String cref;

    @Convert(converter = StringListConverter.class)
    @Column(columnDefinition = "json", nullable = false)
    private List<String> especialidade;

    private Integer experiencia;

    public PersonalTrainer() {}

    public PersonalTrainer(String cref, List<String> especialidade, Integer experiencia) {
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

    public List<String> getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(List<String> especialidade) {
        this.especialidade = especialidade;
    }

    public Integer getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(Integer experiencia) {
        this.experiencia = experiencia;
    }
}
