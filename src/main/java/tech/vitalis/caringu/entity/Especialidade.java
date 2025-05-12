package tech.vitalis.caringu.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "especialidades", schema = "vitalis")
public class Especialidade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 50)
    private String nome;

    @OneToMany(mappedBy = "especialidade")
    private List<PersonalTrainerEspecialidade> personalTrainers = new ArrayList<>();

    public Especialidade() {}

    public Especialidade(Integer id, String nome, List<PersonalTrainerEspecialidade> personalTrainers) {
        this.id = id;
        this.nome = nome;
        this.personalTrainers = personalTrainers;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<PersonalTrainerEspecialidade> getPersonalTrainers() {
        return personalTrainers;
    }

    public void setPersonalTrainers(List<PersonalTrainerEspecialidade> personalTrainers) {
        this.personalTrainers = personalTrainers;
    }
}
