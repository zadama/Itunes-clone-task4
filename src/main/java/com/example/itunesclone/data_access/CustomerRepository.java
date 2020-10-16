package com.example.itunesclone.data_access;

import com.example.itunesclone.models.Customer;

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
            Statement s = conn.createStatement();
            ResultSet rs = s.executeQuery(sql);
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

    public Boolean addNewCustomer(Customer customer){
        Boolean addedCustomer = false;
        String sql = "INSERT INTO customer(FirstName, LastName, Country, PostalCode, PhoneNumber, Email)" +
                "VALUES(?,?,?,?,?,?)";
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

            int result = prep.executeUpdate();
            addedCustomer = (result != 0);
        } catch (SQLException e){
            e.printStackTrace();

        } finally {
            ConnectionHelper.close(conn);
        }
        return addedCustomer;
    }
}
