package com.udacity.popularmovies;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.udacity.popularmovies.database.AppDatabase;
import com.udacity.popularmovies.databinding.ActivityMainBinding;
import com.udacity.popularmovies.model.Poster;
import com.udacity.popularmovies.utilities.TmdbApiUtils;
import com.udacity.popularmovies.utilities.TmdbPosterListJson;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements
        PosterAdapter.PosterAdapterOnClickHandler, AdapterView.OnItemSelectedListener {

    private final static int NUM_OF_COLUMNS = 3;
    private final static String EXTRA_MOVIE_ID = "movieId";
    private final static String SPINNER_POSITION = "mainSpinnerPosition";
    private final static String GRID_LAYOUT_STATE = "mainGridLayoutState";

    private int mSpinnerPosition;
    private boolean mDataLoading;

    private ActivityMainBinding mBinding;
    private PosterAdapter mPosterAdapter;
    private AppDatabase mDb;
    private Parcelable mGridLayoutSavedState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDb = AppDatabase.getInstance(getApplicationContext());

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, NUM_OF_COLUMNS,
                GridLayoutManager.VERTICAL, false);

        mBinding.rvPosters.setLayoutManager(gridLayoutManager);
        mBinding.rvPosters.setHasFixedSize(true);

        mPosterAdapter = new PosterAdapter(this);
        mBinding.rvPosters.setAdapter(mPosterAdapter);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(SPINNER_POSITION, mSpinnerPosition);

        mGridLayoutSavedState = mBinding.rvPosters.getLayoutManager().onSaveInstanceState();
        outState.putParcelable(GRID_LAYOUT_STATE, mGridLayoutSavedState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        mSpinnerPosition = savedInstanceState.getInt(SPINNER_POSITION);
        mGridLayoutSavedState = savedInstanceState.getParcelable(GRID_LAYOUT_STATE);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();

        loadData(mSpinnerPosition);
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
        spinner.setSelection(mSpinnerPosition);

        return true;
    }

    private void loadPopularData() {
        try {
            fetchPosterList(TmdbApiUtils.popularUrl());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadTopRatedData() {
        try {
            fetchPosterList(TmdbApiUtils.topRatedUrl());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadFavoriteData() {
        LiveData<List<Poster>> favorites = mDb.favoriteDao().loadAllFavorites();
        favorites.observe(this, new Observer<List<Poster>>() {
            @Override
            public void onChanged(@Nullable List<Poster> favorites) {

                if (favorites.isEmpty()) {
                    showErrorMessage(R.string.main_favorites_empty);
                } else {
                    mPosterAdapter.setPosterData(favorites);
                    mPosterAdapter.notifyDataSetChanged();

                    if (mGridLayoutSavedState != null) {
                        mBinding.rvPosters.getLayoutManager()
                                .onRestoreInstanceState(mGridLayoutSavedState);
                        mGridLayoutSavedState = null;
                    }

                    showPosterDataView();
                }
            }
        });
    }

    private void loadData(int position) {
        if (!mDataLoading) {
            switch (position) {
                case 0:
                    loadPopularData();
                    break;
                case 1:
                    loadTopRatedData();
                    break;
                case 2:
                    loadFavoriteData();
                    break;
                default:
                    showErrorMessage(R.string.main_error_message);
                    break;
            }
        }
    }

    private void showPosterDataView() {
        mDataLoading = false;
        mBinding.tvErrorMessage.setVisibility(View.INVISIBLE);
        mBinding.pbLoadingIndicator.setVisibility(View.INVISIBLE);
        mBinding.rvPosters.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage(int message_id) {
        mDataLoading = false;
        mBinding.rvPosters.setVisibility(View.INVISIBLE);
        mBinding.pbLoadingIndicator.setVisibility(View.INVISIBLE);
        mBinding.tvErrorMessage.setText(message_id);
        mBinding.tvErrorMessage.setVisibility(View.VISIBLE);
    }

    private void showLoadingIndicator() {
        mDataLoading = true;
        mBinding.tvErrorMessage.setVisibility(View.INVISIBLE);
        mBinding.rvPosters.setVisibility(View.INVISIBLE);
        mBinding.pbLoadingIndicator.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(Poster poster) {
        Context context = this;
        Class destinationClass = DetailActivity.class;
        Intent intentToStartDetailActivity = new Intent(context, destinationClass);

        intentToStartDetailActivity.putExtra(EXTRA_MOVIE_ID, poster.getId());
        startActivity(intentToStartDetailActivity);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mSpinnerPosition = position;
        if (mGridLayoutSavedState == null) {
            mBinding.rvPosters.getLayoutManager().scrollToPosition(0);
        }
        loadData(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    void fetchPosterList(URL url) throws IOException {

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

                            List<Poster> posters = TmdbPosterListJson.parse(tmdbResults);
                            mPosterAdapter.setPosterData(posters);
                            mPosterAdapter.notifyDataSetChanged();

                            if (mGridLayoutSavedState != null) {
                                mBinding.rvPosters.getLayoutManager()
                                        .onRestoreInstanceState(mGridLayoutSavedState);
                                mGridLayoutSavedState = null;
                            }

                            showPosterDataView();

                        }
                    }
                });

            }
        });
    }
}
