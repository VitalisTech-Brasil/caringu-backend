package tech.vitalis.caringu.id;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PersonalTrainerBairroId implements Serializable {
    private Integer id;
    private Integer personalTrainersId;
    private Integer estadosId;
}