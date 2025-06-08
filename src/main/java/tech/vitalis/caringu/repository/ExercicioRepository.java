package tech.vitalis.caringu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tech.vitalis.caringu.dtos.Exercicio.ExercicioResponseTotalExercicioOrigemDTO;
import tech.vitalis.caringu.entity.Exercicio;

import java.util.List;

@Repository
public interface ExercicioRepository  extends JpaRepository<Exercicio, Integer> {

    @Query("""
        SELECT new tech.vitalis.caringu.dtos.Exercicio.ExercicioResponseTotalExercicioOrigemDTO(
           COUNT(e),
           e.origem
        )
        FROM Exercicio e
        GROUP BY e.origem
""")
    List<ExercicioResponseTotalExercicioOrigemDTO> buscarTotalExercicioOrigem();

}
