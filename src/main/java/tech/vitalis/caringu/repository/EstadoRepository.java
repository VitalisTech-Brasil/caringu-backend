package tech.vitalis.caringu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.vitalis.caringu.entity.Estado;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Integer> {
}
