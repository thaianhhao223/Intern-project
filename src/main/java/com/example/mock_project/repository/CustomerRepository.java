package com.example.mock_project.repository;

import com.example.mock_project.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    // Querry Method
    List<Customer> findByNameAndCardId(String name, String cardId);
}
