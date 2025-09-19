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
    @NotBlank
    private String titulo;
    @NotNull
    private LocalDateTime dataCriacao;
    @ManyToOne
    @JoinColumn(name = "alunos_treinos_id")
    private AlunoTreino alunosTreino;

    public Feedback(Integer id, String titulo, LocalDateTime dataCriacao, AlunoTreino alunosTreino) {
        this.id = id;
        this.titulo = titulo;
        this.dataCriacao = dataCriacao;
        this.alunosTreino = alunosTreino;
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

    public AlunoTreino getAlunosTreino() {
        return alunosTreino;
    }

    public void setAlunosTreino(AlunoTreino alunosTreino) {
        this.alunosTreino = alunosTreino;
    }
}
