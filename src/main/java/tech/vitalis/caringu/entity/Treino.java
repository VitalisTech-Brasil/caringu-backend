package tech.vitalis.caringu.entity;

import jakarta.persistence.*;
import tech.vitalis.caringu.enums.TreinoExercicio.GrauDificuldadeEnum;
import tech.vitalis.caringu.enums.TreinoExercicio.OrigemTreinoExercicioEnum;

@Entity
@Table(name = "treinos")
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

    @Enumerated(EnumType.STRING)
    private OrigemTreinoExercicioEnum origem;

    @Enumerated(EnumType.STRING)
    @Column(name = "grau_dificuldade")
    private GrauDificuldadeEnum grauDificuldade;

    public Treino() {}

    public Treino(
            Integer id, String nome, String descricao, Boolean favorito,
            PersonalTrainer personal, OrigemTreinoExercicioEnum origem,
            GrauDificuldadeEnum grauDificuldade
    ) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.favorito = favorito;
        this.personal = personal;
        this.origem = origem;
        this.grauDificuldade = grauDificuldade;
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean getFavorito() {
        return favorito;
    }

    public void setFavorito(Boolean favorito) {
        this.favorito = favorito;
    }

    public PersonalTrainer getPersonal() {
        return personal;
    }

    public void setPersonal(PersonalTrainer personal) {
        this.personal = personal;
    }

    public OrigemTreinoExercicioEnum getOrigem() {
        return origem;
    }

    public void setOrigem(OrigemTreinoExercicioEnum origem) {
        this.origem = origem;
    }

    public GrauDificuldadeEnum getGrauDificuldade() {
        return grauDificuldade;
    }

    public void setGrauDificuldade(GrauDificuldadeEnum grauDificuldade) {
        this.grauDificuldade = grauDificuldade;
    }
}
