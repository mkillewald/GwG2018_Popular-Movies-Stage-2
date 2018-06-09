package com.udacity.popularmovies;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.udacity.popularmovies.databinding.VideoListItemBinding;
import com.udacity.popularmovies.model.Video;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoAdapterImageViewHolder> {
    private List<Video> mVideos;

    private final VideoAdapterOnClickHandler mClickHandler;

    public interface VideoAdapterOnClickHandler {
        void onClick(Video video);
    }

    public VideoAdapter(VideoAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    public class VideoAdapterImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final VideoListItemBinding mVideoListItemBinding;

        VideoAdapterImageViewHolder(VideoListItemBinding videoListItemBinding) {
            super(videoListItemBinding.getRoot());
            mVideoListItemBinding = videoListItemBinding;

            mVideoListItemBinding.tvItemVideoName.setOnClickListener(this);
            mVideoListItemBinding.ivItemPlayButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Video video = mVideos.get(adapterPosition);
            mClickHandler.onClick(video);

        }
    }

    @Override
    public VideoAdapterImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        VideoListItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.video_list_item, parent, false);

        VideoAdapterImageViewHolder viewHolder = new VideoAdapterImageViewHolder(binding);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(VideoAdapterImageViewHolder holder, int position) {
        Video video = mVideos.get(position);

        holder.mVideoListItemBinding.setVideo(video);
        holder.mVideoListItemBinding.tvItemVideoName.setText(video.getName());
    }

    @Override
    public int getItemCount() {
        if (mVideos == null) return 0;

        return mVideos.size();
    }

    public void setVideoData(List<Video> videos) {
        mVideos = videos;
        notifyDataSetChanged();
    }
}
