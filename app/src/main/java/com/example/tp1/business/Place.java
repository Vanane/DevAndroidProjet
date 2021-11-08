package com.example.tp1.business;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.Expose;

@Table(name = "Place")
public class Place extends Model {
    @Expose @Column(name = "label", index = true, unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    public String label;
    @Expose @Column(name = "coordinates")
    public Coordinates coordinates;
    @Expose @Column(name = "street")
    public String street;
    @Expose @Column(name = "zipCode")
    public String zipCode;
    @Expose @Column(name = "city")
    public String  city;
    @Expose @Column(name = "type")
    public int type;


    public Place()
    {
        coordinates = new Coordinates();
    }


    public Place(String lab, double lat, double lon, String str, String zip, String cit, int typ)
    {
        label = lab;
        coordinates = new Coordinates(lat, lon);
        street = str;
        zipCode = zip;
        city = cit;
        type = typ;
    }
}
