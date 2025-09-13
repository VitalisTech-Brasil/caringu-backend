package tech.vitalis.caringu.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
@Table(name = "feedbacks")
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String titulo;

    private LocalDateTime dataCriacao;

    @ManyToOne
    @JoinColumn(name = "sessao_treino_id")
    private SessaoTreino sessaoTreino;

    public Feedback(Integer id, String titulo, LocalDateTime dataCriacao, SessaoTreino sessaoTreino) {
        this.id = id;
        this.titulo = titulo;
        this.dataCriacao = dataCriacao;
        this.sessaoTreino = sessaoTreino;
    }

    public Feedback() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public SessaoTreino getSessaoTreino() {
        return sessaoTreino;
    }

    public void setSessaoTreino(SessaoTreino sessaoTreino) {
        this.sessaoTreino = sessaoTreino;
    }
}
