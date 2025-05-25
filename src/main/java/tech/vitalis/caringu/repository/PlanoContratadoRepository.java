package tech.vitalis.caringu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tech.vitalis.caringu.entity.PlanoContratado;

@Repository
public interface PlanoContratadoRepository extends JpaRepository<PlanoContratado, Integer> {

    @Query("SELECT COUNT(DISTINCT pc.aluno.id) " +
            "FROM PlanoContratado pc " +
            "WHERE pc.status = 'ATIVO' " +
            "AND pc.plano.personalTrainer.id = :personalId")
    Integer countAlunosAtivosByPersonalId(@Param("personalId") Integer personalId);

}
