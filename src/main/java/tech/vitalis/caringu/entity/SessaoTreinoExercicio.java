package tech.vitalis.caringu.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "sessao_treinos_exercicios")
public class SessaoTreinoExercicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sessao_treinos_id")
    private SessaoTreino sessaoTreino;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "alunos_treinos_exercicios_id")
    private AlunoTreinoExercicio alunoTreinoExercicio;

    private Integer ordem;

    public SessaoTreinoExercicio() {}

    public SessaoTreinoExercicio(
            Integer id, SessaoTreino sessaoTreino,
            AlunoTreinoExercicio alunoTreinoExercicio,
            Integer ordem
    ) {
        this.id = id;
        this.sessaoTreino = sessaoTreino;
        this.alunoTreinoExercicio = alunoTreinoExercicio;
        this.ordem = ordem;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public SessaoTreino getSessaoTreino() {
        return sessaoTreino;
    }

    public void setSessaoTreino(SessaoTreino sessaoTreino) {
        this.sessaoTreino = sessaoTreino;
    }

    public AlunoTreinoExercicio getAlunoTreinoExercicio() {
        return alunoTreinoExercicio;
    }

    public void setAlunoTreinoExercicio(AlunoTreinoExercicio alunoTreinoExercicio) {
        this.alunoTreinoExercicio = alunoTreinoExercicio;
    }

    public Integer getOrdem() {
        return ordem;
    }

    public void setOrdem(Integer ordem) {
        this.ordem = ordem;
    }
}
