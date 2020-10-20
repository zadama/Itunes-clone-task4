package com.example.itunesclone.data_access;

import com.example.itunesclone.models.CountCustomerInCountry;
import com.example.itunesclone.models.Customer;
import com.example.itunesclone.models.CustomerPopularGenre;
import com.example.itunesclone.models.SpendingCustomer;

import java.sql.*;
import java.util.ArrayList;

public class CustomerRepository {
    /*Set up a connection object - skapade klassvariabel pga flera metoder*/
    private Connection conn = null;

    public ArrayList<Customer> getAllCustomers() {

        ArrayList<Customer> customers = new ArrayList<>();

        String sql = "SELECT * FROM Customer";

        try {
            conn = ConnectionHelper.getConnection();
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                customers.add(
                        new Customer(rs.getString(1), rs.getString(2),
                                rs.getString(3),
                                rs.getString(8), rs.getString(9),
                                rs.getString(10), rs.getString(12)));

            }

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            ConnectionHelper.close(conn);
        }
        return customers;
    }

    /*TODO: validera genom att kolla ifall befintlig kund redan finns via email*/
    public Boolean addNewCustomer(Customer customer) {
        Boolean addedCustomer = false;
        String sql = "INSERT INTO customer(FirstName, LastName, Country, PostalCode, PhoneNumber, Email, SupportRepId)" +
                "VALUES(?,?,?,?,?,?,?)";
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

    public Boolean updateCustomer(Customer customer) {
        Boolean updatedCustomer = false;
        String sql = "UPDATE customer SET FirstName=?, LastName=?, Country=?, PostalCode=?, PhoneNumber=?, Email=? WHERE CustomerId=?";
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
            updatedCustomer = (result != 0); // if res = 1; true


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

    public ArrayList<CountCustomerInCountry> getCountOfCustomersByCountry() {
        var customersByCountry = new ArrayList<CountCustomerInCountry>();

        String sql = "SELECT Country, COUNT(*) as number_of_customers FROM Customer GROUP BY country ORDER BY number_of_customers DESC";

        try {
            conn = ConnectionHelper.getConnection();
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                customersByCountry.add(new CountCustomerInCountry(rs.getString("Country"), rs.getString("number_of_customers")));
            }

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            ConnectionHelper.close(conn);
        }
        return customersByCountry;
    }

    public ArrayList<SpendingCustomer> getCustomerBySpentAmount() {

        var spendingCustomer = new ArrayList<SpendingCustomer>();
        String sql = "SELECT c.CustomerId, c.FirstName," +
                "       c.LastName,c.Country, " +
                "       c.PostalCode,c.PhoneNumber," +
                "       c.Email,round( SUM(I.Total), 2) as total from Customer c" +
                "                                 JOIN Invoice I on c.CustomerId = I.CustomerId" +
                "       GROUP BY c.customerId" +
                "       ORDER BY total DESC";

        try {
            conn = ConnectionHelper.getConnection();
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                spendingCustomer.add(new SpendingCustomer(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getDouble(8)));
            }

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            ConnectionHelper.close(conn);
        }
        return spendingCustomer;

    }

    public ArrayList<CustomerPopularGenre> getPopularGenre(String customerId) {

        var popularGenre = new ArrayList<CustomerPopularGenre>();
        String sql = "SELECT Customer.CustomerId, Customer.FirstName, Customer.LastName, Genre.Name, Count(Track.GenreId) total" +
                " from Customer" +
                "         JOIN Invoice on Customer.CustomerId = Invoice.CustomerId" +
                "         JOIN InvoiceLine ON Invoice.InvoiceId = InvoiceLine.InvoiceId" +
                "         JOIN Track ON InvoiceLine.TrackId = Track.TrackId" +
                "         JOIN Genre ON Track.GenreId = Genre.GenreId" +
                " WHERE Customer.CustomerId=?" +
                " GROUP BY Genre.GenreId" +
                " ORDER BY total DESC";

        try {
            conn = ConnectionHelper.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1,customerId);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                popularGenre.add(new CustomerPopularGenre(rs.getString("FirstName"), rs.getString("LastName"), rs.getString("Name"), rs.getInt("total")));
            }

            popularGenre = filterPopularGenres(popularGenre);


        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            ConnectionHelper.close(conn);
        }
        return popularGenre;
    }

    private ArrayList<CustomerPopularGenre> filterPopularGenres(ArrayList<CustomerPopularGenre> popularGenre) {
        ArrayList<CustomerPopularGenre> filteredList = new ArrayList<CustomerPopularGenre>();

        int maxValue = Integer.MIN_VALUE;

        for (CustomerPopularGenre c : popularGenre) {
            if (c.getTotal() >= maxValue) {
                maxValue = c.getTotal();
            }
        }

        for (CustomerPopularGenre c : popularGenre) {
            if (c.getTotal() == maxValue) {
              if(filteredList.size()>0){
                  CustomerPopularGenre customerPopularGenre = filteredList.get(0);
                    customerPopularGenre.setGenreName(customerPopularGenre.getGenreName() + ", " + c.getGenreName());
              } else{
                  filteredList.add(c);
              }
            }
        }

        return filteredList;
    }

    public boolean customerExists(String customerId){

        String sql = "SELECT FirstName FROM Customer WHERE CustomerId=?";
        boolean isAvailable = false;
        try {
            conn = ConnectionHelper.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1,customerId);
            ResultSet rs = statement.executeQuery();
            if(rs.next()){
                isAvailable = true;
            }else{
                isAvailable = false;
            }

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            ConnectionHelper.close(conn);
        }
        return isAvailable;

    }

}
