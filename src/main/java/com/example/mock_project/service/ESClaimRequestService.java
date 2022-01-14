package com.example.mock_project.service;

import com.example.mock_project.entity.ClaimRequest;
import com.example.mock_project.entity.ReponseMessage;
import com.example.mock_project.esrepository.ElasticClaimRequestRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@Slf4j
public class ESClaimRequestService {
    @Autowired
    private ElasticClaimRequestRepository esClaimRequestRepository;

    public boolean saveClaimRequestToES(ClaimRequest claimRequest){
        esClaimRequestRepository.save(claimRequest);
        return true;
    }
    public ClaimRequest findById(String id){
        return esClaimRequestRepository.findById(id).get();
    }

    public boolean deleteById(String id){
        Optional<ClaimRequest> claimRequestOptional = esClaimRequestRepository.findById(id);
        if(claimRequestOptional.isPresent()){
            esClaimRequestRepository.delete(claimRequestOptional.get());
            return true;
        }else{
            log.error("Dont found Claim request in ES with id: {}",id);
            throw new IndexOutOfBoundsException("Dont found Claim request in ES with id: "+id);
        }
    }
}
