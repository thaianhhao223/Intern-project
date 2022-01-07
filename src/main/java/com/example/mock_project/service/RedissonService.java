package com.example.mock_project.service;

import com.example.mock_project.entity.ClaimRequest;
import com.example.mock_project.entity.ReponseMessage;
import com.example.mock_project.repository.RedisRepository;
import org.redisson.Redisson;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RedissonService {

    private Logger logger;
    private RedissonClient redissonClient = Redisson.create();
    private  RMap map =  redissonClient.getMap("claimrequest");

    public ReponseMessage saveNewClaimRequestToRedis(ClaimRequest claimRequest){
        map.put(claimRequest.getId(),claimRequest);
        return new ReponseMessage(200,"Save new Claim request to Redis success");
    }
    public ClaimRequest getClaimRequestInRedis(String id){
        logger.info(map.get(id).toString());
        System.out.println(map.get(id).toString());
        return (ClaimRequest) map.get(id);
    }
}
