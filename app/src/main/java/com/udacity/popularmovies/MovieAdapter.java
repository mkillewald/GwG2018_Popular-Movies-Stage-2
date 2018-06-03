package com.udacity.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
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
        public final ImageView mMovieImageView;

        public MovieAdapterImageViewHolder(View itemView) {
            super(itemView);

            mMovieImageView = itemView.findViewById(R.id.iv_item_movie_poster);
            mMovieImageView.setOnClickListener(this);
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
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.movie_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        MovieAdapterImageViewHolder viewHolder = new MovieAdapterImageViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MovieAdapterImageViewHolder holder, int position) {
        Movie movie = mMovies[position];

        Picasso.with(holder.itemView.getContext())
                .load(movie.getPosterUrl())
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(holder.mMovieImageView);
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
