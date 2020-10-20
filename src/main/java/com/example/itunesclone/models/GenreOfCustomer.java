package com.example.itunesclone.models;

public class GenreOfCustomer {
    private String firstName;
    private String lastName;
    private String genreName;
    private int total;

    public GenreOfCustomer(String firstName, String lastName, String genreName, int total) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.genreName = genreName;
        this.total = total;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
