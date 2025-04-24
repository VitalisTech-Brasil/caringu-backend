package tech.vitalis.caringu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.vitalis.caringu.entity.Comentario;

@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Integer> {
}
