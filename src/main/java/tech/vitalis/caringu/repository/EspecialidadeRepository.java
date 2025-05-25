package tech.vitalis.caringu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tech.vitalis.caringu.entity.Especialidade;

import java.util.List;

public interface EspecialidadeRepository extends JpaRepository<Especialidade, Integer> {
    @Query("""
    SELECT pe.personalTrainer.id, e.nome
    FROM PersonalTrainerEspecialidade pe
    JOIN pe.especialidade e
    WHERE pe.personalTrainer.id IN :ids
""")
    List<Object[]> buscarNomesPorPersonalIds(@Param("ids") List<Integer> ids);
}
