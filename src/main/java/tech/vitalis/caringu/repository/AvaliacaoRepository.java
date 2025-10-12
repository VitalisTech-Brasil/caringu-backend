package tech.vitalis.caringu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.vitalis.caringu.entity.Avaliacao;
import tech.vitalis.caringu.entity.PersonalTrainer;

import java.util.List;

@Repository
public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Integer>{
    List<Avaliacao> findByPersonalTrainer(PersonalTrainer personalTrainer);
}
