package com.example.mock_project.mapper;

import com.example.mock_project.dto.ContractDTO;
import com.example.mock_project.dto.CustomerDTO;
import com.example.mock_project.dto.CustomerForClaimRequestDTO;
import com.example.mock_project.entity.Contract;
import com.example.mock_project.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerMapper MAPPER = Mappers.getMapper(CustomerMapper.class);

    CustomerDTO customerToCustomerDTO(Customer customer);

    CustomerForClaimRequestDTO CustomerToCustomerForClaimRequestDTO(Customer customer);
}
