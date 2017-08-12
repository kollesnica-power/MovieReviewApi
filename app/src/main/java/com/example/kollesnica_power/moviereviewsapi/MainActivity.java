package com.example.kollesnica_power.moviereviewsapi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.example.kollesnica_power.moviereviewsapi.adapter.ReviewAdapter;
import com.example.kollesnica_power.moviereviewsapi.model.ReviewModel;
import com.example.kollesnica_power.moviereviewsapi.model.ReviewResponse;
import com.example.kollesnica_power.moviereviewsapi.request.RequestBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ArrayList<ReviewModel> mReviews = new ArrayList<>();
    private RecyclerView recyclerView;
    private ReviewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setAdapter();
        makeRequest();


    }

    private void setAdapter() {

        recyclerView = (RecyclerView) findViewById(R.id.rv_reviews);

        mAdapter = new ReviewAdapter(mReviews);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

    }

    private void makeRequest() {

        RequestBuilder.reviewsRequest(getString(R.string.reviews_url)).getData(getString(R.string.api_key)).enqueue(new Callback<ReviewResponse>() {
            @Override
            public void onResponse(Call<ReviewResponse> call, Response<ReviewResponse> response) {

                Log.e("RequestSuccess", response.toString());
                mReviews.addAll(response.body().getResults());
                mAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<ReviewResponse> call, Throwable t) {

                Log.e("RequestSuccess", t.getLocalizedMessage());

            }
        });

    }

}
