package com.example.tp1.services;

import android.util.Log;

import com.example.tp1.business.Coordinates;
import com.example.tp1.business.Place;
import com.example.tp1.events.EventBusManager;
import com.example.tp1.events.SearchResultEvent;
import com.example.tp1.business.geotools.Feature;
import com.example.tp1.business.geotools.FeatureCollection;

import java.util.List;
import java.util.stream.Collectors;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.activeandroid.query.*;

public class RetrofitPlaceService {
    private static final String apiAddress = "https://api-adresse.data.gouv.fr/";

    static Retrofit retrofit = new Retrofit.Builder()
        .client(new OkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("https://api-adresse.data.gouv.fr/")
        .build();
    static GouvAdresseAPI adresseService = retrofit.create(GouvAdresseAPI.class);


    public static void getPlaces(String places)
    {
        // step 1: get places from db (not network)
        getPlacesFromDB(places);

        // step 2 : network call
        Call<FeatureCollection> getPlacesCallback = adresseService.getFeatures(places);
        getPlacesCallback.enqueue(new Callback<FeatureCollection>() {
            @Override
            public void onResponse(Call<FeatureCollection> call, retrofit2.Response<FeatureCollection> response) {
                if(response.body() == null || response.body().features.size() == 0)
                    return;
                //response.body().features.forEach(f -> { placeList.add(f.toPlace());});
                List<Place> placeList = response.body().features.stream().map(Feature::toPlace).collect(Collectors.toList());
                for(Place place : placeList) {
                    place.coordinates.save();
                    place.save();
                }
                getPlacesFromDB(places);
            }

            @Override
            public void onFailure(Call<FeatureCollection> call, Throwable t) {
                Log.e("DebugTP1:MainActivity::onFailure", t.getMessage());
            }
        });
    }


    public static void getPlacesFromDB(String places)
    {
        From query = new Select().all().from(Place.class).join(Coordinates.class).on("Place.coordinates = Coordinates.id").where("label LIKE '%"+places+"%'").limit(5);
        List<Place> placeList = query.execute();
        String sql = query.toSql();

        EventBusManager.BUS.post(new SearchResultEvent(placeList));
    }
}
