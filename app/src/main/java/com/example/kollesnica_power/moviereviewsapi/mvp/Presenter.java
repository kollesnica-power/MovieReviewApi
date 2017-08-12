package com.example.kollesnica_power.moviereviewsapi.mvp;

import android.app.Activity;
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

/**
 * Created by kollesnica1337 on 12.08.2017.
 */

public class Presenter implements MainMVP.presenter {

    private MainMVP.view mView;
    private ArrayList<ReviewModel> mReviews = new ArrayList<>();
    private ReviewAdapter mAdapter;

    public Presenter(MainMVP.view view){
        mView = view;
    }

    @Override
    public void setUpAdapter(Activity activity, RecyclerView recyclerView) {

        mAdapter = new ReviewAdapter(activity, mReviews);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

    }

    @Override
    public void getReviews() {

        RequestBuilder.reviewsRequest(App.getInstance().getString(R.string.reviews_url))
                .getData(App.getInstance().getString(R.string.api_key)).enqueue(new Callback<ReviewResponse>() {
            @Override
            public void onResponse(Call<ReviewResponse> call, Response<ReviewResponse> response) {

                Log.e("RequestSuccess", response.toString());

                if (response.body() != null) {

                    mReviews.addAll(response.body().getResults());
                    mAdapter.notifyDataSetChanged();
                    mView.hideRefresher();

                }

            }

            @Override
            public void onFailure(Call<ReviewResponse> call, Throwable t) {

                mView.hideRefresher();
                mView.showToast("Request error");
                Log.e("RequestSuccess", t.getLocalizedMessage());

            }
        });

    }
}
