package com.example.mock_project.service;

import com.example.mock_project.entity.ClaimRequest;
import com.example.mock_project.repository.ClaimRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClaimRequestService {
    @Autowired
    private ClaimRequestRepository claimRequestRepository;

    public List<ClaimRequest> getAllClaimRequest(){
        return claimRequestRepository.findAll();
    }
    public ClaimRequest getClaimRequestById(String id){
        return claimRequestRepository.findById(id).orElse(null);
    }
    public ClaimRequest saveClaimRequest(ClaimRequest claimRequest){
        claimRequestRepository.save(claimRequest);
        return claimRequest;
    }
    public ClaimRequest updateClaimRequest(ClaimRequest claimRequest){
        claimRequestRepository.save(claimRequest);
        return claimRequest;
    }
    public void deleteClaimRequest(ClaimRequest claimRequest){
        claimRequestRepository.delete(claimRequest);
    }
}
