package com.udacity.popularmovies.utilities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.udacity.popularmovies.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TmdbMoviesJson {
    List<Movie> results;

    public TmdbMoviesJson() {
        results = new ArrayList<Movie>();
    }

    public static List<Movie> parseJSON(String json) {
        final String KEY_RESULTS = "results";

        Gson gson = new GsonBuilder().create();

        List<Movie> movies = null;

        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray results = jsonObject.getJSONArray(KEY_RESULTS);

            movies = Arrays.asList(gson.fromJson(results.toString(), Movie[].class));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return movies;
    }

}
