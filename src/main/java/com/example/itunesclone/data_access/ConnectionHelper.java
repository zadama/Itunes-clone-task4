package com.example.itunesclone.data_access;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionHelper {

    /*Setting up a connection object*/
    private String URL;
    private static ConnectionHelper instance;

    private ConnectionHelper() {
        URL = "jdbc:sqlite::resource:Chinook_Sqlite.sqlite";
    }

    public static Connection getConnection() throws SQLException {

        if (instance == null) {
            instance = new ConnectionHelper();
        }

        try {
            return DriverManager.getConnection(instance.URL);
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
