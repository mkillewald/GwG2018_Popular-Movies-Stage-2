package com.udacity.popularmovies.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;

import com.udacity.popularmovies.utilities.TmdbApiUtils;

public class Movie extends BaseObservable implements Parcelable {

    // Set the desired image widths here (w92, w154, w185, w342, w500 or w780).
    private final static String POSTER_WIDTH = "w342";
    private final static String BACKDROP_WIDTH = "w500";

    private int id;
    private String title;
    private String original_title;
    private String poster_path;
    private String overview;
    private Double vote_average;
    private String release_date;
    private String backdrop_path;

    public Movie() {
    }

    private Movie(Parcel source) {
        this.id = source.readInt();
        this.title = source.readString();
        this.original_title = source.readString();
        this.poster_path = source.readString();
        this.overview = source.readString();
        this.vote_average = source.readDouble();
        this.release_date = source.readString();
        this.backdrop_path = source.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(original_title);
        dest.writeString(poster_path);
        dest.writeString(overview);
        dest.writeDouble(vote_average);
        dest.writeString(release_date);
        dest.writeString(backdrop_path);
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

}
