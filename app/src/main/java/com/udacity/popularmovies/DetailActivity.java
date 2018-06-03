package com.udacity.popularmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.udacity.popularmovies.model.Movie;

public class DetailActivity extends AppCompatActivity {

    private final static String EXTRA_MOVIE = "com.udacity.popularmovies.model.Movie";

    private Movie mMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Bundle bundle = getIntent().getExtras();
        mMovie = bundle.getParcelable(EXTRA_MOVIE);

        TextView textView = findViewById(R.id.tv_movie_title);
        textView.setText(mMovie.getTitle());
    }

}
