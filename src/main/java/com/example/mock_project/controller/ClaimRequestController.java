package com.example.mock_project.controller;

import com.example.mock_project.dto.ClaimRequestForAnalyzeDTO;
import com.example.mock_project.entity.ClaimRequest;
import com.example.mock_project.entity.Customer;
import com.example.mock_project.service.ClaimRequestService;
import com.example.mock_project.service.CustomerService;
import com.example.mock_project.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

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
    @PostMapping("/")
    public ClaimRequest RequestClaim(@RequestPart(value = "files") MultipartFile[] file,
                                       @RequestParam(value = "name") String name,
                                       @RequestParam(value = "card_id") String card_id){
        Customer customer = customerService.findCustomerByCardIdAndName(name,card_id).get(0);
        ClaimRequest claimRequest = new ClaimRequest();
        claimRequest.setCustomer(customer);

        List<String> listurl = new ArrayList<>();
        for(MultipartFile singleFile: file){
           String url = storageService.store(singleFile);
           listurl.add(url);
        }
        claimRequest.setListUrlImage(listurl);
        claimRequestService.saveClaimRequest(claimRequest);
        return claimRequest;
    }

    @DeleteMapping("/")
    public void deleteRequestClaimById(@RequestParam(name = "id") String id){
        claimRequestService.deleteClaimRequestById(id);
    }


    @GetMapping("/analyze")
    public List<ClaimRequestForAnalyzeDTO> getClaimRequestIsNotAnalyzed(){
        return  claimRequestService.getClaimRequestsIsNotAnalyzed();
    }

    @PutMapping("/analyze")
    public String updateClaimRequestAfterAnalyzed(@RequestBody ClaimRequest claimRequest){
        System.out.println(claimRequest.toString());
        ClaimRequest claimRequestDB = claimRequestService.getClaimRequestById(claimRequest.getId());

        claimRequestDB.setAnalyzed(true);
        claimRequestDB.setAccidentId(claimRequest.getAccidentId());
        claimRequestDB.setHospitalId(claimRequest.getHospitalId());
        claimRequestDB.setReceiptAmount(claimRequest.getReceiptAmount());
        claimRequestDB.setDateOfReceipt(claimRequest.getDateOfReceipt());
        claimRequestDB.setName(claimRequest.getName());
        claimRequestDB.setValidReceipt(claimRequest.isValidReceipt());
        claimRequestService.updateClaimRequest(claimRequestDB);
        return "Success";
    }

    @GetMapping("/review")
    public List<ClaimRequest> getClaimRequestIsAnalyzed(){
        return claimRequestService.getClaimRequestsIsAnalyzedAndIsNotAproveOrReject();
    }

    @PutMapping("/review")
    public String updateClaimRequestApproveOrReject(@RequestParam(name = "id") String id,
                                                    @RequestParam(name = "approve") boolean approve){
        ClaimRequest claimRequest = claimRequestService.getClaimRequestById(id);
        claimRequest.setHasApprove(true);
        claimRequest.setApprove(approve);
        claimRequestService.updateClaimRequest(claimRequest);
        return "Success";
    }
    @GetMapping("/review/payment")
    public List<ClaimRequest> getClaimRequestIsNotReviewToPayment(){
        return claimRequestService.getClaimRequestsIsNotReviewToPayment();
    }
    @PutMapping("/review/payment")
    public String updateClaimRequestIsPaymentOrNot(@RequestParam(name = "id") String id,
                                                   @RequestParam(name = "payment") boolean payment){
        ClaimRequest claimRequest = claimRequestService.getClaimRequestById(id);
        claimRequest.setHasPayment(true);
        claimRequest.setPayment(payment);
        claimRequestService.updateClaimRequest(claimRequest);
        return "Success";
    }
}
