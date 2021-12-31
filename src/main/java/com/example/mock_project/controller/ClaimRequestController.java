package com.example.mock_project.controller;

import com.example.mock_project.dto.ClaimRequestForAnalyzeDTO;
import com.example.mock_project.dto.ClaimRequestPaymentDTO;
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

    /**
     * Người dùng gửi một Claim request.
     * @param file Các ảnh receipt họ yêu cầu bồi thường
     * @param name Tên người dùng, dùng để tìm người dùng (customer)
     * @param card_id card_id của họ, dùng để tìm người dùng (customer)
     * @return
     */
    @PostMapping("/")
    public ClaimRequest RequestClaim(@RequestParam(value = "files") MultipartFile[] file,
                                       @RequestParam(value = "name") String name,
                                       @RequestParam(value = "card_id") String card_id){
        // Truy vấn customer có cùng tên và id_card
        Customer customer = customerService.findCustomerByCardIdAndName(name,card_id).get(0);

        // Lưu trữ các file được upload và trả về danh sách đường dẫn của những file đó
        List<String> listurl = new ArrayList<>();
        for(MultipartFile singleFile: file){
           String url = storageService.store(singleFile);
           listurl.add(url);
        }

        // Tạo một Claim Request để lưu trữ
        ClaimRequest claimRequest = new ClaimRequest();
        claimRequest.setCustomer(customer);
        claimRequest.setListUrlImage(listurl);
        claimRequestService.saveClaimRequest(claimRequest);
        return claimRequest;
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
    public String updateClaimRequestAfterAnalyzed(@RequestBody ClaimRequest claimRequest){
        // Chưa xử lý các trường hợp như tìm không thấy kết quả này trùng khớp.
        // Lấy một object có id tương tự
        ClaimRequest claimRequestDB = claimRequestService.getClaimRequestById(claimRequest.getId());
        // set các atribute cho object vừa được lấy ra, để đảm bảo JPA hoạt động đúng.
        claimRequestDB.setHasAnalyzed(true);
        claimRequestDB.setAccidentId(claimRequest.getAccidentId());
        claimRequestDB.setHospitalId(claimRequest.getHospitalId());
        claimRequestDB.setReceiptAmount(claimRequest.getReceiptAmount());
        claimRequestDB.setDateOfReceipt(claimRequest.getDateOfReceipt());
        claimRequestDB.setName(claimRequest.getName());
        claimRequestDB.setValidReceipt(claimRequest.isValidReceipt());
        claimRequestService.updateClaimRequest(claimRequestDB);
        return "Success";
    }

    /**
     * Yêu cầu danh sách các Claim request đã được analyze nhưng chưa được xét duyệt
     * approve hay reject
     * @return danh sách các Claim request
     */
    @GetMapping("/review")
    public List<ClaimRequest> getClaimRequestIsAnalyzed(){
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
        ClaimRequest claimRequest = claimRequestService.getClaimRequestById(id);
        claimRequest.setHasPayment(true);
        claimRequest.setPayment(payment);
        claimRequestService.updateClaimRequest(claimRequest);
        return "Success";
    }

    @GetMapping("/review/payment/history")
    public List<ClaimRequestPaymentDTO> getClaimRequestPaymentHistory(@RequestParam(name = "year") int year,
                                                                      @RequestParam(name = "month") int month){
        return claimRequestService.getClaimRequestsPaymentHistory(month,year);
    }
}
