package tech.vitalis.caringu.id;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class AlunoTreinoId implements Serializable {

    @Positive
    private Integer treinoId;
    @Positive
    private Integer alunosId;
    @Positive
    private Integer exercicioId;
}
