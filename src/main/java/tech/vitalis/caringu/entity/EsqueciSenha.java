package tech.vitalis.caringu.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "esqueci_senha")
@Data
public class EsqueciSenha {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID autogerado
    private Long id;

    @Column(length = 4, nullable = false)
    private String token; // 4 dígitos apenas

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private LocalDateTime expiracao; // data e hora de expiração
}
