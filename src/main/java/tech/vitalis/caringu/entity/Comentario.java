package tech.vitalis.caringu.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import tech.vitalis.caringu.enums.IntensidadeComentarioEnum;
import tech.vitalis.caringu.enums.TipoAutorEnum;

import java.time.LocalDateTime;

@Entity
@Table(name = "comentarios")
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "feedback_id", nullable = false)
    private Feedback feedback;
    @ManyToOne
    @JoinColumn(name = "pessoas_id", nullable = false)
    private Pessoa pessoas;
    @NotBlank
    private String descricao;
    @NotNull
    private LocalDateTime dataCriacao;
    private TipoAutorEnum tipoAutor;
    private IntensidadeComentarioEnum intensidade;

    public Comentario(
            Integer id, Feedback feedback, Pessoa pessoas,
            String descricao, LocalDateTime dataCriacao,
            TipoAutorEnum tipoAutor, IntensidadeComentarioEnum intensidade
    ) {
        this.id = id;
        this.feedback = feedback;
        this.pessoas = pessoas;
        this.descricao = descricao;
        this.dataCriacao = dataCriacao;
        this.tipoAutor = tipoAutor;
        this.intensidade = intensidade;
    }

    public Comentario() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Feedback getFeedback() {
        return feedback;
    }

    public void setFeedback(Feedback feedback) {
        this.feedback = feedback;
    }

    public Pessoa getPessoas() {
        return pessoas;
    }

    public void setPessoas(Pessoa pessoas) {
        this.pessoas = pessoas;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public TipoAutorEnum getTipoAutor() {
        return tipoAutor;
    }

    public void setTipoAutor(TipoAutorEnum tipoAutor) {
        this.tipoAutor = tipoAutor;
    }

    public IntensidadeComentarioEnum getIntensidade() {
        return intensidade;
    }

    public void setIntensidade(IntensidadeComentarioEnum intensidade) {
        this.intensidade = intensidade;
    }
}
