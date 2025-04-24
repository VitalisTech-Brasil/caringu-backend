package tech.vitalis.caringu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.vitalis.caringu.entity.PersonalTrainerBairro;
import tech.vitalis.caringu.id.PersonalTrainerBairroId;

@Repository
public interface PersonalTrainerBairroRepository extends JpaRepository<PersonalTrainerBairro, PersonalTrainerBairroId> {
}
