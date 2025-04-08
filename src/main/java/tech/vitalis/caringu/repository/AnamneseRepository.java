package tech.vitalis.caringu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.vitalis.caringu.entity.Anamnese;

@Repository
public interface AnamneseRepository extends JpaRepository<Anamnese, Integer> {
}
