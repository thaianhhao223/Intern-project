package com.example.mock_project.repository;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

/**
 * Cách viết lúc đầu của em, nhưng hiện tạo không sử dụng được,
 * Em viết theo kiểu singleton nhưng luôn trả về instance null.
 */
@Component
public class RedisRepository {

    private static RedissonClient redissonClient;
    public RedissonClient getIntance(){
        if(redissonClient == null){
            redissonClient = Redisson.create();
        }
        return redissonClient;
    }
}
