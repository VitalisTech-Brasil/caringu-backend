package tech.vitalis.caringu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.vitalis.caringu.entity.Pessoa;

import java.util.Optional;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Integer> {
    Boolean existsByEmail(String email);
    Optional<Pessoa> findByEmail(String email);
}