package com.example.itunesclone.data_access;

import com.example.itunesclone.models.Artist;
import com.example.itunesclone.models.Customer;

import java.sql.*;
import java.util.ArrayList;

public class ArtistRepository {

    public static ArrayList<Artist> getRandomArtists(int amount){

        var artists = new ArrayList<Artist>();
        Connection con = null;
        String sql = "SELECT ArtistId, Name FROM Artist ORDER BY RANDOM() LIMIT ?";

        try {
            con = ConnectionHelper.getConnection();
            PreparedStatement s = con.prepareStatement(sql);
            s.setInt(1,amount);

            ResultSet rs = s.executeQuery();

            while (rs.next()) {
                artists.add(new Artist(rs.getInt("ArtistId"),rs.getString("Name")));
            }

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            ConnectionHelper.close(con);
        }

        return artists;
    }
}
