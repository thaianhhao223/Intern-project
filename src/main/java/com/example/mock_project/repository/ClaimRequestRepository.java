package com.example.mock_project.repository;

import com.example.mock_project.entity.ClaimRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClaimRequestRepository extends JpaRepository<ClaimRequest,String> {
    List<ClaimRequest> findClaimRequestByAnalyzedFalse();
    List<ClaimRequest> findClaimRequestsByAnalyzedTrueAndHasApproveFalse();
    List<ClaimRequest> findClaimRequestsByHasApproveTrueAndHasPaymentFalse();
}
