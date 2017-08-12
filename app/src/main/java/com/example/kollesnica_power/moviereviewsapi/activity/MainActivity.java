package com.example.kollesnica_power.moviereviewsapi.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.kollesnica_power.moviereviewsapi.R;
import com.example.kollesnica_power.moviereviewsapi.mvp.MainMVP;
import com.example.kollesnica_power.moviereviewsapi.mvp.Presenter;

public class MainActivity extends AppCompatActivity implements MainMVP.view {

    private Presenter mPresenter;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mPresenter = new Presenter(this);

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.rv_reviews);
        setRefreshLayout();

        mPresenter.getReviews();
        mPresenter.setUpAdapter(this, mRecyclerView);



    }


    @Override
    public void hideRefresher() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showToast(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
    }


    private void setRefreshLayout() {

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getReviews();
            }
        });

    }
}
