package com.udacity.popularmovies;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import com.udacity.popularmovies.databinding.ActivityDetailBinding;
import com.udacity.popularmovies.model.Movie;
import com.udacity.popularmovies.model.Video;
import com.udacity.popularmovies.utilities.TmdbApiUtils;
import com.udacity.popularmovies.utilities.TmdbMovieJson;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class DetailActivity extends AppCompatActivity {

    private final static String EXTRA_MOVIE_ID = "movie id";

    private int mId;
    private ActivityDetailBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);

        Intent intent = getIntent();

        if (intent == null) {
            closeOnError();
            return;
        }

        Bundle bundle = intent.getExtras();

        if (bundle == null) {
            closeOnError();
            return;
        }

        mId = bundle.getInt(EXTRA_MOVIE_ID);

        if (mId == 0) {
            closeOnError();
            return;
        }

        try {
            fetchMovie(TmdbApiUtils.buildMovieUrl(mId));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void showMovieDataView() {
        mBinding.tvDetailErrorMessage.setVisibility(View.INVISIBLE);
        mBinding.pbDetailLoadingIndicator.setVisibility(View.INVISIBLE);
        mBinding.svDetailMovie.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage(int message_id) {
        mBinding.svDetailMovie.setVisibility(View.INVISIBLE);
        mBinding.pbDetailLoadingIndicator.setVisibility(View.INVISIBLE);
        mBinding.tvDetailErrorMessage.setText(message_id);
        mBinding.tvDetailErrorMessage.setVisibility(View.VISIBLE);
    }

    private void showLoadingIndicator() {
        mBinding.tvDetailErrorMessage.setVisibility(View.INVISIBLE);
        mBinding.svDetailMovie.setVisibility(View.INVISIBLE);
        mBinding.pbDetailLoadingIndicator.setVisibility(View.VISIBLE);
    }

    void fetchMovie(URL url) throws IOException {

        showLoadingIndicator();

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();

                DetailActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showErrorMessage(R.string.main_network_error);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String tmdbResults = response.body().string();

                DetailActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (tmdbResults != null && !tmdbResults.equals("")) {
                            showMovieDataView();

                            Movie movie = TmdbMovieJson.parse(tmdbResults);

                            Picasso.with(DetailActivity.this)
                                    .load(movie.getPosterUrl())
                                    .placeholder(R.drawable.ic_launcher_foreground)
                                    .into(mBinding.ivMoviePoster);

                            Picasso.with(DetailActivity.this)
                                    .load(movie.getBackdropUrl())
                                    .placeholder(R.drawable.ic_launcher_background)
                                    .into(mBinding.ivBackdrop);

                            mBinding.tvMovieOriginalTitle.setText(movie.getOriginalTitle());
                            mBinding.tvMovieReleaseDate.setText(movie.getReleaseDate().substring(0,4));
                            mBinding.tvMovieVoteAverage.setText(movie.getVoteAverage().toString());
                            mBinding.tvMovieRuntime.setText(Integer.toString(movie.getRuntime()));

                            if (movie.getTagline().equals("")) {
                                mBinding.tvMovieTagline.setHeight(0);
                                mBinding.tvMovieTagline.setPadding(0,0,0,0);
                            } else {
                                mBinding.tvMovieTagline.setText(movie.getTagline());
                            }
                            mBinding.tvMovieOverview.setText(movie.getOverview());

                            List<Video> myList = movie.getVideos().getResults();

                            for (int i = 0; i < myList.size(); i++) {
                                Log.d("DEBUG: ", myList.get(i).getName());
                            }
                        }
                    }
                });

            }
        });
    }

}
