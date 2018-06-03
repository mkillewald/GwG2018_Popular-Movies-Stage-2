package com.udacity.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.udacity.popularmovies.model.Movie;
import com.udacity.popularmovies.utilities.JsonUtils;
import com.udacity.popularmovies.utilities.TmdbUtils;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity
        implements MovieAdapter.MovieAdapterOnClickHandler {

    private final static int NUM_OF_COLUMNS = 3;
    private final static String EXTRA_MOVIE = "com.udacity.popularmovies.model.Movie";

    private MovieAdapter mMovieAdapter;
    private RecyclerView mRecyclerView;
    private TextView mErrorMessage;
    private ProgressBar mLoadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.rv_movies);

        mErrorMessage = findViewById(R.id.tv_error_message);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, NUM_OF_COLUMNS,
                GridLayoutManager.VERTICAL, false);

        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        mMovieAdapter = new MovieAdapter(this);
        mRecyclerView.setAdapter(mMovieAdapter);

        mLoadingIndicator = findViewById(R.id.pb_loading_indicator);

        loadMovieData();
    }

    private void loadMovieData() {
        showMovieDataView();

        new tmdbQueryTask().execute(TmdbUtils.popularURL());
    }

    private void showMovieDataView() {
        mErrorMessage.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMessage.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(Movie movie) {
        Context context = this;
        Class destinationClass = DetailActivity.class;
        Intent intentToStartDetailActivity = new Intent(context, destinationClass);

        intentToStartDetailActivity.putExtra(EXTRA_MOVIE, movie);
        startActivity(intentToStartDetailActivity);
    }

    public class tmdbQueryTask extends AsyncTask<URL, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(URL... params) {
            URL tmdbUrl = params[0];
            String tmdbResults = null;
            try {
                tmdbResults = TmdbUtils.getResponseFromHttpUrl(tmdbUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return tmdbResults;
        }

        @Override
        protected void onPostExecute(String tmdbResults) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (tmdbResults != null && !tmdbResults.equals("")) {
                showMovieDataView();
                Movie[] movies = JsonUtils.parseTmdbJson(tmdbResults);
                mMovieAdapter.setMovieData(movies);
            } else {
                showErrorMessage();
            }
        }
    }
}
