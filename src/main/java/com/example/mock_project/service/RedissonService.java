package com.example.mock_project.service;

import com.example.mock_project.entity.ClaimRequest;
import com.example.mock_project.entity.ReponseMessage;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RedissonService {

    @Autowired
    private RedissonClient redissonClient;

    public ClaimRequest getClaimRequestById(String id){
        RMap<String,ClaimRequest> map = redissonClient.getMap("claimrequest");
        log.info("Get claim request with id {} : {}",id,map.get(id));
        return map.get(id);
    }
}
