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

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * These utilities will be used to work with The Movie Database API https://www.themoviedb.org/.
 */
public class TmdbUtils {


    // 1. Create an account, if needed. https://www.themoviedb.org/account/signup
    // 2. Generate an API key. https://www.themoviedb.org/settings/api
    // 3. Copy and paste your API key (v3 auth) below.
    private final static String API_KEY = "";

    private final static String BASE_URL = "https://api.themoviedb.org/3/movie/";
    private final static String IMAGE_URL = "https://image.tmdb.org/t/p/";
    private final static String POPULAR = "popular";
    private final static String TOP_RATED = "top_rated";
    private final static String PARAM_API_KEY = "api_key";

    // The desired image width (w92, w154, w185, w342, w500 or w780).
    private final static String IMAGE_WIDTH = "w342";

    /**
     * Builds the URL used to query The Movie Database.
     *
     * @param sortOrder The chosen sort order (popular or top_rated).
     * @return The URL used to query the The Movie Database.
     */
    private static URL buildUrl(String sortOrder) {
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
        return buildUrl(TOP_RATED);
    }

    /**
     * Returns the URL used to query The Movie Database sorted by most popular.
     *
     * @return The URL used to query the The Movie Database for most popular movies.
     */
    public static URL popularURL() {
        return buildUrl(POPULAR);
    }

    /**
     * Builds the URL used for The Movie Database movie poster images.
     *
     * @param imageName the name of the image as a String (with leading "/").
     * @return The URL used to query the The Movie Database.
     */
    public static URL buildImageUrl(String imageName) {
        Uri builtUri = Uri.parse(IMAGE_URL).buildUpon()
                .appendEncodedPath(IMAGE_WIDTH + imageName)
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
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}