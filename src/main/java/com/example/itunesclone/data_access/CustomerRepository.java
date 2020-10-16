package com.example.itunesclone.data_access;

import com.example.itunesclone.models.Customer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CustomerRepository {

    public static ArrayList<Customer> getAllCustomers() {

        ArrayList<Customer> allCustomers = new ArrayList<>();

        Connection con = null;
        String sql = "SELECT * FROM Employee";

        try {
            con = ConnectionHelper.getConnection();
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                allCustomers.add(
                        new Customer(rs.getString(1), rs.getString(2),
                                rs.getString(3),
                                rs.getString(8), rs.getString(9),
                                rs.getString(10)));

            }

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            ConnectionHelper.close(con);
        }
        return allCustomers;
    }
}
