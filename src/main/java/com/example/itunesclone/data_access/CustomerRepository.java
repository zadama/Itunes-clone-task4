package com.example.itunesclone.data_access;

import com.example.itunesclone.models.CustomersGroupedByCountry;
import com.example.itunesclone.models.Customer;
import com.example.itunesclone.models.GenreOfCustomer;
import com.example.itunesclone.models.BuyingCustomer;

import java.sql.*;
import java.util.ArrayList;

public class CustomerRepository {

    /*Set up a connection object as class property that all methods use*/
    private Connection conn = null;

    public ArrayList<Customer> getAllCustomers() {

        ArrayList<Customer> customers = new ArrayList<>();

        String sql = "SELECT * FROM Customer";

        try {
            conn = ConnectionHelper.getConnection();
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                customers.add(
                        new Customer(resultSet.getString(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getString(8),
                                resultSet.getString(9),
                                resultSet.getString(10),
                                resultSet.getString(12)));
            }
        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            ConnectionHelper.close(conn);
        }
        return customers;
    }

    /*Tries to add customer to database, if succeeded the method returns boolean = true,
    * otherwise returns false*/
    public Boolean addNewCustomer(Customer customer) {
        Boolean addedCustomer = false;
        String sql = "INSERT INTO customer(" +
                "FirstName, " +
                "LastName, " +
                "Country, " +
                "PostalCode, " +
                "PhoneNumber, " +
                "Email, " +
                "SupportRepId)" +
                " VALUES(?,?,?,?,?,?,?)";
        try {
            conn = ConnectionHelper.getConnection();
            PreparedStatement prep = conn.prepareStatement(sql);

            prep.setString(1, customer.getFirstName());
            prep.setString(2, customer.getLastName());
            prep.setString(3, customer.getCountry());
            prep.setString(4, customer.getPostalCode());
            prep.setString(5, customer.getPhoneNumber());
            prep.setString(6, customer.getEmail());
            prep.setInt(7, customer.generateSupportRepId());

            int result = prep.executeUpdate();
            addedCustomer = (result != 0);
        } catch (SQLException e) {
            addedCustomer = false;
            e.printStackTrace();

        } finally {
            ConnectionHelper.close(conn);
        }
        return addedCustomer;
    }

    /*Updating the customer object by customerId,
    if succeeded returns boolean = true, otherwise false is returned*/
    public Boolean updateCustomer(Customer customer) {
        Boolean updatedCustomer = false;
        String sql = "UPDATE customer SET " +
                " FirstName=?, " +
                " LastName=?, " +
                " Country=?, " +
                " PostalCode=?, " +
                " PhoneNumber=?, " +
                " Email=? " +
                "WHERE CustomerId=?";
        try {
            conn = ConnectionHelper.getConnection();
            PreparedStatement prep =
                    conn.prepareStatement(sql);
            prep.setString(1, customer.getFirstName());
            prep.setString(2, customer.getLastName());
            prep.setString(3, customer.getCountry());
            prep.setString(4, customer.getPostalCode());
            prep.setString(5, customer.getPhoneNumber());
            prep.setString(6, customer.getEmail());
            prep.setString(7, customer.getCustomerId());

            int result = prep.executeUpdate();
            updatedCustomer = (result != 0);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (Exception exception) {
                System.out.println(exception.toString());
            }
        }
        return updatedCustomer;
    }

