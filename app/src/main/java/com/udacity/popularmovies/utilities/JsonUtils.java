package com.udacity.popularmovies.utilities;

import com.udacity.popularmovies.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {

    public static Movie[] parseTmdbJson(String json) {

        final String KEY_RESULTS = "results";
        final String KEY_TITLE = "title";
        final String KEY_ORIGINAL_TITLE = "original_title";
        final String KEY_POSTER_PATH = "poster_path";
        final String KEY_OVERVIEW = "overview";
        final String KEY_VOTE_AVERAGE = "vote_average";
        final String KEY_RELEASE_DATE = "release_date";

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

                movies[i] = movie;
            }


//            JSONArray alsoKnownAsArray = name.getJSONArray(KEY_ALSO_KNOWN_AS);
//            ArrayList<String> alsoKnownAsList = new ArrayList<String>();
//            if (alsoKnownAsArray != null) {
//                for (int i = 0; i < alsoKnownAsArray.length(); i++) {
//                    alsoKnownAsList.add(alsoKnownAsArray.getString(i));
//                }
//            }
//
//            JSONArray ingredientsArray = jsonObject.getJSONArray(KEY_INGREDIENTS);
//            ArrayList<String> ingredientsList = new ArrayList<String>();
//            if (ingredientsArray != null) {
//                for (int i = 0; i < ingredientsArray.length(); i++) {
//                    ingredientsList.add(ingredientsArray.getString(i));
//                }
//            }
//
//            movie.setMainName(name.getString(KEY_MAIN_NAME));
//            movie.setAlsoKnownAs(alsoKnownAsList);
//            movie.setPlaceOfOrigin(jsonObject.getString(KEY_PLACE_OF_ORIGIN));
//            movie.setDescription(jsonObject.getString(KEY_DESCRIPTION));
//            movie.setPosterPath(jsonObject.getString(KEY_IMAGE));
//            movie.setIngredients(ingredientsList);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return movies;
    }
}
