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
public class PlanosId implements Serializable {

    @Positive
    private Integer alunoId;

    @Positive
    private Integer personalTrainerId;
}