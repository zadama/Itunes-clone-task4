package com.example.itunesclone.data_access;

import com.example.itunesclone.models.Customer;
import com.example.itunesclone.models.SearchResultData;

import java.sql.*;
import java.util.ArrayList;

public class SearchRepository {

    public static ArrayList<SearchResultData> getSearchedResult(String query) {
        var resultData = new ArrayList<SearchResultData>();

        Connection con = null;
        // Our query below, ... WHERE trackname LIKE (%query%)
        String sql = "SELECT Track.Name as track, Artist.Name as artist, Album.Title as album,Genre.Name as genre";
        sql += " from Track";
        sql += " JOIN Album on Track.AlbumId=Album.AlbumId";
        sql += " JOIN Artist on Artist.ArtistId=Album.ArtistId";
        sql += " JOIN Genre on Genre.GenreId=Track.GenreId";
        sql += " WHERE UPPER(Track.Name) LIKE UPPER(?)";

        System.out.println(sql);
        try {
            con = ConnectionHelper.getConnection();

            PreparedStatement s = con.prepareStatement(sql);
            s.setString(1, query);

            ResultSet rs = s.executeQuery();
            while (rs.next()) {

                resultData.add(
                        new SearchResultData(rs.getString("track"), rs.getString("artist"), rs.getString("album"), rs.getString("genre")));

            }

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            ConnectionHelper.close(con);
        }

        return resultData;
    }
}
