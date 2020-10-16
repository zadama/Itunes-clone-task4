package com.example.itunesclone.models;

public class SearchResultData {
    private String trackName;
    private String artistName;
    private String albumName;
    private String genreName;

    public SearchResultData(String trackName, String artistName, String albumName, String genreName) {
        this.trackName = trackName;
        this.artistName = artistName;
        this.albumName = albumName;
        this.genreName = genreName;
    }

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }
}
