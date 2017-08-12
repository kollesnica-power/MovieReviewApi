package com.example.kollesnica_power.moviereviewsapi;

import android.app.Application;

/**
 * Created by kollesnica1337 on 12.08.2017.
 */

public class App extends Application{

    private static App instance;

    public static synchronized App getInstance()
    {
        if (instance == null) {

            instance = new App();

        }

        return instance;
    }

    @Override
    public void onCreate() {

        super.onCreate();
        instance = this;

    }

}
