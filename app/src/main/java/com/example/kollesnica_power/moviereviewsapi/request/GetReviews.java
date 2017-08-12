package com.example.kollesnica_power.moviereviewsapi.request;

import com.example.kollesnica_power.moviereviewsapi.model.ReviewResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface GetReviews {

    @GET("/svc/movies/v2/reviews/search.json")
    Call<ReviewResponse> getData(@Header("api_key") String api_key, @Query("query")String query);

}
