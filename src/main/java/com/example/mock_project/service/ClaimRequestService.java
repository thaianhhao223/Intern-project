package com.example.mock_project.service;

import com.example.mock_project.config.RabbitMQConfig;
import com.example.mock_project.dto.ClaimRequestBeforePaymentDTO;
import com.example.mock_project.dto.ClaimRequestForAnalyzeDTO;
import com.example.mock_project.dto.ClaimRequestNewDTO;
import com.example.mock_project.dto.ClaimRequestPaymentDTO;
import com.example.mock_project.entity.ClaimRequest;
import com.example.mock_project.entity.Customer;
import com.example.mock_project.entity.ReponseMessage;
import com.example.mock_project.mapper.ClaimRequestMapper;
import com.example.mock_project.rabbitmq.ReceiveMessage;
import com.example.mock_project.repository.ClaimRequestRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class ClaimRequestService {
    private final RabbitTemplate rabbitTemplate;
    private final ReceiveMessage receiver;
    private CustomerService customerService;
    private RedissonService redissonService;
    private ESClaimRequestService esClaimRequestService;
    private ClaimRequestRepository claimRequestRepository;
    private ClaimRequestMapper claimRequestMapper;

    @Autowired
    public ClaimRequestService(RabbitTemplate rabbitTemplate,
                               CustomerService customerService,
                               RedissonService redissonService,
                               ESClaimRequestService esClaimRequestService,
                               ClaimRequestRepository claimRequestRepository,
                               ClaimRequestMapper claimRequestMapper,
                               ReceiveMessage receiver) {
        this.rabbitTemplate = rabbitTemplate;
        this.customerService = customerService;
        this.redissonService = redissonService;
        this.esClaimRequestService = esClaimRequestService;
        this.claimRequestRepository = claimRequestRepository;
        this.claimRequestMapper = claimRequestMapper;
        this.receiver = receiver;
    }

    public List<ClaimRequest> getAllClaimRequest(){
        return claimRequestRepository.findAll();
    }

    public ClaimRequest getClaimRequestById(String id){
        ClaimRequest claimRequest;
//        claimRequest = redissonService.getClaimRequestById(id);
//        if(claimRequest != null){
//            log.info("Get Claim request from redis");
//            return claimRequest;
//        }
        claimRequest = esClaimRequestService.findById(id);
        if(claimRequest != null){
            log.info("Get Claim request from ES");
            return claimRequest;
        }
        Optional<ClaimRequest> claimRequestOptional = claimRequestRepository.findById(id);
        if(claimRequestOptional.isPresent()){
            log.info("Get Claim request from db");
            return claimRequestOptional.get();
        }else{
            throw new IndexOutOfBoundsException("Not found Claim Request with id: "+id);
        }
    }

    /**
     * lấy danh sách các Claim request chưa được xem xét receipt
     * @return một danh sách các Claim request chưa được xem xét receipt
     */
    public List<ClaimRequestForAnalyzeDTO> getClaimRequestsIsNotAnalyzed(){
        return claimRequestRepository.findClaimRequestByHasAnalyzedFalse().stream()
                .map(claimRequestMapper::ClaimRequestToClaimRequestForAnalyze).collect(Collectors.toList());
    }

    /**
     * Lấy danh sách các Claim request chưa được xét duyệt approve hay reject
     * @return danh sách các Claim request chưa được xét duyệt approve hay reject
     */
    public List<ClaimRequestBeforePaymentDTO> getClaimRequestsIsAnalyzedAndIsNotAproveOrReject(){
        return claimRequestRepository.findClaimRequestsByHasAnalyzedTrueAndHasApproveFalse().stream()
                .map(claimRequestMapper::ClaimRequestToClaimRequestBeforePayment)
                .collect(Collectors.toList());
    }

    /**
     * Lấy danh sách các Claim request chưa được xem xét payment
     * @return danh sách các Claim request chưa được xem xét payment
     */
    public List<ClaimRequest> getClaimRequestsIsNotReviewToPayment(){
        return claimRequestRepository.findClaimRequestsByHasApproveTrueAndHasPaymentFalse();
    }

    /**
     * lấy danh sách các Claim request đã payment trong một tháng
     * @return danh sách các Claim request đã payment trong một tháng
     */
    public List<ClaimRequestPaymentDTO> getClaimRequestsPaymentHistory(int month, int year){

        return claimRequestRepository
                .findClaimRequestPaymentHistory(month,year)
                .stream()
                .map(claimRequestMapper::ClaimRequestToClaimRequestPaymentHistory)
                .collect(Collectors.toList());
    }

    /**
     * Thực hiện lưu một Claim Request mới
     * @param claimRequestNewDTO được client gửi đến
     * @return ReponseMessage
     */
    @Transactional
    public ReponseMessage saveClaimRequest(ClaimRequestNewDTO claimRequestNewDTO){
        // Truy vấn customer có cùng tên và id_card
        List<Customer> listCustomer = customerService.findCustomerByCardIdAndName(claimRequestNewDTO.getName()
                , claimRequestNewDTO.getCardId());
        Customer customer;
        if(listCustomer.size() > 0){
            customer = listCustomer.get(0);
        }else {
            throw new IndexOutOfBoundsException("Not found any customer with name and id has been provided");
        }
        // Tạo một Claim Request để lưu trữ
        ClaimRequest claimRequest = new ClaimRequest();
        claimRequest.setCustomer(customer);
        claimRequest.setListUrlImage(claimRequestNewDTO.getListUrlImage());
        claimRequestRepository.save(claimRequest);
        rabbitTemplate.convertAndSend(RabbitMQConfig.CLAIMREQUEST_EXCHANGE,RabbitMQConfig.CLAIMREQUEST_CREATE_UPDATE,
                claimRequest);
//        esClaimRequestService.saveClaimRequestToES(claimRequest);
//        redissonService.saveClaimRequest(claimRequest);
        ReponseMessage reponseMessage
                = new ReponseMessage(200,"Save a new Claim request success");
        return reponseMessage;
    }
    public ReponseMessage updateClaimRequest(ClaimRequest claimRequest){
        claimRequestRepository.saveAndFlush(claimRequest);
        rabbitTemplate.convertAndSend(RabbitMQConfig.CLAIMREQUEST_EXCHANGE,RabbitMQConfig.CLAIMREQUEST_CREATE_UPDATE,
                claimRequest);
        ReponseMessage reponseMessage
                = new ReponseMessage(200,"update Claim request success");
        return reponseMessage;
    }

    /**
     * Thực hiện cập nhật các giá trị cho ClaimRequest sau khi được analyze
     * @param claimRequest
     * @return
     */
    @Transactional
    public ReponseMessage updateClaimRequestAfterAnalyze(ClaimRequest claimRequest){
        Optional<ClaimRequest> claimRequestOptional
                = claimRequestRepository.findById(claimRequest.getId());
        if(claimRequestOptional.isPresent()){
            ClaimRequest claimRequestDB = claimRequestOptional.get();
            claimRequestDB.setHasAnalyzed(true);
            claimRequestDB.setAccidentId(claimRequest.getAccidentId());
            claimRequestDB.setHospitalId(claimRequest.getHospitalId());
            claimRequestDB.setReceiptAmount(claimRequest.getReceiptAmount());
            claimRequestDB.setDateOfReceipt(claimRequest.getDateOfReceipt());
            claimRequestDB.setName(claimRequest.getName());
            claimRequestDB.setValidReceipt(claimRequest.isValidReceipt());
            claimRequestRepository.saveAndFlush(claimRequestDB);
            esClaimRequestService.saveClaimRequestToES(claimRequestDB);
            ReponseMessage reponseMessage
                    = new ReponseMessage(200,"Analyze success");
            return reponseMessage;
        }
        else {
            throw new IndexOutOfBoundsException("Not found Claim request with id: "
                    + claimRequest.getId());
        }
    }

    /**
     * Thực hiện set giá trị payment cho ClaimRequest, true nếu đồng ý payment, flase nếu từ chối
     * @param id id của ClaimRequest được gửi đến.
     * @param payment
     * @return
     */
    public ReponseMessage paymentClaimRequest(String id, boolean payment){
        Optional<ClaimRequest> claimRequestOptional = claimRequestRepository.findById(id);

        String result;
        if(claimRequestOptional.isPresent()){
            ClaimRequest claimRequest = claimRequestOptional.get();
            claimRequest.setHasPayment(true);
            claimRequest.setPayment(payment);
            claimRequestRepository.saveAndFlush(claimRequest);
            ReponseMessage reponseMessage
                    = new ReponseMessage(200,"Payment success");
            return reponseMessage;
        }else{
            throw new IndexOutOfBoundsException("Dont found Claim request with id: "+id);
        }
    }

    /**
     * Xóa một Claim request khỏi hệ thống
     * @param id
     * @return
     */
    public ReponseMessage deleteClaimRequestById(String id){
        Optional<ClaimRequest> claimRequestOptional = claimRequestRepository.findById(id);
        if(claimRequestOptional.isPresent()){
            claimRequestRepository.delete(claimRequestOptional.get());
            rabbitTemplate.convertAndSend(RabbitMQConfig.CLAIMREQUEST_EXCHANGE,RabbitMQConfig.CLAIMREQUEST_DELETE,
                    id);
            ReponseMessage reponseMessage
                    = new ReponseMessage(200,"delete success");
            return reponseMessage;
        }else{
            throw new IndexOutOfBoundsException("Dont found Claim request with id: "+id);
        }
    }
}
