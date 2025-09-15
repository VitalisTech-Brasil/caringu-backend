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
    @JoinColumn(name = "treinos_exercicios_id")
    private TreinoExercicio treinoExercicio;

    private Integer ordem;

    public SessaoTreinoExercicio() {}

    public SessaoTreinoExercicio(
            Integer id, SessaoTreino sessaoTreino,
            TreinoExercicio treinoExercicio,
            Integer ordem
    ) {
        this.id = id;
        this.sessaoTreino = sessaoTreino;
        this.treinoExercicio = treinoExercicio;
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

    public TreinoExercicio getTreinoExercicio() {
        return treinoExercicio;
    }

    public void setTreinoExercicio(TreinoExercicio treinoExercicio) {
        this.treinoExercicio = treinoExercicio;
    }

    public Integer getOrdem() {
        return ordem;
    }

    public void setOrdem(Integer ordem) {
        this.ordem = ordem;
    }
}
