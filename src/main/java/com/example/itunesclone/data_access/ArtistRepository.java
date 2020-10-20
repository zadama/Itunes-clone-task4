package com.example.itunesclone.data_access;

import com.example.itunesclone.models.Artist;

import java.sql.*;
import java.util.ArrayList;

public class ArtistRepository {

    Connection conn = null;

    public ArrayList<Artist> getRandomArtists(int numberOfArtists) {

        var artists = new ArrayList<Artist>();

        String sql = "SELECT ArtistId, Name FROM Artist ORDER BY RANDOM() LIMIT ?";

        try {
            conn = ConnectionHelper.getConnection();
            PreparedStatement s = conn.prepareStatement(sql);
            s.setInt(1, numberOfArtists);

            ResultSet resultSet = s.executeQuery();

            while (resultSet.next()) {
                artists.add(new Artist(resultSet.getInt("ArtistId"), resultSet.getString("Name")));
            }

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            ConnectionHelper.close(conn);
        }

        return artists;
    }
}
