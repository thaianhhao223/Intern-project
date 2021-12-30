package com.example.mock_project.service;

import com.example.mock_project.dto.ClaimRequestForAnalyzeDTO;
import com.example.mock_project.entity.ClaimRequest;
import com.example.mock_project.mapper.ClaimRequestMapper;
import com.example.mock_project.repository.ClaimRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClaimRequestService {
    @Autowired
    private ClaimRequestRepository claimRequestRepository;

    @Autowired
    private ClaimRequestMapper claimRequestMapper;

    public List<ClaimRequest> getAllClaimRequest(){
        return claimRequestRepository.findAll();
    }

    public ClaimRequest getClaimRequestById(String id){
        return claimRequestRepository.findById(id).orElse(null);
    }

    public List<ClaimRequestForAnalyzeDTO> getClaimRequestsIsNotAnalyzed(){
        return claimRequestRepository.findClaimRequestByAnalyzedFalse().stream()
                .map(claimRequestMapper::ClaimRequestToClaimRequestForAnalyze).collect(Collectors.toList());
    }

    public List<ClaimRequest> getClaimRequestsIsAnalyzedAndIsNotAproveOrReject(){
        return claimRequestRepository.findClaimRequestsByAnalyzedTrueAndHasApproveFalse();
    }

    public List<ClaimRequest> getClaimRequestsIsNotReviewToPayment(){
        return claimRequestRepository.findClaimRequestsByHasApproveTrueAndHasPaymentFalse();
    }

    public ClaimRequest saveClaimRequest(ClaimRequest claimRequest){
        claimRequestRepository.save(claimRequest);
        return claimRequest;
    }
    public ClaimRequest updateClaimRequest(ClaimRequest claimRequest){
        claimRequestRepository.saveAndFlush(claimRequest);
        return claimRequest;
    }
    public void deleteClaimRequest(ClaimRequest claimRequest){
        claimRequestRepository.delete(claimRequest);
    }
    public void deleteClaimRequestById(String id){
        ClaimRequest claimRequest = claimRequestRepository.findById(id).get();
        if(claimRequest != null)
            claimRequestRepository.delete(claimRequest);
    }
}
