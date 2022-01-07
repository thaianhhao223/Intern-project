package com.example.mock_project.controller;

import com.example.mock_project.dto.ClaimRequestBeforePaymentDTO;
import com.example.mock_project.dto.ClaimRequestForAnalyzeDTO;
import com.example.mock_project.dto.ClaimRequestNewDTO;
import com.example.mock_project.dto.ClaimRequestPaymentDTO;
import com.example.mock_project.entity.ClaimRequest;
import com.example.mock_project.entity.Customer;
import com.example.mock_project.entity.ReponseMessage;
import com.example.mock_project.service.ClaimRequestService;
import com.example.mock_project.service.CustomerService;
import com.example.mock_project.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Xử lý các chức năng liên quan đến Claim request
 */
@RestController
@RequestMapping("/claim")
public class ClaimRequestController {

    @Autowired
    private StorageService storageService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ClaimRequestService claimRequestService;

    @GetMapping("/")
    public List<ClaimRequest> getAllClaimRequest(){
        return  claimRequestService.getAllClaimRequest();
    }

    @GetMapping("/id")
    public ClaimRequest getClaimRequestById(@RequestParam String id){
        return claimRequestService.getClaimRequestById(id);
    }

    /**
     * Người dùng gửi một Claim request.
     * @param claimRequestNewDTO
     * @return trả về thành công hay thất bại
     */
    @PostMapping("/")
    public ReponseMessage saveRequestClaim(@Valid @RequestBody ClaimRequestNewDTO claimRequestNewDTO){
        return claimRequestService.saveClaimRequest(claimRequestNewDTO);
    }

    @DeleteMapping("/")
    public void deleteRequestClaimById(@RequestParam(name = "id") String id){
        claimRequestService.deleteClaimRequestById(id);
    }

    /**
     * yêu cầu danh sách các Claim request chưa được xem xét
     * @return trả về danh sách các Claim request chưa được xem xét
     */
    @GetMapping("/analyze")
    public List<ClaimRequestForAnalyzeDTO> getClaimRequestIsNotAnalyzed(){
        return  claimRequestService.getClaimRequestsIsNotAnalyzed();
    }

    /**
     * Cập nhật thông tin về một Claim Request sau khi xem xét các receipt
     * @param claimRequest bao gồm: isValidReceipt, hospital ID,
     *                     accident ID, name, receipt amount, date of receipt
     * @return
     */
    @PutMapping("/analyze")
    public ReponseMessage updateClaimRequestAfterAnalyzed(@RequestBody ClaimRequest claimRequest){
        return claimRequestService.updateClaimRequestAfterAnalyze(claimRequest);
    }

    /**
     * Yêu cầu danh sách các Claim request đã được analyze nhưng chưa được xét duyệt
     * approve hay reject
     * @return danh sách các Claim request
     */
    @GetMapping("/review")
    public List<ClaimRequestBeforePaymentDTO> getClaimRequestIsAnalyzed(){
        return claimRequestService.getClaimRequestsIsAnalyzedAndIsNotAproveOrReject();
    }

    /**
     * Cập nhật thông tin về một Claim request sau khi xem xét approve hay reject
     * @param id
     * @param approve true nếu approve hoặc false nếu reject
     * @return
     */
    @PutMapping("/review")
    public String updateClaimRequestApproveOrReject(@RequestParam(name = "id") String id,
                                                    @RequestParam(name = "approve") boolean approve){
        ClaimRequest claimRequest = claimRequestService.getClaimRequestById(id);
        claimRequest.setHasApprove(true);
        claimRequest.setApprove(approve);
        claimRequestService.updateClaimRequest(claimRequest);
        return "Success";
    }

    /**
     * Lấy danh sách các Claim request chưa được xét duyệt payment
     * @return danh sách Claim request
     */
    @GetMapping("/review/payment")
    public List<ClaimRequest> getClaimRequestIsNotReviewToPayment(){
        return claimRequestService.getClaimRequestsIsNotReviewToPayment();
    }

    /**
     * Cập nhật thông tin về một Claim Request có được payment hay không
     * @param id
     * @param payment true nếu đồng ý chi trả hoặc false nếu từ chối chi trả
     * @return
     */
    @PutMapping("/review/payment")
    public String updateClaimRequestIsPaymentOrNot(@RequestParam(name = "id") String id,
                                                   @RequestParam(name = "payment") boolean payment){

        claimRequestService.paymentClaimRequest(id, payment);
        return "Success";
    }

    @GetMapping("/review/payment/history")
    public List<ClaimRequestPaymentDTO> getClaimRequestPaymentHistory(@RequestParam(name = "year") int year,
                                                                      @RequestParam(name = "month") int month){
        return claimRequestService.getClaimRequestsPaymentHistory(month,year);
    }
}
