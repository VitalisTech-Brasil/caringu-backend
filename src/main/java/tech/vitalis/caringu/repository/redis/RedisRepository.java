package tech.vitalis.caringu.repository.redis;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tech.vitalis.caringu.dtos.Pessoa.security.TentativaDeLogin;

@Repository
public interface RedisRepository extends CrudRepository<TentativaDeLogin, String> {
}
