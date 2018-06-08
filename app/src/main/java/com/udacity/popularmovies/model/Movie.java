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

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Bindable
    public String getOriginalTitle() {
        return original_title;
    }

    public void setOriginalTitle(String originalTitle) {
        this.original_title = originalTitle;
    }

    public void setPosterPath(String posterPath) {
        this.poster_path = posterPath;
    }

    public void setBackdropPath(String backdropPath) { this.backdrop_path = backdropPath; }

    @Bindable
    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    @Bindable
    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    @Bindable
    public Double getVoteAverage() {
        return vote_average;
    }

    public void setVoteAverage(Double voteAverage) {
        this.vote_average = voteAverage;
    }

    @Bindable
    public String getReleaseDate() {
        return release_date;
    }

    public void setReleaseDate(String releaseDate) {
        this.release_date = releaseDate;
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
}
