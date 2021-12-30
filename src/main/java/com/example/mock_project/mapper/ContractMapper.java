package com.example.mock_project.mapper;

import com.example.mock_project.dto.ContractDTO;
import com.example.mock_project.entity.Contract;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ContractMapper {
    ContractMapper MAPPER = Mappers.getMapper(ContractMapper.class);

    ContractDTO contractToContractDTO(Contract contract);
}
