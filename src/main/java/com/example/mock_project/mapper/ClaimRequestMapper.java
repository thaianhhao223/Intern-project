package com.example.mock_project.mapper;

import com.example.mock_project.dto.ClaimRequestBeforePaymentDTO;
import com.example.mock_project.dto.ClaimRequestForAnalyzeDTO;
import com.example.mock_project.dto.ClaimRequestPaymentDTO;
import com.example.mock_project.dto.CustomerForClaimRequestDTO;
import com.example.mock_project.entity.ClaimRequest;
import com.example.mock_project.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public interface ClaimRequestMapper {

    ClaimRequestMapper MAPPER = Mappers.getMapper(ClaimRequestMapper.class);

    ClaimRequestForAnalyzeDTO ClaimRequestToClaimRequestForAnalyze(ClaimRequest claimRequest);

    ClaimRequestPaymentDTO ClaimRequestToClaimRequestPaymentHistory(ClaimRequest claimRequest);

    CustomerForClaimRequestDTO CustomerToCustomerForClaimRequestDTO(Customer customer);

    @Mapping(source = "customer",
            target = "customerForClaimRequestDTO",
            qualifiedByName = "customCustomer")
    ClaimRequestBeforePaymentDTO ClaimRequestToClaimRequestBeforePayment(ClaimRequest claimRequest);

    @Named(value = "customCustomer")
    default CustomerForClaimRequestDTO customCustomerMapper(Customer customer){

        return CustomerToCustomerForClaimRequestDTO(customer);
    }
}
