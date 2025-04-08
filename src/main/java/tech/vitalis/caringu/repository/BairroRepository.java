package tech.vitalis.caringu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.vitalis.caringu.entity.Bairro;

@Repository
public interface BairroRepository extends JpaRepository<Bairro, Integer> {
}
