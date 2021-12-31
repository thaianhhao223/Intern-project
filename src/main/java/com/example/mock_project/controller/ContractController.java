package com.example.mock_project.controller;

import com.example.mock_project.dto.ContractDTO;
import com.example.mock_project.entity.Contract;
import com.example.mock_project.repository.ContractRepository;
import com.example.mock_project.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contracts")
public class ContractController {

    @Autowired
    ContractRepository contractRepository;

    @Autowired
    private ContractService contractService;

    @GetMapping("/")
    public List<ContractDTO> getAllContract(){
        return contractService.getAllDTO();
    }

    @GetMapping("/byId")
    public Contract getContractById(@RequestParam(name = "id_contract", value = "id_contract") String id){
        return contractService.getContractById(id);
    }

    @PostMapping("/")
    public Contract saveContract(@RequestBody Contract contract){
        return contractService.saveANewContract(contract);
    }

    @PutMapping("/")
    public Contract updateContract(@RequestBody Contract contract){
        return contractService.updateContract(contract);
    }

    @DeleteMapping("/")
    public void deleteContract(@RequestBody Contract contract){
        contractService.deleteContract(contract);
    }
}
