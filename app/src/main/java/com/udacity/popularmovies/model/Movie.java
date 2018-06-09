package com.udacity.popularmovies.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.udacity.popularmovies.utilities.TmdbApiUtils;

public class Movie extends BaseObservable {

    // Set the desired image widths here (w92, w154, w185, w342, w500 or w780).
    private final static String POSTER_WIDTH = "w342";
    private final static String BACKDROP_WIDTH = "w500";

    private int id;
    private int runtime;
    private String title;
    private String original_title;
    private String poster_path;
    private String overview;
    private Double vote_average;
    private String release_date;
    private String backdrop_path;
    private String tagline;
    private Boolean video;
    private VideoList videos;
    private ReviewList reviews;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Bindable
    public int getRuntime() {
        return runtime;
    }

    @Bindable
    public String getTitle() {
        return title;
    }

    @Bindable
    public String getOriginalTitle() {
        return original_title;
    }

    @Bindable
    public String getTagline() {
        return tagline;
    }

    @Bindable
    public String getOverview() {
        return overview;
    }

    @Bindable
    public Double getVoteAverage() {
        return vote_average;
    }

    @Bindable
    public String getReleaseDate() {
        return release_date;
    }

    public String getPosterUrl() {
        return TmdbApiUtils.buildImageUrl(POSTER_WIDTH, poster_path).toString();
    }

    public String getBackdropUrl() {
        return TmdbApiUtils.buildImageUrl(BACKDROP_WIDTH, backdrop_path).toString();
    }

    public Boolean getVideo() {
        return video;
    }

    public void setVideo(Boolean video) {
        this.video = video;
    }

    public VideoList getVideos() {
        return videos;
    }

    public void setVideos(VideoList videos) {
        this.videos = videos;
    }

    public ReviewList getReviews() {
        return reviews;
    }

    public void setReviews(ReviewList reviews) {
        this.reviews = reviews;
    }
}
