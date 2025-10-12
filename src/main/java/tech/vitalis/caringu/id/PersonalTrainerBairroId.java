package tech.vitalis.caringu.id;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class PersonalTrainerBairroId implements Serializable {
    private Integer id;
    private Integer personalTrainersId;
    private Integer estadosId;

    public PersonalTrainerBairroId(Integer id, Integer personalTrainersId, Integer estadosId) {
        this.id = id;
        this.personalTrainersId = personalTrainersId;
        this.estadosId = estadosId;
    }

    public PersonalTrainerBairroId() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPersonalTrainersId() {
        return personalTrainersId;
    }

    public void setPersonalTrainersId(Integer personalTrainersId) {
        this.personalTrainersId = personalTrainersId;
    }

    public Integer getEstadosId() {
        return estadosId;
    }

    public void setEstadosId(Integer estadosId) {
        this.estadosId = estadosId;
    }
}