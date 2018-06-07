package com.udacity.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.udacity.popularmovies.databinding.ActivityMainBinding;
import com.udacity.popularmovies.model.Movie;
import com.udacity.popularmovies.utilities.TmdbApiUtils;
import com.udacity.popularmovies.utilities.TmdbMovieListJson;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity
        implements MovieAdapter.MovieAdapterOnClickHandler, AdapterView.OnItemSelectedListener {

    private final static int NUM_OF_COLUMNS = 3;
    private final static String EXTRA_MOVIE = "com.udacity.popularmovies.model.Movie";

    private ActivityMainBinding mBinding;

    private MovieAdapter mMovieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, NUM_OF_COLUMNS,
                GridLayoutManager.VERTICAL, false);

        mBinding.rvMovies.setLayoutManager(gridLayoutManager);
        mBinding.rvMovies.setHasFixedSize(true);

        mMovieAdapter = new MovieAdapter(this);
        mBinding.rvMovies.setAdapter(mMovieAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar_spinner_menu, menu);

        MenuItem item = menu.findItem(R.id.spinner);
        Spinner spinner = (Spinner) item.getActionView();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_list_item_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        return true;
    }

    private void loadPopularData() {
        try {
            run(TmdbApiUtils.popularUrl());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadTopRatedData() {
        try {
            run(TmdbApiUtils.topRatedUrl());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showMovieDataView() {
        mBinding.tvErrorMessage.setVisibility(View.INVISIBLE);
        mBinding.pbLoadingIndicator.setVisibility(View.INVISIBLE);
        mBinding.rvMovies.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage(int message_id) {
        mBinding.rvMovies.setVisibility(View.INVISIBLE);
        mBinding.pbLoadingIndicator.setVisibility(View.INVISIBLE);
        mBinding.tvErrorMessage.setText(message_id);
        mBinding.tvErrorMessage.setVisibility(View.VISIBLE);
    }

    private void showLoadingIndicator() {
        mBinding.tvErrorMessage.setVisibility(View.INVISIBLE);
        mBinding.rvMovies.setVisibility(View.INVISIBLE);
        mBinding.pbLoadingIndicator.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(Movie movie) {
        Context context = this;
        Class destinationClass = DetailActivity.class;
        Intent intentToStartDetailActivity = new Intent(context, destinationClass);

        intentToStartDetailActivity.putExtra(EXTRA_MOVIE, movie);
        startActivity(intentToStartDetailActivity);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch(position) {
            case 0:
                loadPopularData();
                break;
            case 1:
                loadTopRatedData();
                break;
            default:
                showErrorMessage(R.string.main_error_message);
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    void run(URL url) throws IOException {

        showLoadingIndicator();

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();

                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showErrorMessage(R.string.main_network_error);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String tmdbResults = response.body().string();

                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (tmdbResults != null && !tmdbResults.equals("")) {
                            showMovieDataView();

                            List<Movie> movies = TmdbMovieListJson.parseJSON(tmdbResults);
                            mMovieAdapter.setMovieData(movies);
                        }
                    }
                });

            }
        });
    }
}
