package tech.vitalis.caringu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.vitalis.caringu.entity.Cidade;

import java.util.List;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Integer> {

    boolean existsByNomeIgnoreCase(String nome);
    List<Cidade> findByNomeContainingIgnoreCase(String nome);
}
