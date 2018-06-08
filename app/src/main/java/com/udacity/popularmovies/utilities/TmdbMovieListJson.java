package com.udacity.popularmovies.utilities;

import com.google.gson.Gson;
import com.udacity.popularmovies.model.Movie;
import com.udacity.popularmovies.model.MovieList;

import java.util.ArrayList;
import java.util.List;

public class TmdbMovieListJson {
    List<Movie> movies;

    public TmdbMovieListJson() {
        movies = new ArrayList<>();
    }

    public static List<Movie> parse(String json) {
        Gson gson = new Gson();
        MovieList movieList = gson.fromJson(json, MovieList.class);

        return movieList.getResults();
    }
}
