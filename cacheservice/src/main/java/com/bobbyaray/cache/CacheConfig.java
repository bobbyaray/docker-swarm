package com.bobbyaray.cache;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;

/**
 * Created by robertray on 6/22/17.
 */
@Configuration
public class CacheConfig {
    @Value("${spring.redis.host}")
    private String redishost;

    @Bean
    public Jedis getJedis(){
        return new Jedis(redishost);
    }
}
