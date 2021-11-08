package com.example.tp1.services;

import android.os.AsyncTask;
import android.util.Log;

import com.example.tp1.business.Place;
import com.example.tp1.events.EventBusManager;
import com.example.tp1.events.SearchResultEvent;
import com.example.tp1.business.geotools.Feature;
import com.example.tp1.business.geotools.FeatureCollection;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
@Deprecated
public class PlaceService {
    private static final String apiAddress = "https://api-adresse.data.gouv.fr/";

    public static void getPlaces(String places)
    {
        ArrayList<Place> placesList = new ArrayList<>();

        AsyncTask<String, Void, Response> task = new AsyncTask<String, Void, Response>()
        {
            @Override
            protected void onPostExecute(Response s) {
                super.onPostExecute(s);
                Gson gson = new Gson();
                try {
                    FeatureCollection features = gson.fromJson(s.body().string(), FeatureCollection.class);
                    for (Feature f: features.features)
                        placesList.add(f.toPlace());
                    EventBusManager.BUS.post(new SearchResultEvent(placesList));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected Response doInBackground(String... strings) {
                if(strings.length != 1) return null;
                Response res = null;
                String uri = apiAddress + "search/?q=" + strings[0];
                OkHttpClient client = new OkHttpClient();
                Request req = new Request.Builder()
                        .url(uri)
                        .build();
                Log.d("DebugTP1", "Appel à " + uri);
                try {
                    res = client.newCall(req).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return res;
            }
        };

        task.execute(places);
    }
}
