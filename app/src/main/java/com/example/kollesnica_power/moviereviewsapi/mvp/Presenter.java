package com.example.kollesnica_power.moviereviewsapi.mvp;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.kollesnica_power.moviereviewsapi.App;
import com.example.kollesnica_power.moviereviewsapi.R;
import com.example.kollesnica_power.moviereviewsapi.adapter.ReviewAdapter;
import com.example.kollesnica_power.moviereviewsapi.model.ReviewModel;
import com.example.kollesnica_power.moviereviewsapi.model.ReviewResponse;
import com.example.kollesnica_power.moviereviewsapi.request.RequestBuilder;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class Presenter implements MainMVP.presenter {

    private MainMVP.view mView;
    private ArrayList<ReviewModel> mReviews = new ArrayList<>();
    private ReviewAdapter mAdapter;
    private RecyclerView mRecyclerView;

    public Presenter(MainMVP.view view){
        mView = view;
    }

    @Override
    public void setUpAdapter(Activity activity, RecyclerView recyclerView) {

        mAdapter = new ReviewAdapter(activity, mReviews);
        mRecyclerView = recyclerView;
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(activity);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public void getReviews(String query) {

        RequestBuilder.reviewsRequest(App.getInstance().getString(R.string.reviews_url))
                .getData(App.getInstance().getString(R.string.api_key), query).enqueue(new Callback<ReviewResponse>() {
            @Override
            public void onResponse(@NonNull Call<ReviewResponse> call, @NonNull Response<ReviewResponse> response) {

                Log.e("RequestSuccess", response.toString());

                if (response.body() != null && response.body().getResults().size() > 0) {

                    mReviews.clear();
                    mReviews.addAll(response.body().getResults());
                    mAdapter.notifyDataSetChanged();
                    mView.hideRefresher();
                    mRecyclerView.smoothScrollToPosition(0);

                }

            }

            @Override
            public void onFailure(@NonNull Call<ReviewResponse> call, @NonNull Throwable t) {

                mView.hideRefresher();
                mView.showToast("Request error");
                Log.e("RequestSuccess", t.getLocalizedMessage());

            }
        });

    }
}
