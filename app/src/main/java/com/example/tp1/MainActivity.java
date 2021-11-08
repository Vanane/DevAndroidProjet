package com.example.tp1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.tp1.business.Place;
import com.example.tp1.events.EventBusManager;
import com.example.tp1.events.SearchResultEvent;
import com.example.tp1.services.RetrofitPlaceService;
import com.squareup.otto.Subscribe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    private final int SEARCH_RESULT_CODE = 1;

    @BindView(R.id.listPlaces) RecyclerView recyclerView;
    @BindView(R.id.txtSearchPlace) TextView searchPlace;
    @OnClick(R.id.butGoMap) public void OnButGoMapClick(View view)
    {
        Intent i = new Intent(this, SearchActivity.class);
        i.putExtra("query", searchPlace.getText().toString());
        startActivityForResult(i, SEARCH_RESULT_CODE);
    }
    @OnClick(R.id.butDoSearch) public void OnButSearchClick(View view)
    {
        RetrofitPlaceService.getPlaces(searchPlace.getText().toString());
    }

    PlaceAdapter placeAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        EventBusManager.BUS.register(this);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // check that it is the SecondActivity with an OK result
        if (requestCode == SEARCH_RESULT_CODE) {
            if (resultCode == RESULT_OK) { // Activity.RESULT_OK
                String query = data.getStringExtra("query");
                if(query != null && !query.equals(""))
                    searchPlace.setText(query);
            }
        }
    }

    @Override
    protected void onPause() {
        EventBusManager.BUS.unregister(this);
        super.onPause();
    }


    @Subscribe
    public void searchResult(SearchResultEvent event)
    {
        List<Place> places = (List<Place>) event.getPlaces();
        placeAdapter = new PlaceAdapter(this, places);
        recyclerView.setAdapter(placeAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}