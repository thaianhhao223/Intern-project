package com.example.mock_project.listener;

import com.example.mock_project.config.RabbitMQConfig;
import com.example.mock_project.entity.ClaimRequest;
import com.example.mock_project.service.ESClaimRequestService;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@EnableRabbit
@Service
public class ClaimRequestRabbitListener {

    @Autowired
    private ESClaimRequestService esClaimRequestService;

    @RabbitListener(queues = {RabbitMQConfig.CLAIMREQUEST_CREATE_UPDATE})
    public void receiveClaimRequestCreate(ClaimRequest claimRequest) {
        esClaimRequestService.saveClaimRequestToES(claimRequest);
    }

    @RabbitListener(queues = {RabbitMQConfig.CLAIMREQUEST_DELETE})
    public void receiveClaimRequestDeleteById(String id) {
        esClaimRequestService.deleteById(id);
    }
}
