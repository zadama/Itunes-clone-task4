package com.example.itunesclone.models;

public class CustomersGroupedByCountry {
    private String country;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getNumber_of_customers() {
        return number_of_customers;
    }

    public void setNumber_of_customers(String number_of_customers) {
        this.number_of_customers = number_of_customers;
    }

    private String number_of_customers;


    public CustomersGroupedByCountry(String country, String number_of_customers) {
        this.country = country;
        this.number_of_customers = number_of_customers;
    }
}
