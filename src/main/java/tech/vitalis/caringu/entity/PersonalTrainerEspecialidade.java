package tech.vitalis.caringu.entity;

import jakarta.persistence.*;
import tech.vitalis.caringu.id.PersonalTrainerEspecialidadeId;

@Entity
@Table(name = "personal_trainer_especialidades")
@IdClass(PersonalTrainerEspecialidadeId.class)
public class PersonalTrainerEspecialidade {

    @Id
    @ManyToOne
    @JoinColumn(name = "personal_trainers_id")
    private PersonalTrainer personalTrainer;

    @Id
    @ManyToOne
    @JoinColumn(name = "especialidades_id")
    private Especialidade especialidade;

    public PersonalTrainerEspecialidade() {}

    public PersonalTrainerEspecialidade(PersonalTrainer personalTrainer, Especialidade especialidade) {
        this.personalTrainer = personalTrainer;
        this.especialidade = especialidade;
    }

    public PersonalTrainer getPersonalTrainer() {
        return personalTrainer;
    }

    public void setPersonalTrainer(PersonalTrainer personalTrainer) {
        this.personalTrainer = personalTrainer;
    }

    public Especialidade getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(Especialidade especialidade) {
        this.especialidade = especialidade;
    }
}