    /*Get all customers by country and count how many customers in each country*/
    public ArrayList<CustomersGroupedByCountry> getCountOfCustomersByCountry() {
        var customersByCountry = new ArrayList<CustomersGroupedByCountry>();

        String sql = "SELECT Country, COUNT(*) as number_of_customers" +
                " FROM Customer " +
                " GROUP BY country " +
                " ORDER BY number_of_customers DESC";

        try {
            conn = ConnectionHelper.getConnection();
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                customersByCountry.add(new CustomersGroupedByCountry(
                        resultSet.getString("Country"),
                        resultSet.getString("number_of_customers")));
            }

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            ConnectionHelper.close(conn);
        }
        return customersByCountry;
    }

    /*Get customers with spent amount, saved in a list*/
    public ArrayList<BuyingCustomer> getCustomerBySpentAmount() {

        var spendingCustomer = new ArrayList<BuyingCustomer>();
        String sql = "SELECT " +
                "c.CustomerId, " +
                "c.FirstName," +
                "c.LastName," +
                "c.Country, " +
                "c.PostalCode, " +
                "c.PhoneNumber, " +
                "c.Email,round( SUM(I.Total), 2) as total from Customer c" +
                " JOIN Invoice I on c.CustomerId = I.CustomerId" +
                " GROUP BY c.customerId" +
                " ORDER BY total DESC";

        try {
            conn = ConnectionHelper.getConnection();
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                spendingCustomer.add(new BuyingCustomer(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getString(7),
                        resultSet.getDouble(8)));
            }

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            ConnectionHelper.close(conn);
        }
        return spendingCustomer;

    }

    /*With given customerId, a list of genres ordered by amount of tracks per genre.
    * Then filtered to retun the most popular genre of a customer, if tied return all tied*/
    public ArrayList<GenreOfCustomer> getPopularGenreOfCustomer(String customerId) {

        var genres = new ArrayList<GenreOfCustomer>();
        String sql = "SELECT " +
                " Customer.CustomerId, " +
                " Customer.FirstName, " +
                " Customer.LastName, " +
                " Genre.Name, " +
                " Count(Track.GenreId) total " +
                " FROM Customer" +
                " JOIN Invoice on Customer.CustomerId = Invoice.CustomerId" +
                " JOIN InvoiceLine ON Invoice.InvoiceId = InvoiceLine.InvoiceId" +
                " JOIN Track ON InvoiceLine.TrackId = Track.TrackId" +
                " JOIN Genre ON Track.GenreId = Genre.GenreId" +
                " WHERE Customer.CustomerId=?" +
                " GROUP BY Genre.GenreId" +
                " ORDER BY total DESC";

        try {
            conn = ConnectionHelper.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, customerId);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                genres.add(new GenreOfCustomer(
                        rs.getString("FirstName"),
                        rs.getString("LastName"),
                        rs.getString("Name"),
                        rs.getInt("total")));
            }

            genres = filterByMostPopularGenre(genres);

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            ConnectionHelper.close(conn);
        }
        return genres;
    }

    /*Filter the list of a customers' genres by the most popular*/
    private ArrayList<GenreOfCustomer> filterByMostPopularGenre(ArrayList<GenreOfCustomer> popularGenre) {
        ArrayList<GenreOfCustomer> mostPopularGenres = new ArrayList<GenreOfCustomer>();

        int maxValue = Integer.MIN_VALUE;

        for (GenreOfCustomer c : popularGenre) {
            if (c.getTotal() >= maxValue) {
                maxValue = c.getTotal();
            }
        }

        for (GenreOfCustomer c : popularGenre) {
            if (c.getTotal() == maxValue) {
                if (mostPopularGenres.size() > 0) {
                    GenreOfCustomer genreOfCustomer = mostPopularGenres.get(0);
                    genreOfCustomer.setGenreName(genreOfCustomer.getGenreName() + ", " + c.getGenreName());
                } else {
                    mostPopularGenres.add(c);
                }
            }
        }

        return mostPopularGenres;
    }

    public boolean checkIfCustomerExists(String customerId) {

        String sql = "SELECT FirstName FROM Customer WHERE CustomerId=?";
        boolean existingCustomer = false;
        try {
            conn = ConnectionHelper.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, customerId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                existingCustomer = true;
            } else {
                existingCustomer = false;
            }

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            ConnectionHelper.close(conn);
        }
        return existingCustomer;

    }

}
