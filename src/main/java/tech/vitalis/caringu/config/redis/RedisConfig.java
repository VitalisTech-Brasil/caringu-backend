package tech.vitalis.caringu.config.redis;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
@Profile({"dev-with-redis", "prod"})
@EnableRedisRepositories(basePackages = "tech.vitalis.caringu.repository.redis")
public class RedisConfig {
}