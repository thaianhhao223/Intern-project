package com.example.mock_project.repository;

import com.example.mock_project.entity.ClaimRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface ClaimRequestRepository extends JpaRepository<ClaimRequest,String> {

    // Querry Method

    /**
     * Tìm các Claim request chưa được analyze
     * @return
     */
    List<ClaimRequest> findClaimRequestByHasAnalyzedFalse();

    /**
     * Tìm các Claim request chưa được approve hay reject
     * @return
     */
    List<ClaimRequest> findClaimRequestsByHasAnalyzedTrueAndHasApproveFalse();

    /**
     * Tìm các Claim request chưa được xem xét payment
     * @return
     */
    List<ClaimRequest> findClaimRequestsByHasApproveTrueAndHasPaymentFalse();

    /**
     * Tìm các Claim request đã được xác nhận payment trong một khoảng thời gian
     * @return
     */
    @Query("select clrq from ClaimRequest clrq where hasPayment = true and payment = true " +
            "and month(clrq.dateOfReceipt) = month and year(clrq.dateOfReceipt) = year")
    List<ClaimRequest> findClaimRequestPaymentHistory(int month, int year);
}
