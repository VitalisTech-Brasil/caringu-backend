package tech.vitalis.caringu.id;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Positive;

import java.io.Serializable;

@Embeddable
public class AlunoTreinoId implements Serializable {

    @Positive
    private Integer alunosId;
    @Positive
    private Integer treinoExercicioId;

    public AlunoTreinoId(Integer alunosId, Integer treinoExercicioId) {
        this.alunosId = alunosId;
        this.treinoExercicioId = treinoExercicioId;
    }

    public AlunoTreinoId() {}

    public Integer getAlunosId() {
        return alunosId;
    }

    public void setAlunosId(Integer alunosId) {
        this.alunosId = alunosId;
    }

    public Integer getTreinoExercicioId() {
        return treinoExercicioId;
    }

    public void setTreinoExercicioId(Integer treinoExercicioId) {
        this.treinoExercicioId = treinoExercicioId;
    }
}
