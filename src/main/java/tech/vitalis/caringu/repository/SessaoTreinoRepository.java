package tech.vitalis.caringu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.vitalis.caringu.entity.SessaoTreino;

@Repository
public interface SessaoTreinoRepository extends JpaRepository<SessaoTreino, Integer> {
}
