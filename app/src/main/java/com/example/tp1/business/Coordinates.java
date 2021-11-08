package com.example.tp1.business;

import com.activeandroid.Model;
import com.activeandroid.annotation.*;
import com.google.gson.annotations.Expose;

@Table(name = "Coordinates")
public class Coordinates extends Model {
    @Expose @Column(name = "latitude", index = true)
    public double latitude;
    @Expose @Column(name = "longitude", index = true)
    public double longitude;


    public Coordinates()
    {

    }

    public Coordinates(double la, double lon)
    {
        latitude = la;
        longitude = lon;
    }


    public double getDistance(Coordinates c1, Coordinates c2)
    {
        return Math.sqrt((c2.latitude > c1.latitude ? c2.latitude - c1.latitude : c1.latitude - c2.latitude)
                + (c2.longitude> c1.longitude? c2.longitude- c1.longitude : c1.longitude - c2.longitude));
    }
}
