package com.example.nagistest;

import retrofit2.Call;
import retrofit2.http.GET;

public interface TimeApi {
    @GET("1")
    Call<TimeTurkey> getTime();
}




