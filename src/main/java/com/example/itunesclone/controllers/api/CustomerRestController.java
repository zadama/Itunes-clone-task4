package com.example.itunesclone.controllers.api;

import com.example.itunesclone.data_access.CustomerRepository;
import com.example.itunesclone.models.CountCustomerInCountry;
import com.example.itunesclone.models.Customer;
import com.example.itunesclone.models.CustomerPopularGenre;
import com.example.itunesclone.models.SpendingCustomer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.ArrayList;

@RequestMapping("/api")
@RestController
public class CustomerRestController {
    CustomerRepository customerRepository = new CustomerRepository();

    // om ej funkar, ta bort entity grejen helt o returnera listan bara
    @RequestMapping(value = "/customers", method = RequestMethod.GET)
    public ArrayList<Customer> getAllCustomers() {
        return customerRepository.getAllCustomers();
    }

    @RequestMapping(value = "/customers", method = RequestMethod.POST)
    public ResponseEntity addNewCustomer(@RequestBody Customer customer) {
        if (customer.getEmail() == null || customer.getFirstName() == null) {

            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        boolean isCreated = customerRepository.addNewCustomer(customer);

        return isCreated ? new ResponseEntity(HttpStatus.CREATED) : new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(value = "/customers", method = RequestMethod.PUT)
    public ResponseEntity updateCustomer(@RequestBody Customer customer) {

        if (!customerRepository.customerExists(customer.getCustomerId())) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        boolean isUpdated = customerRepository.updateCustomer(customer);

        return isUpdated ? new ResponseEntity(HttpStatus.OK) : new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @RequestMapping(value = "/customers/countries/top", method = RequestMethod.GET)
    public ArrayList<CountCustomerInCountry> countCustomersByCountry() {
        return customerRepository.getCountOfCustomersByCountry();
    }

    @RequestMapping(value = "/customers/spendings/top", method = RequestMethod.GET)
    public ArrayList<SpendingCustomer> customersBySpentAmount() {
        return customerRepository.getCustomerBySpentAmount();
    }

    @RequestMapping(value = "/customers/{customerId}/popular/genre", method = RequestMethod.GET)
    public ResponseEntity<?> popularGenres(@PathVariable String customerId) {
        ArrayList<CustomerPopularGenre> customerPopularGenres = customerRepository.getPopularGenre(customerId);

        if (!customerRepository.customerExists(customerId)){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(customerPopularGenres,HttpStatus.OK);
    }
}
