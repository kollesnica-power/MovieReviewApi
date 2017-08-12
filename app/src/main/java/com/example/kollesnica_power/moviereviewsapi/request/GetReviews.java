package com.example.kollesnica_power.moviereviewsapi.request;

import com.example.kollesnica_power.moviereviewsapi.model.ReviewModel;
import com.example.kollesnica_power.moviereviewsapi.model.ReviewResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

/**
 * Created by kollesnica1337 on 12.08.2017.
 */

public interface GetReviews {

    @GET("/svc/movies/v2/reviews/search.json")
    Call<ReviewResponse> getData(@Header("api_key") String api_key);

}
