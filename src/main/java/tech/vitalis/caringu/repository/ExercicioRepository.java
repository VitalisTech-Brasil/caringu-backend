package tech.vitalis.caringu.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tech.vitalis.caringu.dtos.Exercicio.ExercicioResponseGetDTO;
import tech.vitalis.caringu.dtos.Exercicio.ExercicioResponseTotalExercicioOrigemDTO;
import tech.vitalis.caringu.entity.Exercicio;

import java.util.List;

@Repository
public interface ExercicioRepository extends JpaRepository<Exercicio, Integer> {

    @Query("""
                SELECT new tech.vitalis.caringu.dtos.Exercicio.ExercicioResponseTotalExercicioOrigemDTO(
                    COUNT(e),
                    CASE
                        WHEN e.personal IS NULL THEN tech.vitalis.caringu.enums.Exercicio.OrigemEnum.BIBLIOTECA
                         ELSE tech.vitalis.caringu.enums.Exercicio.OrigemEnum.PERSONAL
                    END
                )
                FROM Exercicio e
                WHERE e.personal.id = :idPersonal OR e.personal.id IS NULL
                GROUP BY
                    CASE WHEN e.personal IS NULL THEN tech.vitalis.caringu.enums.Exercicio.OrigemEnum.BIBLIOTECA
                         ELSE tech.vitalis.caringu.enums.Exercicio.OrigemEnum.PERSONAL
                    END
            """)
    List<ExercicioResponseTotalExercicioOrigemDTO> buscarTotalExercicioOrigem(Integer idPersonal);

    List<Exercicio> findAllByPersonal_IdOrPersonalIsNull(Integer personalId);
    Page<Exercicio> findAllByPersonal_IdOrPersonalIsNull(Integer idPersonal, Pageable pageable);

}
