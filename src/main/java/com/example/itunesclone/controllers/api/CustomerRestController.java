package com.example.itunesclone.controllers.api;

import com.example.itunesclone.data_access.CustomerRepository;
import com.example.itunesclone.models.CountCustomerInCountry;
import com.example.itunesclone.models.Customer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RequestMapping("/api")
@RestController
public class CustomerRestController {
    CustomerRepository customerRepository = new CustomerRepository();

    // om ej funkar, ta bort entity grejen helt o returnera listan bara
    @RequestMapping(value = "/customers", method = RequestMethod.GET)
    public ResponseEntity<?> getAllCustomers(){

        return new ResponseEntity<>(customerRepository.getAllCustomers(),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/customers", method = RequestMethod.POST)
    public Boolean addNewCustomer(@RequestBody Customer customer){
        return customerRepository.addNewCustomer(customer);
    }

    @RequestMapping(value = "/customers", method = RequestMethod.PUT)
    public Boolean updateCustomer(@RequestBody Customer customer){
        return customerRepository.updateCustomer(customer);
    }

    @RequestMapping(value = "/customers/countries/top", method = RequestMethod.GET)
    public ArrayList<CountCustomerInCountry> countCustomersByCountry(){
        return customerRepository.getCountOfCustomersByCountry();
    }
}
