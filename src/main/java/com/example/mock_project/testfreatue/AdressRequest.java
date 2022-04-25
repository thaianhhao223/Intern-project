package com.example.mock_project.testfreatue;

import lombok.Data;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.List;

@Data
public class AdressRequest {
    private int id;

    @UniqueElements
    private List<Address> addressList;
}
