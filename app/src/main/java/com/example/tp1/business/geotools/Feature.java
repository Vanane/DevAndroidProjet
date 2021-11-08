package com.example.tp1.business.geotools;

import com.example.tp1.business.Place;

import java.util.HashMap;
import java.util.Objects;

public class Feature {
    public String type;
    public Geometry geometry;
    public HashMap<String, String> properties;


    public Place toPlace()
    {
        return new Place(
                properties.get("label"),
                geometry.coordinates[1],
                geometry.coordinates[0],
                properties.get("name"),
                properties.get("citycode"),
                properties.get("city"),
                Objects.equals(properties.get("type"), "street") ? 1 : 0
        );
    }
}
