package tech.vitalis.caringu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.vitalis.caringu.entity.Especialidade;
import tech.vitalis.caringu.entity.PersonalTrainer;
import tech.vitalis.caringu.entity.PersonalTrainerEspecialidade;
import tech.vitalis.caringu.id.PersonalTrainerEspecialidadeId;

@Repository
public interface PersonalTrainerEspecialidadeRepository extends JpaRepository<PersonalTrainerEspecialidade, PersonalTrainerEspecialidadeId> {
    boolean existsByPersonalTrainerAndEspecialidade(PersonalTrainer pt, Especialidade esp);
}
