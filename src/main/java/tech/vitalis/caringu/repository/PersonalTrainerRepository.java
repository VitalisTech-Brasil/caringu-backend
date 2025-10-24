package tech.vitalis.caringu.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tech.vitalis.caringu.dtos.PersonalTrainer.PersonalTrainerInfoBasicaDTO;
import tech.vitalis.caringu.entity.PersonalTrainer;

import javax.swing.text.html.Option;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

@Repository
public interface PersonalTrainerRepository extends JpaRepository<PersonalTrainer, Integer> {
    Boolean existsByCref(String cref);

    List<PersonalTrainer> findPersonalTrainerById(Integer id);

    @Query("""
    SELECT new tech.vitalis.caringu.dtos.PersonalTrainer.PersonalTrainerInfoBasicaDTO(
        p.id,
        p.nome,
        p.email,
        p.celular,
        p.urlFotoPerfil,
        p.genero,
        p.experiencia,
        b.nome,
        c.nome,
        COALESCE(CAST(AVG(a.nota) AS double), 0.0),
        COUNT(a.id)
    )
    FROM PersonalTrainer p
    JOIN PersonalTrainerBairro pb ON pb.personalTrainer.id = p.id
    JOIN Bairro b ON pb.bairro.id = b.id
    JOIN Cidade c ON b.cidade.id = c.id
    LEFT JOIN Avaliacao a ON a.personalTrainer.id = p.id
    GROUP BY
        p.id, p.nome, p.email, p.celular, p.urlFotoPerfil,
        p.genero, p.experiencia, b.nome, c.nome
""")
    List<PersonalTrainerInfoBasicaDTO> buscarBasicos();

    @Query("""
    SELECT new tech.vitalis.caringu.dtos.PersonalTrainer.PersonalTrainerInfoBasicaDTO(
        p.id,
        p.nome,
        p.email,
        p.celular,
        p.urlFotoPerfil,
        p.genero,
        p.experiencia,
        b.nome,
        c.nome,
        COALESCE(CAST(AVG(a.nota) AS double), 0.0),
        COUNT(a.id)
    )
    FROM PersonalTrainer p
    JOIN PersonalTrainerBairro pb ON pb.personalTrainer.id = p.id
    JOIN Bairro b ON pb.bairro.id = b.id
    JOIN Cidade c ON b.cidade.id = c.id
    LEFT JOIN Avaliacao a ON a.personalTrainer.id = p.id
    WHERE p.id = :personalId
    GROUP BY
        p.id, p.nome, p.email, p.celular, p.urlFotoPerfil,
        p.genero, p.experiencia, b.nome, c.nome
""")
    Optional<PersonalTrainerInfoBasicaDTO> buscarBasicoPorId(@Param("personalId") Integer personalId);

    Page<PersonalTrainer> findAll(Pageable pageable);
}
