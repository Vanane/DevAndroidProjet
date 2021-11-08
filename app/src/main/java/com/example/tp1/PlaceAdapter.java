package com.example.tp1;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tp1.business.Place;
import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PlaceAdapter extends RecyclerView.Adapter {
    Context parentContext;
    public class PlaceViewHolder extends RecyclerView.ViewHolder
    {
        Place place;
        @BindView(R.id.placeCity) public TextView city;
        @BindView(R.id.placeStreet) public TextView street;
        @BindView(R.id.placeZipCode) public TextView zipCode;
        @BindView(R.id.placeImage) public ImageView image;

        @OnClick(R.id.placeImage) void imageOnClick(View view)
        {
            AssetFileDescriptor afd = null;
            try {
                afd = view.getContext().getAssets().openFd("click.mp3");

                MediaPlayer player = new MediaPlayer();
                player.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
                player.prepare();
                player.start();

            } catch (IOException e) {
                // Silent catch : sound will not be played
                e.printStackTrace();
            }
        }


        @OnClick(R.id.listItem) void listItemOnClick(View view)
        {
            Intent i = new Intent(parentContext, PlaceDetailActivity.class);
            i.putExtra("city", place.city);
            i.putExtra("type", place.type);
            i.putExtra("street", place.street);
            i.putExtra("zipCode", place.zipCode);
            i.putExtra("lat", place.coordinates.latitude);
            i.putExtra("lon", place.coordinates.longitude);
            parentContext.startActivity(i);
        }

        public PlaceViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setPlace(Place p)
        {
            place = p;
        }
    }


    List<Place> places;
    LayoutInflater inflater;

    public PlaceAdapter(Context context, List<Place> objects)
    {
        parentContext = context;
        inflater = LayoutInflater.from(context);
        places = objects;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new PlaceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Place p = places.get(position);
        ((PlaceViewHolder)holder).setPlace(p);
        ((PlaceViewHolder)holder).city.setText(p.city);
        ((PlaceViewHolder)holder).street.setText(p.street);
        ((PlaceViewHolder)holder).zipCode.setText(p.zipCode);
        switch(p.type)
        {
            case 0:
                ((PlaceViewHolder)holder).image.setImageResource(R.drawable.maison);
                break;
            case 1:
                ((PlaceViewHolder)holder).image.setImageResource(R.drawable.route);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return places.size();
    }
}
