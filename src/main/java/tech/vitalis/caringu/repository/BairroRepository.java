package tech.vitalis.caringu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.vitalis.caringu.entity.Bairro;
import tech.vitalis.caringu.entity.Cidade;

import java.util.List;

@Repository
public interface BairroRepository extends JpaRepository<Bairro, Integer> {

    boolean existsByNomeIgnoreCaseAndCidadeId(String nome, Integer cidadeId);
    List<Bairro> findByNomeContainingIgnoreCase(String nome);
}
