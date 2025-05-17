package tech.vitalis.caringu.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import tech.vitalis.caringu.enums.Notificacoes.TipoNotificacaoEnum;

import java.time.LocalDateTime;

@Entity
@Table(name = "notificacoes")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Notificacoes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pessoas_id", nullable = false)
    private Pessoa pessoa;
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoNotificacaoEnum tipo;
    private String titulo;
    private boolean visualizada;
    private LocalDateTime dataCriacao;

    public Notificacoes(Integer id, Pessoa pessoa, TipoNotificacaoEnum tipo, String titulo, boolean visualizada, LocalDateTime dataCriacao) {
        this.id = id;
        this.pessoa = pessoa;
        this.tipo = tipo;
        this.titulo = titulo;
        this.visualizada = visualizada;
        this.dataCriacao = dataCriacao;
    }

    public Notificacoes() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public TipoNotificacaoEnum getTipo() {
        return tipo;
    }

    public void setTipo(TipoNotificacaoEnum tipo) {
        this.tipo = tipo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public boolean isVisualizada() {
        return visualizada;
    }

    public void setVisualizada(boolean visualizada) {
        this.visualizada = visualizada;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
}
