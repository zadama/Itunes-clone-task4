package com.example.itunesclone.controllers.api;

import com.example.itunesclone.data_access.CustomerRepository;
import com.example.itunesclone.models.CustomersGroupedByCountry;
import com.example.itunesclone.models.Customer;
import com.example.itunesclone.models.GenreOfCustomer;
import com.example.itunesclone.models.BuyingCustomer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RequestMapping("/api")

@RestController
public class CustomerRestController {

    CustomerRepository customerRepository = new CustomerRepository();

    @RequestMapping(value = "/customers", method = RequestMethod.GET)
    public ArrayList<Customer> getAllCustomers() {
        return customerRepository.getAllCustomers();
    }

    @RequestMapping(value = "/customers", method = RequestMethod.POST)
    public ResponseEntity addNewCustomer(@RequestBody Customer customer) {
        if (customer.getEmail() == null || customer.getFirstName() == null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        boolean isCreateSuccessful = customerRepository.addNewCustomer(customer);

        return isCreateSuccessful ? new ResponseEntity(HttpStatus.CREATED) : new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(value = "/customers", method = RequestMethod.PUT)
    public ResponseEntity updateCustomer(@RequestBody Customer customer) {

        if (!customerRepository.checkIfCustomerExists(customer.getCustomerId())) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        boolean isUpdateSuccessful = customerRepository.updateCustomer(customer);

        return isUpdateSuccessful ? new ResponseEntity(HttpStatus.OK) : new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @RequestMapping(value = "/customers/countries/top", method = RequestMethod.GET)
    public ArrayList<CustomersGroupedByCountry> countCustomersByCountry() {
        return customerRepository.getCountOfCustomersByCountry();
    }

    @RequestMapping(value = "/customers/spendings/top", method = RequestMethod.GET)
    public ArrayList<BuyingCustomer> customersBySpentAmount() {
        return customerRepository.getCustomerBySpentAmount();
    }

    @RequestMapping(value = "/customers/{customerId}/popular/genre", method = RequestMethod.GET)
    public ResponseEntity<?> popularGenres(@PathVariable String customerId) {
        ArrayList<GenreOfCustomer> genreOfCustomers = customerRepository.getPopularGenreOfCustomer(customerId);

        if (!customerRepository.checkIfCustomerExists(customerId)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(genreOfCustomers, HttpStatus.OK);
    }
}
