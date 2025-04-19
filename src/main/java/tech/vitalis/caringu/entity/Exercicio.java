package tech.vitalis.caringu.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "exercicios")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Exercicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String grupoMuscular;
    private String urlVideo;
    private String observacoes;
    private Boolean favorito;
    private String origem;

    public Exercicio(Integer id, String nome, String grupoMuscular, String urlVideo, String observacoes, Boolean favorito, String origem) {
        this.id = id;
        this.nome = nome;
        this.grupoMuscular = grupoMuscular;
        this.urlVideo = urlVideo;
        this.observacoes = observacoes;
        this.favorito = favorito;
        this.origem = origem;
    }

    public Exercicio() {}

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

    public String getGrupoMuscular() {
        return grupoMuscular;
    }

    public void setGrupoMuscular(String grupoMuscular) {
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

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    @Override
    public String toString() {
        return "Exercicio{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", grupoMuscular='" + grupoMuscular + '\'' +
                ", urlVideo='" + urlVideo + '\'' +
                ", observacoes='" + observacoes + '\'' +
                ", favorito=" + favorito +
                ", origem='" + origem + '\'' +
                '}';
    }
}
