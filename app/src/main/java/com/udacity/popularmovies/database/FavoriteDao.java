package com.udacity.popularmovies.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.udacity.popularmovies.model.Poster;

import java.util.List;

@Dao
public interface FavoriteDao {

    @Query("SELECT * FROM favorite ORDER BY title")
    LiveData<List<Poster>> loadAllFavorites();

    @Insert
    void insertFavorite(Poster poster);

    @Delete
    void deleteFavorite(Poster poster);

    @Query("SELECT * FROM favorite WHERE id = :id")
//    LiveData<Poster> loadFavoriteById(int id);
    Poster loadFavoriteById(int id);
}
