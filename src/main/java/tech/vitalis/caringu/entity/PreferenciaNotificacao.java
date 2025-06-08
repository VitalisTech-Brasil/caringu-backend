package tech.vitalis.caringu.entity;

import jakarta.persistence.*;
import tech.vitalis.caringu.enums.PreferenciaNotificacao.TipoPreferenciaEnum;

@Entity
@Table(name = "preferencias_notificacao", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"pessoas_id", "tipo"})
})
public class PreferenciaNotificacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "pessoas_id", nullable = false)
    private Pessoa pessoa;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoPreferenciaEnum tipo;

    @Column(nullable = false)
    private boolean ativada = true;

    public PreferenciaNotificacao() {}

    public PreferenciaNotificacao(Integer id, Pessoa pessoa, TipoPreferenciaEnum tipo, boolean ativada) {
        this.id = id;
        this.pessoa = pessoa;
        this.tipo = tipo;
        this.ativada = ativada;
    }

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

    public TipoPreferenciaEnum getTipo() {
        return tipo;
    }

    public void setTipo(TipoPreferenciaEnum tipo) {
        this.tipo = tipo;
    }

    public boolean isAtivada() {
        return ativada;
    }

    public void setAtivada(boolean ativada) {
        this.ativada = ativada;
    }
}
