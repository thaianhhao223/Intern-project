package com.example.mock_project.service;

import com.example.mock_project.entity.ClaimRequest;
import com.example.mock_project.entity.Contract;
import com.example.mock_project.entity.Customer;
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
    private final String CLAIM_REQUEST_MAP = "claimrequest";
    private final String CONTRACT_MAP = "contract";
    private final String CUSTOMER_MAP = "customer";
    private final String COUNT_MAP = "count";
    private final String COUNT_CLAIM_REQUEST = "countclaimrequest";
    private final String COUNT_CONTRACT = "countcontract";
    @Autowired
    private RedissonClient redissonClient;

    private RMap<String, Object> getMap(String map){
        return redissonClient.getMap(map);
    }

    public boolean saveCustomer(Customer customer){
        RMap<String, Object> map = getMap(CUSTOMER_MAP);
        if(map.put(customer.getId().toString(),customer) != null){
            return true;
        }
        return false;
    }

    public boolean saveContract(Contract contract){
        RMap<String, Object> map = getMap(CONTRACT_MAP);
        if(map.put(contract.getId(),contract) != null){
            return true;
        }
        return false;
    }

    public boolean saveClaimRequest(ClaimRequest claimRequest){
        RMap<String, Object> map = getMap(CLAIM_REQUEST_MAP);
        if(map.put(claimRequest.getId(),claimRequest) != null){
            return true;
        }
        return false;
    }

    public ClaimRequest getClaimRequestById(String id){
        RMap<String,Object> map = getMap(CLAIM_REQUEST_MAP);
        log.info("Get claim request with id {} : {}",id,map.get(id));
        return (ClaimRequest) map.get(id);
    }
    public Customer getCustomerById(String id){
        RMap<String,Object> map = getMap(CUSTOMER_MAP);
        log.info("Get customer with id {} : {}",id,map.get(id));
        return (Customer) map.get(id);
    }
    public Contract getContractById(String id){
        RMap<String,Object> map = getMap(CONTRACT_MAP);
        log.info("Get contract with id {} : {}",id,map.get(id));
        return (Contract) map.get(id);
    }

    public Long getCountClaimRequest(){
        RMap<String,Object> map = getMap(COUNT_MAP);
        log.info("Get count claimrequest: {}",map.get(COUNT_CLAIM_REQUEST));
        return (Long) map.get(COUNT_CLAIM_REQUEST);
    }

    public Long getCountContract(){
        RMap<String,Object> map = getMap(COUNT_MAP);
        log.info("Get count claimrequest: {}",map.get(COUNT_CONTRACT));
        return (Long) map.get(COUNT_CONTRACT);
    }

//    public boolean increaseCountClaimRequest(){
//        RMap<String,Object> map = getMap(COUNT_MAP);
//        redissonClient.getList()
//        log.info("Increase count claimrequest: {}",map.get(COUNT_CLAIM_REQUEST));
//    }
}
