package com.udacity.popularmovies.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;

import com.udacity.popularmovies.utilities.TmdbUtils;

public class Movie extends BaseObservable implements Parcelable {

    // Set the desired image widths here (w92, w154, w185, w342, w500 or w780).
    private final static String POSTER_WIDTH = "w342";
    private final static String BACKDROP_WIDTH = "w500";

    private Double id;
    private String title;
    private String originalTitle;
    private String posterPath;
    private String overview;
    private Double voteAverage;
    private String releaseDate;
    private String backdropPath;

    public Movie() {
    }

    private Movie(Parcel source) {
        this.id = source.readDouble();
        this.title = source.readString();
        this.originalTitle = source.readString();
        this.posterPath = source.readString();
        this.overview = source.readString();
        this.voteAverage = source.readDouble();
        this.releaseDate = source.readString();
        this.backdropPath = source.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(id);
        dest.writeString(title);
        dest.writeString(originalTitle);
        dest.writeString(posterPath);
        dest.writeString(overview);
        dest.writeDouble(voteAverage);
        dest.writeString(releaseDate);
        dest.writeString(backdropPath);
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {

        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public Double getId() {
        return id;
    }

    public void setId(Double id) {
        this.id = id;
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
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public void setBackdropPath(String backdropPath) { this.backdropPath = backdropPath; }

    @Bindable
    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    @Bindable
    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    @Bindable
    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getPosterUrl() {
        return TmdbUtils.buildImageUrl(POSTER_WIDTH, posterPath).toString();
    }

    public String getBackdropUrl() {
        return TmdbUtils.buildImageUrl(BACKDROP_WIDTH, backdropPath).toString();
    }

}
