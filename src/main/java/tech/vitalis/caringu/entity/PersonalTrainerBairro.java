package tech.vitalis.caringu.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "personal_trainers_bairros")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PersonalTrainerBairro {

    @Id
    private Integer id;
    @Id
    private Integer personalTrainersId;
    @Id
    private Integer estadosId;

}
