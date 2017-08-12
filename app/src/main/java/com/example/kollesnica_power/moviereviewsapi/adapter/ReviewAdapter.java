package com.example.kollesnica_power.moviereviewsapi.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.kollesnica_power.moviereviewsapi.App;
import com.example.kollesnica_power.moviereviewsapi.R;
import com.example.kollesnica_power.moviereviewsapi.model.ReviewModel;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by kollesnica1337 on 12.08.2017.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    private ArrayList<ReviewModel> moviesList;
    private int[] androidColors;

    public ReviewAdapter(ArrayList<ReviewModel> moviesList) {
        this.moviesList = moviesList;
        androidColors = App.getInstance().getResources().getIntArray(R.array.androidcolors);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_review, parent, false);

        return new ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        ReviewModel review = moviesList.get(position);

        if (review.getMultimedia().getSrc() != null && !review.getMultimedia().getSrc().isEmpty()){

            Glide
                    .with(App.getInstance())
                    .load(review.getMultimedia().getSrc())
                    .into(holder.image);

        }else {

            holder.image.setBackgroundColor(androidColors[new Random().nextInt(androidColors.length)]);

        }

        holder.title.setText(review.getDisplayTitle());
        if (review.getMpaaRating() != null && !review.getMpaaRating().isEmpty()) {
            holder.rating.setText(review.getMpaaRating());
            holder.rating.setVisibility(View.VISIBLE);
        }else {
            holder.rating.setVisibility(View.INVISIBLE);
        }
        holder.date.setText(review.getPublicationDate());
        holder.headline.setText(review.getHeadline());
        holder.summary.setText(review.getSummaryShort());

    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView title;
        TextView rating;
        TextView date;
        TextView headline;
        TextView summary;

        public ViewHolder(View view) {
            super(view);
            image = view.findViewById(R.id.iv_review_image);
            title = view.findViewById(R.id.tv_review_name);
            rating = view.findViewById(R.id.tv_review_rating);
            date = view.findViewById(R.id.tv_review_date);
            headline = view.findViewById(R.id.tv_review_headline);
            summary = view.findViewById(R.id.tv_review_summary_short);

        }
    }


}
