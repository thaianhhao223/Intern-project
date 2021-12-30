package com.example.mock_project.controller;

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
//        return " You successfully uploaded!";
    }
}
