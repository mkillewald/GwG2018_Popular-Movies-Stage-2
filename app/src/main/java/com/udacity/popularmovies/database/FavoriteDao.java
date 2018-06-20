package com.udacity.popularmovies.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.udacity.popularmovies.model.Movie;

import java.util.List;

@Dao
public interface FavoriteDao {

    @Query("SELECT * FROM favorite ORDER BY title")
    LiveData<List<Favorite>> loadAllFavorites();

    @Insert
    void insertFavorite(Favorite favorite);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateFavorite(Favorite favorite);

    @Delete
    void deleteFavorite(Favorite favorite);

    @Query("SELECT * FROM favorite WHERE id = :id")
    LiveData<Movie> loadFavoriteById(int id);
}
