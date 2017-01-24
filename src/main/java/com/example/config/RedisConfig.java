package com.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@Configuration
//@Profile("redis")
@EnableRedisHttpSession
public class RedisConfig {
}
