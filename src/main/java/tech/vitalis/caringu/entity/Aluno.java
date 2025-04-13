package tech.vitalis.caringu.entity;

import jakarta.persistence.*;
import tech.vitalis.caringu.enums.Aluno.NivelAtividadeEnum;
import tech.vitalis.caringu.enums.Aluno.NivelExperienciaEnum;

@Entity
@Table(name = "alunos", schema = "vitalis")
@PrimaryKeyJoinColumn(name = "id")
public class Aluno extends Pessoa {

    private Double peso;

    private Double altura;

    @Enumerated(EnumType.STRING)
    @Column(name = "nivel_atividade", nullable = false)
    private NivelAtividadeEnum nivelAtividade;

    @Enumerated(EnumType.STRING)
    @Column(name = "nivel_experiencia", nullable = false)
    private NivelExperienciaEnum nivelExperiencia;

    public Aluno() {}

    public Aluno(Double peso, Double altura, NivelAtividadeEnum nivelAtividade, NivelExperienciaEnum nivelExperiencia) {
        this.peso = peso;
        this.altura = altura;
        this.nivelAtividade = nivelAtividade;
        this.nivelExperiencia = nivelExperiencia;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public Double getAltura() {
        return altura;
    }

    public void setAltura(Double altura) {
        this.altura = altura;
    }

    public NivelAtividadeEnum getNivelAtividade() {
        return nivelAtividade;
    }

    public void setNivelAtividade(NivelAtividadeEnum nivelAtividade) {
        this.nivelAtividade = nivelAtividade;
    }

    public NivelExperienciaEnum getNivelExperiencia() {
        return nivelExperiencia;
    }

    public void setNivelExperiencia(NivelExperienciaEnum nivelExperiencia) {
        this.nivelExperiencia = nivelExperiencia;
    }
}
