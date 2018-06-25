package com.udacity.popularmovies;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.udacity.popularmovies.database.AppDatabase;
import com.udacity.popularmovies.model.Poster;

import java.util.List;

public class FavoriteViewModel extends AndroidViewModel {

    private LiveData<List<Poster>> mFavorites;

    public FavoriteViewModel(@NonNull Application application) {
        super(application);

        AppDatabase appDatabase = AppDatabase.getInstance(this.getApplication());
        mFavorites = appDatabase.favoriteDao().loadAllFavorites();
    }

    public LiveData<List<Poster>> getFavorites() {
        return mFavorites;
    }
}
