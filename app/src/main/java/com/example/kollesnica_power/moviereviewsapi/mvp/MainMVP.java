package com.example.kollesnica_power.moviereviewsapi.mvp;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;

/**
 * Created by kollesnica1337 on 12.08.2017.
 */

public interface MainMVP {

     interface view{
        void hideRefresher();
        void showToast(String text);
    }

    interface presenter{
        void  getReviews();
        void setUpAdapter(Activity activity, RecyclerView recyclerView);
    }

}
