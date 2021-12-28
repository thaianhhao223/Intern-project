package com.example.mock_project.service;

import com.example.mock_project.entity.Contract;
import com.example.mock_project.repository.ContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContractService {
    @Autowired
    private ContractRepository contractRepository;

    public List<Contract> getAll(){
        return contractRepository.findAll();
    }
    public Contract getContractById(String id){
        return contractRepository.findById(id).orElse(null);
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
