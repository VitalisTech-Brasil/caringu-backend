package tech.vitalis.caringu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tech.vitalis.caringu.entity.Especialidade;

import java.util.List;
import java.util.Optional;

public interface EspecialidadeRepository extends JpaRepository<Especialidade, Integer> {
    @Query("""
    SELECT pe.personalTrainer.id, e.nome
    FROM PersonalTrainerEspecialidade pe
    JOIN pe.especialidade e
    WHERE pe.personalTrainer.id IN :ids
""")
    List<Object[]> buscarNomesPorPersonalIds(@Param("ids") List<Integer> ids);

    @Query("""
    SELECT e.nome
    FROM PersonalTrainerEspecialidade pe
    JOIN Especialidade e ON pe.especialidade.id = e.id
    WHERE pe.personalTrainer.id = :personalId
""")
    List<String> buscarNomesPorPersonalId(@Param("personalId") Integer personalId);

    Optional<Especialidade> findByNomeIgnoreCase(String nome);
}
