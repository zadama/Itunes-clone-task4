package com.example.itunesclone.data_access;

import com.example.itunesclone.models.Artist;
import com.example.itunesclone.models.Genre;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GenreRepository {

    public static ArrayList<Genre> getRandomGenres(int amount){

        var genres = new ArrayList<Genre>();
        Connection con = null;
        String sql = "SELECT GenreId, Name FROM Genre ORDER BY RANDOM() LIMIT ?";

        try {
            con = ConnectionHelper.getConnection();
            PreparedStatement s = con.prepareStatement(sql);
            s.setInt(1,amount);

            ResultSet rs = s.executeQuery();

            while (rs.next()) {
                genres.add(new Genre(rs.getInt("GenreId"),rs.getString("Name")));
            }

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            ConnectionHelper.close(con);
        }

        return genres;
    }
}
