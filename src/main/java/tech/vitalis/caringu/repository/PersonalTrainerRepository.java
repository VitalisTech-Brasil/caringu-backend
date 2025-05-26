package tech.vitalis.caringu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tech.vitalis.caringu.dtos.PersonalTrainer.PersonalTrainerDisponivelResponseDTO;
import tech.vitalis.caringu.dtos.PersonalTrainer.PersonalTrainerInfoBasicaDTO;
import tech.vitalis.caringu.entity.PersonalTrainer;

import java.util.List;

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
        c.nome
    )
    FROM PersonalTrainer p
    JOIN PersonalTrainerBairro pb ON pb.personalTrainer.id = p.id
    JOIN Bairro b ON pb.bairro.id = b.id
    JOIN Cidade c ON b.cidade.id = c.id
""")
    List<PersonalTrainerInfoBasicaDTO> buscarBasicos();
}
