package com.example.tp1.events;

import com.example.tp1.business.Place;

import java.util.List;

public class SearchResultEvent
{
    private List<Place> places;

    public SearchResultEvent(List<Place> places)
    {
        this.places = places;
    }

    public List<Place> getPlaces()
    {
        return places;
    }
}
