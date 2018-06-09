package com.udacity.popularmovies;

import android.content.Context;
import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udacity.popularmovies.databinding.ReviewListItemBinding;
import com.udacity.popularmovies.model.Review;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewAdapterImageViewHolder> {

    private final static int REVIEW_PREVIEW_LENGTH = 200;

    private List<Review> mReviews;

    private final ReviewAdapterOnClickHandler mClickHandler;

    public interface ReviewAdapterOnClickHandler {
        void onClick(Review review);
    }

    public ReviewAdapter(ReviewAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    public class ReviewAdapterImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final ReviewListItemBinding mReviewListItemBinding;

        ReviewAdapterImageViewHolder(ReviewListItemBinding reviewListItemBinding) {
            super(reviewListItemBinding.getRoot());
            mReviewListItemBinding = reviewListItemBinding;

            mReviewListItemBinding.tvItemReviewContent.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Review review = mReviews.get(adapterPosition);
            mClickHandler.onClick(review);
        }
    }

    @Override
    public ReviewAdapterImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ReviewListItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.review_list_item, parent, false);

        ReviewAdapterImageViewHolder viewHolder = new ReviewAdapterImageViewHolder(binding);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ReviewAdapterImageViewHolder holder, int position) {
        Review review = mReviews.get(position);

        holder.mReviewListItemBinding.setReview(review);
        holder.mReviewListItemBinding.tvItemReviewAuthor.setText(review.getAuthor());

        String content = review.getContent();

        if (content.length() > REVIEW_PREVIEW_LENGTH) {
            Resources resources = holder.itemView.getResources();
            Context context = holder.itemView.getContext();

            content = content.substring(0, REVIEW_PREVIEW_LENGTH);

            SpannableString more = new SpannableString(resources
                    .getString(R.string.detail_content_more));
            more.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context,
                    R.color.colorPrimary)), 0, more.length(), 0);

            SpannableStringBuilder builder = new SpannableStringBuilder();
            builder.append(content);
            builder.append(resources.getString(R.string.detail_content_ellipsis));
            builder.append(" ");
            builder.append(more);

            holder.mReviewListItemBinding.tvItemReviewContent
                    .setText(builder, TextView.BufferType.SPANNABLE);
        } else {
            holder.mReviewListItemBinding.tvItemReviewContent.setText(content);
        }

    }

    @Override
    public int getItemCount() {
        if (mReviews == null) return 0;

        return mReviews.size();
    }

    public void setReviewData(List<Review> reviews) {
        mReviews = reviews;
        notifyDataSetChanged();
    }
}
