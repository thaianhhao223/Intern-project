package com.example.mock_project.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.Column;
import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class ContractDTO {

    @JsonProperty("customerId")
    private String customerId;
    @JsonProperty("validFrom")
    private Date validFrom;
    @JsonProperty("validTo")
    private Date validTo;
    @JsonProperty("maximumAmount")
    private double maximumAmount;
    @JsonProperty("remainingAmount")
    private double remainingAmount;
    @JsonProperty("acceptableHospitals")
    private String acceptableHospitals;
    @JsonProperty("acceptableAccidents")
    private String acceptableAccidents;
}
