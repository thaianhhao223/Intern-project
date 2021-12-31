package com.example.mock_project.service;

import com.example.mock_project.dto.ClaimRequestForAnalyzeDTO;
import com.example.mock_project.dto.ClaimRequestPaymentDTO;
import com.example.mock_project.entity.ClaimRequest;
import com.example.mock_project.mapper.ClaimRequestMapper;
import com.example.mock_project.repository.ClaimRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
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

    /**
     * lấy danh sách các Claim request chưa được xem xét receipt
     * @return
     */
    public List<ClaimRequestForAnalyzeDTO> getClaimRequestsIsNotAnalyzed(){
        return claimRequestRepository.findClaimRequestByHasAnalyzedFalse().stream()
                .map(claimRequestMapper::ClaimRequestToClaimRequestForAnalyze).collect(Collectors.toList());
    }

    /**
     * Lấy danh sách các Claim request chưa được xét duyệt approve hay reject
     * @return
     */
    public List<ClaimRequest> getClaimRequestsIsAnalyzedAndIsNotAproveOrReject(){
        return claimRequestRepository.findClaimRequestsByHasAnalyzedTrueAndHasApproveFalse();
    }

    /**
     * Lấy danh sách các Claim request chưa được xem xét payment
     * @return
     */
    public List<ClaimRequest> getClaimRequestsIsNotReviewToPayment(){
        return claimRequestRepository.findClaimRequestsByHasApproveTrueAndHasPaymentFalse();
    }

    /**
     * lấy danh sách các Claim request đã payment trong một tháng
     * @return
     */
    public List<ClaimRequestPaymentDTO> getClaimRequestsPaymentHistory(int month, int year){

        return claimRequestRepository
                .findClaimRequestPaymentHistory(month,year)
                .stream()
                .map(claimRequestMapper::ClaimRequestToClaimRequestPaymentHistory)
                .collect(Collectors.toList());
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
