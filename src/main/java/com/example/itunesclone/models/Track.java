package com.example.itunesclone.models;

public class Track {
    private int id;
    private String name;
    private String composer;

    public Track(int id, String name, String composer) {
        this.id = id;
        this.name = name;
        this.composer = composer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComposer() {
        return composer;
    }

    public void setComposer(String composer) {
        this.composer = composer;
    }
}
