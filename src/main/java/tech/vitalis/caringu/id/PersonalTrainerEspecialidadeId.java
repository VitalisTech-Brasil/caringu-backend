package tech.vitalis.caringu.id;

import java.io.Serializable;
import java.util.Objects;

public class PersonalTrainerEspecialidadeId implements Serializable {

    private Integer personalTrainer;
    private Integer especialidade;

    public PersonalTrainerEspecialidadeId() {}

    public PersonalTrainerEspecialidadeId(Integer personalTrainer, Integer especialidade) {
        this.personalTrainer = personalTrainer;
        this.especialidade = especialidade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonalTrainerEspecialidadeId that = (PersonalTrainerEspecialidadeId) o;
        return Objects.equals(personalTrainer, that.personalTrainer) &&
                Objects.equals(especialidade, that.especialidade);
    }

    @Override
    public int hashCode() {
        return Objects.hash(personalTrainer, especialidade);
    }
}