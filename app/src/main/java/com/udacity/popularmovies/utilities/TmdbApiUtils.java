/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.udacity.popularmovies.utilities;

import android.net.Uri;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * These utilities will be used to work with The Movie Database API https://www.themoviedb.org/.
 */
public class TmdbApiUtils {

    // 1. Create an account, if needed. https://www.themoviedb.org/account/signup
    // 2. Generate an API key. https://www.themoviedb.org/settings/api
    // 3. Copy and paste your API key (v3 auth) below.
    private final static String API_KEY = "";

    private final static String BASE_URL = "https://api.themoviedb.org/3/movie/";
    private final static String IMAGE_URL = "https://image.tmdb.org/t/p/";
    private final static String POPULAR = "popular";
    private final static String TOP_RATED = "top_rated";
    private final static String VIDEOS = "videos";
    private final static String REVIEWS = "reviews";
    private final static String PARAM_API_KEY = "api_key";

    /**
     * Builds the URL used to query The Movie Database.
     *
     * @param sortOrder The chosen sort order (popular or top_rated).
     * @return The URL used to query the The Movie Database.
     */
    private static URL buildListUrl(String sortOrder) {
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendEncodedPath(sortOrder)
                .appendQueryParameter(PARAM_API_KEY, API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    /**
     * Returns the URL used to query The Movie Database sorted by top rated.
     *
     * @return The URL used to query the The Movie Database for top rated movies.
     */
    public static URL topRatedUrl() {
        return buildListUrl(TOP_RATED);
    }

    /**
     * Returns the URL used to query The Movie Database sorted by most popular.
     *
     * @return The URL used to query the The Movie Database for most popular movies.
     */
    public static URL popularUrl() {
        return buildListUrl(POPULAR);
    }

    /**
     * Builds the URL used for The Movie Database images.
     *
     * @param imageWidth the desired width of the image (w92, w154, w185, w342, w500 or w780)
     * @param imageName the name of the image as a String (with leading "/").
     * @return The URL used to query the The Movie Database.
     */
    public static URL buildImageUrl(String imageWidth, String imageName) {
        Uri builtUri = Uri.parse(IMAGE_URL).buildUpon()
                .appendEncodedPath(imageWidth + imageName)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    /**
     * Builds the URL used to query The Movie Database for a single movie.
     *
     * @param id the id of the desired movie
     * @return The URL used to query the The Movie Database.
     */
    public static URL buildMovieUrl(int id) {
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendEncodedPath(Integer.toString(id))
                .appendQueryParameter(PARAM_API_KEY, API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }
    /**
     * Builds the URL used to query The Movie Database for a single movie's list of videos.
     *
     * @param id the id of the desired movie
     * @return The URL used to query the The Movie Database.
     */
    public static URL buildVideosUrl(int id) {
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendEncodedPath(Integer.toString(id))
                .appendEncodedPath(VIDEOS)
                .appendQueryParameter(PARAM_API_KEY, API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    /**
     * Builds the URL used to query The Movie Database for a single movie's list of reviews.
     *
     * @param id the id of the desired movie
     * @return The URL used to query the The Movie Database.
     */
    public static URL buildReviewsUrl(int id) {
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendEncodedPath(Integer.toString(id))
                .appendEncodedPath(REVIEWS)
                .appendQueryParameter(PARAM_API_KEY, API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

}