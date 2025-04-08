package tech.vitalis.caringu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.vitalis.caringu.entity.Cidade;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Integer> {
}
