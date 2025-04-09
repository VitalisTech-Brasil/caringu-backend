package tech.vitalis.caringu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.vitalis.caringu.entity.Treino;

@Repository
public interface TreinoRepository extends JpaRepository<Treino, Integer> {
}
