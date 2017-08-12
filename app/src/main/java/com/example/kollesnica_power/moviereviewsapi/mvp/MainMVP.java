package com.example.kollesnica_power.moviereviewsapi.mvp;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;

public interface MainMVP {

     interface view{
        void hideRefresher();
        void showToast(String text);
    }

    interface presenter{
        void getReviews(String query);
        void setUpAdapter(Activity activity, RecyclerView recyclerView);
    }

}
