package com.example.mock_project.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.spring.cache.CacheConfig;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class RedissonConfig {
    @Bean(destroyMethod = "shutdown")
    RedissonClient redissonClient() throws IOException {
        Config config = Config.fromYAML(new File("src/main/resources/redisson-config.yml"));
        return Redisson.create(config);
    }

    @Bean
    CacheManager cacheManager(RedissonClient redissonClient){
        Map<String, CacheConfig> config = new HashMap<>();

        // create "customerMap" spring cache with ttl = 24 minutes and maxIdleTime = 12 minutes
        config.put("customerMap", new CacheConfig(24*60*1000, 12*60*1000));

        // create "claimRequestMap" spring cache with ttl = 24 minutes and maxIdleTime = 12 minutes
        config.put("claimRequestMap", new CacheConfig(24*60*1000, 12*60*1000));
        return new RedissonSpringCacheManager(redissonClient, config);
    }
}
