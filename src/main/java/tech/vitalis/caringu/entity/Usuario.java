package tech.vitalis.caringu.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import tech.vitalis.caringu.enums.GeneroPessoaEnum;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Inheritance(strategy = InheritanceType.JOINED)
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @NotBlank(message = "O nome não pode estar em branco")
    @Column(nullable = false)
    private String nome;

    @NotBlank(message = "O email não pode estar em branco")
    @Email(message = "O email deve ser válido")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "A senha não pode estar em branco")
    @Size(min = 6, max = 16, message = "A senha deve ter entre 6 e 16 caracteres")
    @Column(nullable = false)
    private String senha;

    private String celular;
    private String urlFotoPerfil;
    @Past
    @NotNull
    private Date dataNascimento;
    @NotNull
    @Enumerated(EnumType.STRING)
    private GeneroPessoaEnum genero;
    @NotNull
    private LocalDateTime dataCadastro;


}
