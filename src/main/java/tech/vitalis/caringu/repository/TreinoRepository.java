package tech.vitalis.caringu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tech.vitalis.caringu.entity.Treino;

import java.util.List;
import java.util.Optional;

@Repository
public interface TreinoRepository extends JpaRepository<Treino, Integer> {
    Integer countByPersonalId(Integer idPersonal);

    Optional<List<Treino>> findByNomeContainingIgnoreCase(String nome);

    @Query("""
    SELECT t FROM Treino t
    LEFT JOIN FETCH t.personal p
    WHERE t.id = :id
""")
    Optional<Treino> findByIdComPersonal(@Param("id") Integer id);

}
