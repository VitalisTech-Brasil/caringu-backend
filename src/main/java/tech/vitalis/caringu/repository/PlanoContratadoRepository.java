package tech.vitalis.caringu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tech.vitalis.caringu.entity.Plano;
import tech.vitalis.caringu.entity.PlanoContratado;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlanoContratadoRepository extends JpaRepository<PlanoContratado, Integer> {

}
