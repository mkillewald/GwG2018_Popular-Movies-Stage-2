package com.udacity.popularmovies;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.udacity.popularmovies.databinding.MovieListItemBinding;
import com.udacity.popularmovies.model.Movie;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterImageViewHolder> {

    private Movie[] mMovies;

    private final MovieAdapterOnClickHandler mClickHandler;

    public interface MovieAdapterOnClickHandler {
        void onClick(Movie movie);
    }

    public MovieAdapter(MovieAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    public class MovieAdapterImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final MovieListItemBinding mMovieListItemBinding;

        MovieAdapterImageViewHolder(MovieListItemBinding movieListItemBinding) {
            super(movieListItemBinding.getRoot());
            mMovieListItemBinding = movieListItemBinding;

            mMovieListItemBinding.ivItemMoviePoster.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Movie movie = mMovies[adapterPosition];
            mClickHandler.onClick(movie);
        }
    }

    @Override
    public MovieAdapterImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MovieListItemBinding binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.getContext()),
            R.layout.movie_list_item, parent, false);

        MovieAdapterImageViewHolder viewHolder = new MovieAdapterImageViewHolder(binding);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MovieAdapterImageViewHolder holder, int position) {
        Movie movie = mMovies[position];

        holder.mMovieListItemBinding.setMovie(movie);

        Picasso.with(holder.itemView.getContext())
                .load(movie.getPosterUrl())
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(holder.mMovieListItemBinding.ivItemMoviePoster);
    }

    @Override
    public int getItemCount() {
        if (mMovies == null) return 0;
        return mMovies.length;
    }

    public void setMovieData(Movie[] movies) {
        mMovies = movies;
        notifyDataSetChanged();
    }
}
