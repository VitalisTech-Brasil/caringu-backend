package tech.vitalis.caringu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.vitalis.caringu.entity.PersonalTrainerBairro;
import tech.vitalis.caringu.id.PersonalTrainerBairroId;

import java.util.Optional;

@Repository
public interface PersonalTrainerBairroRepository extends JpaRepository<PersonalTrainerBairro, PersonalTrainerBairroId> {

    Optional<PersonalTrainerBairro> findByPersonalTrainerIdAndBairroId(Integer personalTrainerId, Integer bairroId);
    boolean existsByPersonalTrainerIdAndBairroId(Integer personalTrainerId, Integer bairroId);
}
