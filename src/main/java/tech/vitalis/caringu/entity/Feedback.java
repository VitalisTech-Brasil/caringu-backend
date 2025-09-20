package tech.vitalis.caringu.entity;

import jakarta.persistence.*;
import tech.vitalis.caringu.enums.Feedback.IntensidadeEnum;
import tech.vitalis.caringu.enums.Feedback.TipoAutorEnum;

import java.time.LocalDateTime;

@Entity
@Table(name = "feedbacks")
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "aulas_id", nullable = false)
    private Aula aula;

    @ManyToOne
    @JoinColumn(name = "pessoas_id", nullable = false)
    private Pessoa pessoa;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_autor", nullable = false)
    private TipoAutorEnum tipoAutor;

    @Enumerated(EnumType.STRING)
    private IntensidadeEnum intensidade;

    // No banco a "dataCriacao" já tem DEFAULT CURRENT_TIMESTAMP ->
    // Assim garante que tanto o BD quanto o Back conseguem setar o valor padrão
    @PrePersist
    public void prePersist() {
        if (dataCriacao == null) {
            dataCriacao = LocalDateTime.now();
        }
    }

    public Feedback() {}

    public Feedback(
            Integer id, Aula aula, Pessoa pessoa,
            String descricao, LocalDateTime dataCriacao,
            TipoAutorEnum tipoAutor, IntensidadeEnum intensidade
    ) {
        this.id = id;
        this.aula = aula;
        this.pessoa = pessoa;
        this.descricao = descricao;
        this.dataCriacao = dataCriacao;
        this.tipoAutor = tipoAutor;
        this.intensidade = intensidade;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Aula getAula() {
        return aula;
    }

    public void setAula(Aula aula) {
        this.aula = aula;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
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

    public IntensidadeEnum getIntensidade() {
        return intensidade;
    }

    public void setIntensidade(IntensidadeEnum intensidade) {
        this.intensidade = intensidade;
    }
}
