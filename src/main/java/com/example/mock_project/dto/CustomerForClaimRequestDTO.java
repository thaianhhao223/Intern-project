package com.example.mock_project.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.sql.Date;

/**
 * Chỉ bao gồm các thông tin cơ bản, và phương thức liên lạc
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class CustomerForClaimRequestDTO {

    private String name;
    private String gender;
    private String phone;
    private String email;
    private Date dateOfBirth;
    private String address;
}
