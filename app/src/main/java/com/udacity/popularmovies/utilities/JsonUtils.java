package com.udacity.popularmovies.utilities;

import com.udacity.popularmovies.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtils {

    /**
     * Parses JSON received from The Move Database API https://themoviedb.org
     *
     * @param json The json string to be parsed
     * @return An array of Movie objects.
     */
    public static Movie[] parseTmdbJson(String json) {

        final String KEY_RESULTS = "results";
        final String KEY_TITLE = "title";
        final String KEY_ORIGINAL_TITLE = "original_title";
        final String KEY_POSTER_PATH = "poster_path";
        final String KEY_OVERVIEW = "overview";
        final String KEY_VOTE_AVERAGE = "vote_average";
        final String KEY_RELEASE_DATE = "release_date";
        final String KEY_BACKDROP_PATH = "backdrop_path";

        Movie[] movies = null;

        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray results = jsonObject.getJSONArray(KEY_RESULTS);

            movies = new Movie[results.length()];

            for (int i = 0; i < results.length(); i++) {
                Movie movie = new Movie();

                JSONObject movieJson = results.getJSONObject(i);

                movie.setTitle(movieJson.getString(KEY_TITLE));
                movie.setOriginalTitle(movieJson.getString(KEY_ORIGINAL_TITLE));
                movie.setPosterPath(movieJson.getString(KEY_POSTER_PATH));
                movie.setOverview(movieJson.getString(KEY_OVERVIEW));
                movie.setReleaseDate(movieJson.getString(KEY_RELEASE_DATE));
                movie.setVoteAverage(movieJson.getDouble(KEY_VOTE_AVERAGE));
                movie.setBackdropPath(movieJson.getString(KEY_BACKDROP_PATH));

                movies[i] = movie;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return movies;
    }
}
