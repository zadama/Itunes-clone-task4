# REST API example application
This is a working prototype with a simple UI built in Thymeleaf with the rest of the application being exposed as a regular REST API to be publicly consumed.
The web application makes use of Spring Boot and Spring Initializr. 

The app is hosted on Heroku at https://task5-copyright-strike.herokuapp.com/


## API-docs

## Get all customers

`GET /api/customers`

### Example response

    Status: 200 OK
    [ { "customerId": 6, "firstName": "Helena", "lastName": "Holý", "country": "Czech Republic", "postalCode": "14300", "phoneNumber": "+420 2 4177 0449", "email": "hholy@gmail.com", "totalAmount": 49.62 }]


## Create a new customer

`POST /api/customers`

### Example response

    Status: 201 Created  


## Update a customer

`PUT /api/customers`

### Example response

    Status: 200 OK


## Number of customers in each country

`GET /api/customers/countries/top`

### Example response

    Status: 200 OK
    [ { "country": "USA", "number_of_customers": "13" }]


## Customers who are the highest spenders

`GET /api/customers/spendendings/top`

### Example response

    Status: 200 OK
    [ { "customerId": 6, "firstName": "Helena", "lastName": "Holý", "country": "Czech Republic", "postalCode": "14300", "phoneNumber": "+420 2 4177 0449", "email": "hholy@gmail.com", "totalAmount": 49.62 }]

## Get for a given customer, their most popular genre

`GET /api/customers/{customerId}/popular/genre`

### Example response
    Status: 200 OK
    [{ "firstName":"Luis","lastName":"Goncalves","genreName":"Rock",            "total":"14"}]

## Authors

[Paria Karim](https://github.com/lillap) and [Aman Zadran](https://github.com/zadama).
