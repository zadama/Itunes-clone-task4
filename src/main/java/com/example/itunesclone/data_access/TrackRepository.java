package com.example.itunesclone.data_access;

import com.example.itunesclone.models.Artist;
import com.example.itunesclone.models.Track;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TrackRepository {

    public static ArrayList<Track> getRandomTracks(int amount){

        var tracks = new ArrayList<Track>();
        Connection con = null;
        String sql = "SELECT TrackId, Name,Composer FROM Track ORDER BY RANDOM() LIMIT ?";

        try {
            con = ConnectionHelper.getConnection();
            PreparedStatement s = con.prepareStatement(sql);
            s.setInt(1,amount);

            ResultSet rs = s.executeQuery();

            while (rs.next()) {
                tracks.add(new Track(rs.getInt("TrackId"),rs.getString("Name"),rs.getString("composer")));
            }

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            ConnectionHelper.close(con);
        }

        return tracks;
    }
}
