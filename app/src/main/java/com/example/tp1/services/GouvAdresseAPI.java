package com.example.tp1.services;

import com.example.tp1.business.geotools.FeatureCollection;

import retrofit2.Call;
import retrofit2.http.*;

public interface GouvAdresseAPI
{
    @GET("search/")
    public Call<FeatureCollection> getFeatures(@Query("q") String search);
}
