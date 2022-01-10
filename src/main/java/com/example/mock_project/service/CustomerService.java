package com.example.mock_project.service;

import com.example.mock_project.dto.CustomerDTO;
import com.example.mock_project.entity.Customer;
import com.example.mock_project.mapper.CustomerMapper;
import com.example.mock_project.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerMapper customerMapper;

    public List<Customer> getAllCustomer(){
       return customerRepository.findAll();
    }

    public List<CustomerDTO> getAllCustomerDTO(){
        return customerRepository.findAll().stream().
                map(customerMapper::customerToCustomerDTO).collect(Collectors.toList());
    }
    public Customer findCustomerById(Long id){
        return customerRepository.findById(id).orElse(null);
    }
    public List<Customer> findCustomerByCardIdAndName(String name, String card_id){
        return customerRepository.findByNameAndCardId(name,card_id);
    }
    public CustomerDTO getAllCustomerDTO(Long id){
        return customerRepository.findById(id).map(customerMapper::customerToCustomerDTO)
                .get();
    }
    public Customer saveCustomer(CustomerDTO customerDTO){
        Customer customer = customerMapper.customerDTOToCustomer(customerDTO);
        customerRepository.save(customer);
        return customer;
    }
    public Customer updateCustomer(Customer customer){
        customerRepository.save(customer);
        return customer;
    }
    public void deleteCustomer(Customer customer){
        customerRepository.delete(customer);
    }
}
