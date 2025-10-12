package tech.vitalis.caringu.entity;

import jakarta.persistence.*;
import tech.vitalis.caringu.enums.Exercicio.GrupoMuscularEnum;
import tech.vitalis.caringu.enums.Exercicio.OrigemEnum;

@Entity
@Table(name = "exercicios")
public class Exercicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "personal_id")
    private PersonalTrainer personal;

    @Column(nullable = false)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(name = "grupo_muscular", nullable = false)
    private GrupoMuscularEnum grupoMuscular;

    @Column(name = "url_video")
    private String urlVideo;

    private String observacoes;

    private Boolean favorito;

    @Enumerated(EnumType.STRING)
    @Column(name = "origem", nullable = false)
    private OrigemEnum origem;

    public Exercicio() {
    }

    public Exercicio(Integer id, PersonalTrainer personal, String nome,
                     GrupoMuscularEnum grupoMuscular, String urlVideo,
                     String observacoes, Boolean favorito, OrigemEnum origem) {
        this.id = id;
        this.personal = personal;
        this.nome = nome;
        this.grupoMuscular = grupoMuscular;
        this.urlVideo = urlVideo;
        this.observacoes = observacoes;
        this.favorito = favorito;
        this.origem = origem;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PersonalTrainer getPersonal() {
        return personal;
    }

    public void setPersonal(PersonalTrainer personal) {
        this.personal = personal;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public GrupoMuscularEnum getGrupoMuscular() {
        return grupoMuscular;
    }

    public void setGrupoMuscular(GrupoMuscularEnum grupoMuscular) {
        this.grupoMuscular = grupoMuscular;
    }

    public String getUrlVideo() {
        return urlVideo;
    }

    public void setUrlVideo(String urlVideo) {
        this.urlVideo = urlVideo;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public Boolean getFavorito() {
        return favorito;
    }

    public void setFavorito(Boolean favorito) {
        this.favorito = favorito;
    }

    public OrigemEnum getOrigem() {
        return origem;
    }

    public void setOrigem(OrigemEnum origem) {
        this.origem = origem;
    }
}
