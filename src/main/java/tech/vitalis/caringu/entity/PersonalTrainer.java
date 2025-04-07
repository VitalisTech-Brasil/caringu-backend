package tech.vitalis.caringu.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Entity
@Table(name = "personal_trainers")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PersonalTrainer extends Usuario{

    @NotBlank
    private String cref;
    @NotBlank
    private String especialidade;
    @Positive
    @NotNull
    private Integer experiencia;


}
