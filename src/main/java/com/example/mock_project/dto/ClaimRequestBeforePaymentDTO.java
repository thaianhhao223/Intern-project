package com.example.mock_project.dto;

import com.example.mock_project.entity.Customer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonKey;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.boot.context.properties.bind.Name;

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

    @JsonProperty("customer")
    private CustomerForClaimRequestDTO customerForClaimRequestDTO;
    @JsonProperty("amount")
    private double receiptAmount;
    private Date dateOfReceipt;
}
