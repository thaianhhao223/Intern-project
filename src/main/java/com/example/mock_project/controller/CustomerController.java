package com.example.mock_project.controller;

import com.example.mock_project.dto.CustomerDTO;
import com.example.mock_project.entity.Customer;
import com.example.mock_project.entity.ReponseMessage;
import com.example.mock_project.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/")
    public List<Customer> getAllCustomer(){
        return customerService.getAllCustomer();
    }
    @PostMapping("/")
    public ReponseMessage saveCustomer(@Valid @RequestBody CustomerDTO customerDTO){
        customerService.saveCustomer(customerDTO);
        return new ReponseMessage(200,"Save a new Customer succes");
    }
}
