package tech.vitalis.caringu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.vitalis.caringu.entity.PersonalTrainer;

@Repository
public interface PersonalTrainerRepository extends JpaRepository<PersonalTrainer, Integer> {
    Boolean existsByCref(String cref);
}
