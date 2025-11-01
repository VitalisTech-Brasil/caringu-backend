package tech.vitalis.caringu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tech.vitalis.caringu.dtos.Feedback.FeedbackPorAulaDTO;
import tech.vitalis.caringu.entity.Feedback;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {

    @Query("""
            SELECT new tech.vitalis.caringu.dtos.Feedback.FeedbackPorAulaDTO(
                 a.dataHorarioInicio, f.tipoAutor, f.pessoa.id, f.descricao, f.dataCriacao
            )
            FROM Feedback f
            JOIN f.aula a
            WHERE a.id = :aulaId
            ORDER BY f.dataCriacao
        """)
    List<FeedbackPorAulaDTO> buscarFeedbacksPorAula(@Param("aulaId") Integer aulaId);
}
