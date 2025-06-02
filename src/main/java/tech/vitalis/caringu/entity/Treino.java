package tech.vitalis.caringu.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "treinos")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Treino {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private String descricao;
    private Boolean favorito;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "personal_id", nullable = false)
    private PersonalTrainer personal;

    public Treino(Integer id, String nome, String descricao, PersonalTrainer personal) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.personal = personal;
    }

    public Treino() {}

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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public PersonalTrainer getPersonal() {
        return personal;
    }

    public void setPersonal(PersonalTrainer personal) {
        this.personal = personal;
    }
}
