package com.udacity.popularmovies.model;

import com.udacity.popularmovies.utilities.TmdbApiUtils;

public class Poster {

    // Set the desired image width here (w92, w154, w185, w342, w500 or w780).
    private final static String POSTER_WIDTH = "w342";

    private int id;
    private String poster_path;

    public Poster(int id, String poster_path) {
        this.id = id;
        this.poster_path = poster_path;
    }

    public int getId() {
        return id;
    }

    public String getUrl() {
        return TmdbApiUtils.buildImageUrl(POSTER_WIDTH, poster_path).toString();
    }
}
