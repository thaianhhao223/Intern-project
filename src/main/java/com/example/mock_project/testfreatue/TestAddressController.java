package com.example.mock_project.testfreatue;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/test")
public class TestAddressController {

    @PostMapping("/upload")
    public String uploadAddress(@Valid @RequestBody AdressRequest adressRequest){
        System.out.println(adressRequest.toString());
        return "success";
    }
}
