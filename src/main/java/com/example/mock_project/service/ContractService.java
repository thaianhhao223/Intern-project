package com.example.mock_project.service;

import com.example.mock_project.dto.ContractDTO;
import com.example.mock_project.entity.Contract;
import com.example.mock_project.mapper.ContractMapper;
import com.example.mock_project.repository.ContractRepository;
import jdk.nashorn.internal.runtime.options.Option;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContractService {
    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private ContractMapper contractMapper;
    public List<Contract> getAll(){
        return contractRepository.findAll();
    }
    public List<ContractDTO> getAllDTO(){
        return contractRepository.findAll().stream().
                map(contractMapper::contractToContractDTO).collect(Collectors.toList());
    }
    public Contract getContractById(String id){
        Optional<Contract> contractOption = contractRepository.findById(id);
        if (contractOption.isPresent()){
            return contractOption.get();
        }
        else {
            throw new IndexOutOfBoundsException("Not found Contract with id: "+ id);
        }
    }
    public Contract saveANewContract(Contract contract){
        contractRepository.save(contract);
        return contract;
    }
    public Contract updateContract(Contract contract){
        contractRepository.save(contract);
        return contract;
    }
    public void deleteContract(Contract contract){
        contractRepository.delete(contract);
    }
}
