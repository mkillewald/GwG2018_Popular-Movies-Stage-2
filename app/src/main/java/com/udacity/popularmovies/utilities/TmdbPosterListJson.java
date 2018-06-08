package com.udacity.popularmovies.utilities;

import com.google.gson.Gson;
import com.udacity.popularmovies.model.Poster;
import com.udacity.popularmovies.model.PosterList;

import java.util.ArrayList;
import java.util.List;

public class TmdbPosterListJson {
    List<Poster> mPosters;

    public TmdbPosterListJson() {
        mPosters = new ArrayList<>();
    }

    public static List<Poster> parse(String json) {
        Gson gson = new Gson();
        PosterList posterList = gson.fromJson(json, PosterList.class);

        return posterList.getResults();
    }
}
