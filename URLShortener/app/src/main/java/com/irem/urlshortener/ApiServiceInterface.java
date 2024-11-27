package com.irem.urlshortener;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiServiceInterface {

    @POST("shorten")
    Call<ShortenResponse> shortenUrl(@Body ShortenRequest request);
}
