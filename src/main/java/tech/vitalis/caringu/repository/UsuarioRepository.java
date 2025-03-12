package tech.vitalis.caringu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.vitalis.caringu.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    public Boolean existsByEmail(String email);
}