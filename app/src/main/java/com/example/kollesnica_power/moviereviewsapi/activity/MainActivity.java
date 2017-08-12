package com.example.kollesnica_power.moviereviewsapi.activity;

import android.app.SearchManager;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.kollesnica_power.moviereviewsapi.R;
import com.example.kollesnica_power.moviereviewsapi.mvp.MainMVP;
import com.example.kollesnica_power.moviereviewsapi.mvp.Presenter;

public class MainActivity extends AppCompatActivity implements MainMVP.View {

    private Presenter mPresenter;
    private String mQuery = "";
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setRefreshLayout();

        attachPresenter();

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.rv_reviews);
        mPresenter.setUpAdapter(mRecyclerView);

        mPresenter.getReviews("");


    }

    private void attachPresenter() {

        mPresenter = (Presenter) getLastCustomNonConfigurationInstance();

        if (mPresenter == null) {
            mPresenter = new Presenter();
        }

        mPresenter.attachView(this);

    }

    @Override
    protected void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return mPresenter;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);

        // Set up searching View
        final SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        // Add query listener
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String query) {

                mPresenter.getReviews(query);
                mQuery = query;

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        // Make x-button cancel the search
        ImageView closeButton = searchView.findViewById(R.id.search_close_btn);
        closeButton.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {

                // Reset query
                if (!mQuery.isEmpty()) {
                    mQuery = "";
                    mPresenter.getReviews("");
                }else {
                    mQuery = "";
                }

                // Hide search View
                searchView.onActionViewCollapsed();

            }
        });

        return true;
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
                mPresenter.getReviews(mQuery);
            }
        });

    }
}
