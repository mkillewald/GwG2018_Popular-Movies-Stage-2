package com.udacity.popularmovies.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.udacity.popularmovies.utilities.TmdbApiUtils;

@Entity(tableName = "favorite")
public class Poster {

    // Set the desired image width here (w92, w154, w185, w342, w500 or w780).
    private final static String POSTER_WIDTH = "w342";

    @PrimaryKey(autoGenerate = false)
    private int id;
    private String title;
    private String poster_path;


    public Poster(int id, String title, String poster_path) {
        this.id = id;
        this.title = title;
        this.poster_path = poster_path;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getUrl() {
        return TmdbApiUtils.buildImageUrl(POSTER_WIDTH, poster_path).toString();
    }
}
