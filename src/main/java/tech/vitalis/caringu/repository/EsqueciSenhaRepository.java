package tech.vitalis.caringu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.vitalis.caringu.entity.EsqueciSenha;

import java.util.Optional;

public interface EsqueciSenhaRepository extends JpaRepository<EsqueciSenha, Long> {
    Optional<EsqueciSenha> findByEmail(String email);
}
