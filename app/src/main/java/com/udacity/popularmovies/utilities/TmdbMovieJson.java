package com.udacity.popularmovies.utilities;

import com.google.gson.Gson;
import com.udacity.popularmovies.model.Movie;

public class TmdbMovieJson {
    Movie movie;

    public TmdbMovieJson() {
        movie = new Movie();
    }

    public static Movie parse(String json) {
        Gson gson = new Gson();

        return gson.fromJson(json, Movie.class);
    }

}