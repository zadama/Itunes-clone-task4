package com.example.itunesclone.controllers.api;

import com.example.itunesclone.data_access.CustomerRepository;
import com.example.itunesclone.models.Customer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RequestMapping("/api")
@RestController
public class CustomerRestController {

    // om ej funkar, ta bort entity grejen helt o returnera listan bara
    @RequestMapping(value = "/customers", method = RequestMethod.GET)
    public ResponseEntity<?> getAllCustomers(){

        return new ResponseEntity<>(CustomerRepository.getAllCustomers(),
                HttpStatus.OK);
    }
}
