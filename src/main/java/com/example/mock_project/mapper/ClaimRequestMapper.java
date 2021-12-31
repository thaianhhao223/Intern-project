package com.example.mock_project.mapper;

import com.example.mock_project.dto.ClaimRequestForAnalyzeDTO;
import com.example.mock_project.dto.ClaimRequestPaymentDTO;
import com.example.mock_project.entity.ClaimRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ClaimRequestMapper {
    ClaimRequestMapper MAPPER = Mappers.getMapper(ClaimRequestMapper.class);

    ClaimRequestForAnalyzeDTO ClaimRequestToClaimRequestForAnalyze(ClaimRequest claimRequest);

    ClaimRequestPaymentDTO ClaimRequestToClaimRequestPaymentHistory(ClaimRequest claimRequest);
}
