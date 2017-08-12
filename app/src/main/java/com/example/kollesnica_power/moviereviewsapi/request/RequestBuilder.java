package com.example.kollesnica_power.moviereviewsapi.request;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by kollesnica1337 on 12.08.2017.
 */

public class RequestBuilder {

    private static Retrofit build(String url){

        return new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }

    public static GetReviews reviewsRequest(String url){

        return build(url).create(GetReviews.class);

    }

}
