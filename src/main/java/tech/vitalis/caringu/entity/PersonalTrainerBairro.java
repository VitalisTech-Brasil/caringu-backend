package tech.vitalis.caringu.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "personal_trainers_bairros", schema = "vitalis")
public class PersonalTrainerBairro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "personal_trainers_id")
    private PersonalTrainer personalTrainer;

    @ManyToOne(optional = false)
    @JoinColumn(name = "bairro_id")
    private Bairro bairro;

    public PersonalTrainerBairro() {}

    public PersonalTrainerBairro(Integer id, PersonalTrainer personalTrainer, Bairro bairro) {
        this.id = id;
        this.personalTrainer = personalTrainer;
        this.bairro = bairro;
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

    public Bairro getBairro() {
        return bairro;
    }

    public void setBairro(Bairro bairro) {
        this.bairro = bairro;
    }
}