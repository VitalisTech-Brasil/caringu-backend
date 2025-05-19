package tech.vitalis.caringu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.vitalis.caringu.entity.Notificacoes;

import java.util.List;

@Repository
public interface NotificacoesRepository extends JpaRepository<Notificacoes, Integer> {
    List<Notificacoes> findByPessoa_Id(Integer pessoasId);
}