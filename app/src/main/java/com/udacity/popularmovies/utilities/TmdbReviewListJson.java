package com.udacity.popularmovies.utilities;

import com.google.gson.Gson;
import com.udacity.popularmovies.model.Review;
import com.udacity.popularmovies.model.ReviewList;

import java.util.ArrayList;
import java.util.List;

public class TmdbReviewListJson {
    List<Review> mReviews;

    public TmdbReviewListJson() { mReviews = new ArrayList<>(); }

    public static List<Review> parse(String json) {
        Gson gson = new Gson();
        ReviewList reviewList = gson.fromJson(json, ReviewList.class);

        return reviewList.getResults();
    }
}
