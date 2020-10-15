package com.example.itunesclone.controllers;

import com.example.itunesclone.data_access.ConnectionHelper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

// Kommer tas bort!!

@RestController
public class Test {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index (){

        List<String> list = new ArrayList<String>();
        Connection c = null;
        String sql = "SELECT * FROM Employee";
        try {
            c = ConnectionHelper.getConnection();
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                list.add(rs.getString(2) + "\n");
                list.add(rs.getString(3) + "\n");
                list.add(rs.getString(4) + "\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            ConnectionHelper.close(c);
        }

        String res = "";
        for(String customer : list){
            res+=customer + "";
        }

        return res;
    }

}
