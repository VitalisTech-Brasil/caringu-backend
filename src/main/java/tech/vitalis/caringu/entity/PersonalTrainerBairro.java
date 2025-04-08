package tech.vitalis.caringu.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import tech.vitalis.caringu.id.PersonalTrainerBairroId;

@Entity
@Table(name = "personal_trainers_bairros")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PersonalTrainerBairro {

    @EmbeddedId
    private PersonalTrainerBairroId id;

}
