package tech.vitalis.caringu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.vitalis.caringu.entity.Plano;

@Repository
public interface PlanoRepository extends JpaRepository<Plano, Integer> {
}
