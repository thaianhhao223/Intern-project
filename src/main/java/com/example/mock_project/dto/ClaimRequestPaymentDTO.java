package com.example.mock_project.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

/**
 *  Dùng để trả về một ClaimRequestDTO cho việc xem lịch sử payment trong một tháng, bao gồm:
 *  id
 *  danh sách các reciept
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class ClaimRequestPaymentDTO {
    private String id;
    private double receiptAmount;
}
