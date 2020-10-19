package com.example.itunesclone.controllers.api;

import com.example.itunesclone.data_access.CustomerRepository;
import com.example.itunesclone.models.CountCustomerInCountry;
import com.example.itunesclone.models.Customer;
import com.example.itunesclone.models.CustomerPopularGenre;
import com.example.itunesclone.models.SpendingCustomer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(value = "/customers/spendings/top", method = RequestMethod.GET)
    public ArrayList<SpendingCustomer> customersBySpentAmount() {
        return customerRepository.getCustomerBySpentAmount();
    }

    @RequestMapping(value = "/customers/{customerId}/popular/genre", method = RequestMethod.GET)
    public ArrayList<CustomerPopularGenre> popularGenres(@PathVariable String customerId) {
        return customerRepository.getPopularGenre(customerId);
    }
}
