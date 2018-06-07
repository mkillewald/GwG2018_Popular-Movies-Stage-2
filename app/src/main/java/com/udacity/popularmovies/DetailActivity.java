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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityDetailBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);

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
                .into(binding.ivMoviePoster);

        Picasso.with(this)
                .load(movie.getBackdropUrl())
                .placeholder(R.drawable.ic_launcher_background)
                .into(binding.ivBackdrop);

        binding.tvMovieOriginalTitle.setText(movie.getOriginalTitle());
        binding.tvMovieOverview.setText(movie.getOverview());
        binding.tvMovieVoteAverage.setText(movie.getVoteAverage().toString());
        binding.tvMovieReleaseDate.setText(movie.getReleaseDate().substring(0,4));
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

}
