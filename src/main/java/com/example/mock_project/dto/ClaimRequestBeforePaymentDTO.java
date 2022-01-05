package com.example.mock_project.dto;

import com.example.mock_project.entity.Customer;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.Column;
import java.sql.Date;

/**
 * Chỉ bao gồm các thông tin cơ bản cho quá trình xét duyệt approve hay reject
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class ClaimRequestBeforePaymentDTO {
    private String id;
    @Column(name = "customer")
    private CustomerForClaimRequestDTO customerForClaimRequestDTO;
    private double receiptAmount;
    private Date dateOfReceipt;
}
