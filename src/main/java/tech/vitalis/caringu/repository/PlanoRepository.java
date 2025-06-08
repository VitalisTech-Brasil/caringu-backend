package tech.vitalis.caringu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tech.vitalis.caringu.dtos.Plano.PlanoRespostaRecord;
import tech.vitalis.caringu.dtos.Plano.PlanoResumoDTO;
import tech.vitalis.caringu.entity.Plano;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlanoRepository extends JpaRepository<Plano, Integer> {
    @Query("SELECT p FROM Plano p WHERE p.personalTrainer.id = :personalTrainerId")
    List<Plano> findByPersonalTrainerId(@Param("personalTrainerId") Integer personalTrainerId);

    Optional<Plano> findByPersonalTrainerIdAndIdEquals(Integer personalId, Integer planoId);

    @Query("""
    SELECT new tech.vitalis.caringu.dtos.Plano.PlanoResumoDTO(
        p.id,
        p.personalTrainer.id,
        p.nome,
        p.periodo,
        p.quantidadeAulas,
        p.valorAulas
    )
    FROM Plano p
    WHERE p.personalTrainer.id IN :personalIds
""")
    List<PlanoResumoDTO> findResumoByPersonalIds(@Param("personalIds") List<Integer> personalIds);

    @Query("""
    SELECT new tech.vitalis.caringu.dtos.Plano.PlanoResumoDTO(
        p.id,
        p.personalTrainer.id,
        p.nome,
        p.periodo,
        p.quantidadeAulas,
        p.valorAulas
    )
    FROM Plano p
    WHERE p.personalTrainer.id = :personalIds
""")
    List<PlanoResumoDTO> findResumoByPersonalId(@Param("personalIds") Integer personalIds);
}
