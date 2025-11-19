package tech.vitalis.caringu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tech.vitalis.caringu.dtos.Avaliacao.FiltroAvaliacaoResponseDTO;
import tech.vitalis.caringu.entity.Avaliacao;
import tech.vitalis.caringu.entity.PersonalTrainer;

import java.util.List;

@Repository
public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Integer>{
    List<Avaliacao> findByPersonalTrainer(PersonalTrainer personalTrainer);

    @Query("""
    SELECT new tech.vitalis.caringu.dtos.Avaliacao.FiltroAvaliacaoResponseDTO(
        a.personalTrainer.id,
        a.aluno.id,
        a.aluno.nome,
        a.aluno.urlFotoPerfil,
        a.nota,
        a.comentario,
        a.dataAvaliacao
    )
    FROM Avaliacao a
    WHERE a.personalTrainer.id = :idPersonal
    AND (:filtroNota IS NULL OR a.nota = :filtroNota)
""")
    List<FiltroAvaliacaoResponseDTO> findAvaliacoesByPersonalAndNota(
            @Param("idPersonal") Integer idPersonal,
            @Param("filtroNota") Double filtroNota
    );

    @Query("SELECT a FROM Avaliacao a WHERE a.aluno.id = :alunoId AND a.personalTrainer.id = :personalTrainerId")
    java.util.Optional<Avaliacao> findByAlunoIdAndPersonalTrainerId(
            @Param("alunoId") Integer alunoId,
            @Param("personalTrainerId") Integer personalTrainerId
    );

}
