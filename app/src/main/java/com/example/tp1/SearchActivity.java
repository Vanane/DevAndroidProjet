package com.example.tp1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.tp1.business.Place;
import com.example.tp1.events.EventBusManager;
import com.example.tp1.events.SearchResultEvent;
import com.example.tp1.services.RetrofitPlaceService;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchActivity extends AppCompatActivity implements OnMapReadyCallback {
    @BindView((R.id.txtSearchPlace)) TextView searchPlace;

    @OnClick(R.id.butGoList) public void OnButGoListClick(View view)
    {
        Intent i = new Intent();
        i.putExtra("query", searchPlace.getText().toString());
        setResult(RESULT_OK, i);
        finish();
    }
    @OnClick(R.id.butDoSearch) public void OnButDoSearchClick(View view)
    {
        RetrofitPlaceService.getPlaces(searchPlace.getText().toString());
    }

    GoogleMap activeMap;
    List<Marker> markers;
    String query;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        SupportMapFragment fragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.googleMap);
        fragment.getMapAsync(this);
        query = this.getIntent().getStringExtra("query");
        if(query != null && !query.equals(""))
            searchPlace.setText(query);
        markers = new ArrayList<Marker>();
    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap)
    {
        activeMap = googleMap;
    }


    @Override
    protected void onResume() {
        super.onResume();
        EventBusManager.BUS.register(this);
    }


    @Override
    protected void onPause() {
        EventBusManager.BUS.unregister(this);
        super.onPause();
    }


    @Subscribe
    public void searchResult(SearchResultEvent event)
    {
        clearMarkers();
        event.getPlaces().stream().forEach(p -> markers.add(makePlaceMarker(p)));
    }


    void clearMarkers()
    {
        if(markers == null) return;
        for (Marker marker : markers) {
            marker.remove();
        }
    }

    Marker makePlaceMarker(Place p)
    {
        BitmapDescriptor icon;
        switch(p.type)
        {
            case 0:
                icon = BitmapDescriptorFactory.fromResource(R.drawable.maison);
                break;
            case 1:
                icon = BitmapDescriptorFactory.fromResource(R.drawable.route);
                break;
            default:
                icon = null;
                break;
        }
        MarkerOptions options = new MarkerOptions()
                .position(new LatLng(p.coordinates.latitude, p.coordinates.longitude))
                .title(p.street)
                .snippet(p.zipCode + " " + p.city)
                .icon(icon);
        return activeMap.addMarker(options);
    }
}