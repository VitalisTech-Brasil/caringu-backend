package tech.vitalis.caringu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.vitalis.caringu.entity.PersonalTrainer;

import java.util.List;

@Repository
public interface PersonalTrainerRepository extends JpaRepository<PersonalTrainer, Integer> {
    Boolean existsByCref(String cref);

    List<PersonalTrainer> findPersonalTrainerById(Integer id);
}
