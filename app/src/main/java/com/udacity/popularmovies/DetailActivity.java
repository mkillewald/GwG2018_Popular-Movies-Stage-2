package com.udacity.popularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import com.udacity.popularmovies.model.Movie;

public class DetailActivity extends AppCompatActivity {

    private final static String EXTRA_MOVIE = "com.udacity.popularmovies.model.Movie";

    private Movie mMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView moviePosterImageView = findViewById(R.id.iv_movie_poster);
        ImageView backdropImageView = findViewById(R.id.iv_backdrop);
        TextView originalTitleTextView = findViewById(R.id.tv_movie_original_title);
        TextView overviewTextView = findViewById(R.id.tv_movie_overview);
        TextView voteAverageTextView = findViewById(R.id.tv_movie_vote_average);
        TextView releaseDateTextView = findViewById(R.id.tv_movie_release_date);

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

        mMovie = bundle.getParcelable(EXTRA_MOVIE);

        if (mMovie == null) {
            closeOnError();
            return;
        }

        Picasso.with(this)
                .load(mMovie.getPosterUrl())
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(moviePosterImageView);

        Picasso.with(this)
                .load(mMovie.getBackdropUrl())
                .placeholder(R.drawable.ic_launcher_background)
                .into(backdropImageView);

        originalTitleTextView.setText(mMovie.getOriginalTitle());
        overviewTextView.setText(mMovie.getOverview());
        voteAverageTextView.setText(mMovie.getVoteAverage().toString());
        releaseDateTextView.setText(mMovie.getReleaseDate());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

}
