package com.example.itunesclone.data_access;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionHelper {

    /*Setting up a connection object*/
    private String URL;
    private static ConnectionHelper connectionHelper;

    /*Singleton pattern with private constructor*/
    private ConnectionHelper() {
        URL = "jdbc:sqlite::resource:Chinook_Sqlite.sqlite";
    }

    /*Static method that returns a connection based on the URL*/
    public static Connection getConnection() throws SQLException {

        if (connectionHelper == null) {
            connectionHelper = new ConnectionHelper();
        }

        try {
            return DriverManager.getConnection(connectionHelper.URL);
        } catch (SQLException ex) {
            System.out.println("Exception thrown! Could not connect to database...");
            throw ex;
        }
    }

    public static void close(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
