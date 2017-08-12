package com.example.kollesnica_power.moviereviewsapi.mvp;

import android.app.Activity;
import android.content.Context;
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



public class Presenter implements MainMVP.Presenter {

    private MainMVP.View mView;
    private ArrayList<ReviewModel> mReviews = new ArrayList<>();
    private ReviewAdapter mAdapter;
    private RecyclerView mRecyclerView;



    @Override
    public void setUpAdapter(RecyclerView recyclerView) {

        mAdapter = new ReviewAdapter((Activity) mView, mReviews);
        mRecyclerView = recyclerView;
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager((Context) mView);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public void attachView(MainMVP.View view) {

        mView = view;

    }

    @Override
    public void detachView() {

        mView = null;

    }

    @Override
    public void getReviews(String query) {

        RequestBuilder.reviewsRequest(App.getInstance().getString(R.string.reviews_url))
                .getData(App.getInstance().getString(R.string.api_key), query).enqueue(new Callback<ReviewResponse>() {
            @Override
            public void onResponse(@NonNull Call<ReviewResponse> call, @NonNull Response<ReviewResponse> response) {

                Log.e("RequestSuccess", response.toString());

                mView.hideRefresher();
                mReviews.clear();

                if (response.body() != null && response.body().getResults().size() > 0) {

                    mReviews.addAll(response.body().getResults());
                    mRecyclerView.smoothScrollToPosition(0);

                }

                mAdapter.notifyDataSetChanged();

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
