package tech.vitalis.caringu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.vitalis.caringu.entity.EvolucaoCorporal;

@Repository
public interface EvolucaoCorporalRepository extends JpaRepository<EvolucaoCorporal, Integer> {
}
