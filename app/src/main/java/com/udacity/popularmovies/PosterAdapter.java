package com.udacity.popularmovies;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.udacity.popularmovies.databinding.PosterListItemBinding;
import com.udacity.popularmovies.model.Poster;

import java.util.List;

public class PosterAdapter extends RecyclerView.Adapter<PosterAdapter.PosterAdapterImageViewHolder> {
    private List<Poster> mPosters;

    private final PosterAdapterOnClickHandler mClickHandler;

    public interface PosterAdapterOnClickHandler {
        void onClick(Poster poster);
    }

    public PosterAdapter(PosterAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    public class PosterAdapterImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final PosterListItemBinding mPosterListItemBinding;

        PosterAdapterImageViewHolder(PosterListItemBinding posterListItemBinding) {
            super(posterListItemBinding.getRoot());
            mPosterListItemBinding = posterListItemBinding;

            mPosterListItemBinding.ivItemMoviePoster.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Poster poster = mPosters.get(adapterPosition);
            mClickHandler.onClick(poster);

        }
    }

    @Override
    public PosterAdapterImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        PosterListItemBinding binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.getContext()),
            R.layout.poster_list_item, parent, false);

        PosterAdapterImageViewHolder viewHolder = new PosterAdapterImageViewHolder(binding);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PosterAdapterImageViewHolder holder, int position) {
        Poster poster = mPosters.get(position);

        holder.mPosterListItemBinding.setPoster(poster);

        Picasso.with(holder.itemView.getContext())
                .load(poster.getUrl())
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(holder.mPosterListItemBinding.ivItemMoviePoster);
    }

    @Override
    public int getItemCount() {
        if (mPosters == null) return 0;

        return mPosters.size();
    }

    public void setPosterData(List<Poster> posters) {
        mPosters = posters;
        notifyDataSetChanged();
    }
}
