package com.udacity.popularmovies;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import com.udacity.popularmovies.database.AppDatabase;
import com.udacity.popularmovies.databinding.ActivityDetailBinding;
import com.udacity.popularmovies.model.Movie;
import com.udacity.popularmovies.model.Poster;
import com.udacity.popularmovies.model.Review;
import com.udacity.popularmovies.model.Video;
import com.udacity.popularmovies.utilities.AppExecutors;
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


public class DetailActivity extends AppCompatActivity
        implements VideoAdapter.VideoAdapterOnClickHandler,
        ReviewAdapter.ReviewAdapterOnClickHandler {

    private final static String EXTRA_MOVIE_ID = "movieId";
    private final static String EXTRA_REVIEW = "com.udacity.popularmovies.model.Review";
    private final static String EXTRA_POSTER_URL = "extraPosterUrl";
    private final static String EXTRA_BACKDROP_URL = "extraBackdropUrl";
    private final static String SCROLL_POSITION = "detailScrollPosition";

    private int mId;
    private boolean isFavorited = false;

    private Movie mMovie;
    private AppDatabase mDb;
    private ActivityDetailBinding mBinding;
    private VideoAdapter mVideoAdapter;
    private ReviewAdapter mReviewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDb = AppDatabase.getInstance(getApplicationContext());

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);

        setTitle(R.string.detail_title);

        LinearLayoutManager videoLinearLayoutManager = new LinearLayoutManager(this);
        mBinding.rvMovieVideos.setLayoutManager(videoLinearLayoutManager);
        mBinding.rvMovieVideos.setHasFixedSize(true);
        mBinding.rvMovieVideos.setNestedScrollingEnabled(false);

        mVideoAdapter = new VideoAdapter(this);
        mBinding.rvMovieVideos.setAdapter(mVideoAdapter);

        LinearLayoutManager reviewLinearLayoutManager = new LinearLayoutManager(this);
        mBinding.rvMovieReviews.setLayoutManager(reviewLinearLayoutManager);
        mBinding.rvMovieReviews.setHasFixedSize(true);
        mBinding.rvMovieReviews.setNestedScrollingEnabled(false);

        mReviewAdapter = new ReviewAdapter(this);
        mBinding.rvMovieReviews.setAdapter(mReviewAdapter);

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

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                Poster favorite = mDb.favoriteDao().loadFavoriteById(mId);
                if ( favorite != null) {
                    isFavorited = true;
                    mBinding.ibFavorite.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),
                            android.R.drawable.btn_star_big_on));
                }
            }
        });

//        LiveData<Poster> favorite = mDb.favoriteDao().loadFavoriteById(mId);
//        favorite.observe(this, new Observer<Poster>() {
//            @Override
//            public void onChanged(@Nullable Poster favorite) {
//                if ( favorite != null) {
//                    isFavorited = true;
//                    mBinding.ibFavorite.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),
//                            android.R.drawable.btn_star_big_on));
//                }
//            }
//        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putIntArray(SCROLL_POSITION, new int[]{ mBinding.svDetailMovie.getScrollX(),
                mBinding.svDetailMovie.getScrollY()});
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        final int[] scrollPosition = savedInstanceState.getIntArray(SCROLL_POSITION);

        if(scrollPosition != null) {
            mBinding.svDetailMovie.postDelayed(new Runnable() {
                public void run() {
                    mBinding.svDetailMovie.scrollTo(scrollPosition[0], scrollPosition[1]);
                }
            }, 500);
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

    @Override
    public void onClick(Video video) {
        Context context = this;

        String id = video.getKey();

        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + id));
        try {
            context.startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            context.startActivity(webIntent);
        }
    }

    @Override
    public void onClick(Review review) {
        Context context = this;

        Class destinationClass = ReviewActivity.class;
        Intent intentToStartReviewActivity = new Intent(context, destinationClass);

        intentToStartReviewActivity.putExtra(EXTRA_REVIEW, review);
        intentToStartReviewActivity.putExtra(EXTRA_POSTER_URL, mMovie.getPosterUrl());
        intentToStartReviewActivity.putExtra(EXTRA_BACKDROP_URL, mMovie.getBackdropUrl());
        startActivity(intentToStartReviewActivity);

//        Intent webIntent = new Intent(Intent.ACTION_VIEW,
//                Uri.parse(review.getUrl()));
//
//        context.startActivity(webIntent);
    }

    public void onToggleFavorite(View view) {
        isFavorited = !isFavorited;
        if (isFavorited) {
            mBinding.ibFavorite.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),
                    android.R.drawable.btn_star_big_on));

            // add favorite
            final Poster favorite = new Poster(mMovie.getId(), mMovie.getTitle(), mMovie.getPosterPath());
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    mDb.favoriteDao().insertFavorite(favorite);
                }
            });

        } else {
            mBinding.ibFavorite.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),
                    android.R.drawable.btn_star_big_off));

            // delete favorite
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    Poster favorite = mDb.favoriteDao().loadFavoriteById(mId);
                    mDb.favoriteDao().deleteFavorite(favorite);
                }
            });

//            LiveData<Poster> favorite = mDb.favoriteDao().loadFavoriteById(mId);
//            favorite.observe(DetailActivity.this, new Observer<Poster>() {
//                @Override
//                public void onChanged(final @Nullable Poster favorite) {
//                    AppExecutors.getInstance().diskIO().execute(new Runnable() {
//                        @Override
//                        public void run() {
//                            // causes null reference crash
//                            Log.e("DEBUG: ", favorite.getTitle());
//                            mDb.favoriteDao().deleteFavorite(favorite);
//                        }
//                    });
//                }
//            });
        }
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

                            mMovie = TmdbMovieJson.parse(tmdbResults);

                            Picasso.with(DetailActivity.this)
                                    .load(mMovie.getPosterUrl())
                                    .placeholder(R.drawable.ic_launcher_foreground)
                                    .into(mBinding.ivMoviePoster);

                            Picasso.with(DetailActivity.this)
                                    .load(mMovie.getBackdropUrl())
                                    .placeholder(R.drawable.ic_launcher_background)
                                    .into(mBinding.ivBackdrop);

                            mBinding.tvMovieOriginalTitle.setText(mMovie.getOriginalTitle());
                            mBinding.tvMovieReleaseDate.setText(mMovie.getReleaseDate().substring(0,4));
                            mBinding.tvMovieVoteAverage.setText(mMovie.getVoteAverage().toString());
                            mBinding.tvMovieRuntime.setText(Integer.toString(mMovie.getRuntime()));

                            if (mMovie.getTagline().equals("")) {
                                mBinding.tvMovieTagline.setHeight(0);
                                mBinding.tvMovieTagline.setPadding(0,0,0,0);
                            } else {
                                mBinding.tvMovieTagline.setText(mMovie.getTagline());
                            }
                            mBinding.tvMovieOverview.setText(mMovie.getOverview());

                            List<Video> videoList = mMovie.getVideos().getResults();
                            mVideoAdapter.setVideoData(videoList);

                            List<Review> reviewList = mMovie.getReviews().getResults();
                            mReviewAdapter.setReviewData(reviewList);
                        }
                    }
                });

            }
        });
    }

}
