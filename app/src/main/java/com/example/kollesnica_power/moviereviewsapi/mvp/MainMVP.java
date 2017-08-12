package com.example.kollesnica_power.moviereviewsapi.mvp;

import android.support.v7.widget.RecyclerView;

public interface MainMVP {

     interface View {
        void hideRefresher();
        void showToast(String text);
    }

    interface Presenter {
        void getReviews(String query);
        void setUpAdapter(RecyclerView recyclerView);
        void attachView(View view);
        void detachView();
    }

}
