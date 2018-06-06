package com.udacity.popularmovies;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import com.udacity.popularmovies.databinding.ActivityDetailBinding;
import com.udacity.popularmovies.model.Movie;


public class DetailActivity extends AppCompatActivity {

    private final static String EXTRA_MOVIE = "com.udacity.popularmovies.model.Movie";

    ActivityDetailBinding mBinding;

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

        Movie movie = bundle.getParcelable(EXTRA_MOVIE);

        if (movie == null) {
            closeOnError();
            return;
        }

        Picasso.with(this)
                .load(movie.getPosterUrl())
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(mBinding.ivMoviePoster);

        Picasso.with(this)
                .load(movie.getBackdropUrl())
                .placeholder(R.drawable.ic_launcher_background)
                .into(mBinding.ivBackdrop);

        mBinding.tvMovieOriginalTitle.setText(movie.getOriginalTitle());
        mBinding.tvMovieOverview.setText(movie.getOverview());
        mBinding.tvMovieVoteAverage.setText(movie.getVoteAverage().toString());
        mBinding.tvMovieReleaseDate.setText(movie.getReleaseDate());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

}
