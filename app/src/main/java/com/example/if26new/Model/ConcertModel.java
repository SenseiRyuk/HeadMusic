package com.example.if26new.Model;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import android.os.Bundle;

@Entity(foreignKeys =
        @ForeignKey(entity = ArtistModel.class,
                parentColumns = "id",
                childColumns = "artistId"))

public class ConcertModel{
    @PrimaryKey
    private int id;
    private int artistId;
    private String date;
    private String location;
    private String locationCity;
    private double locationLat;
    private double locationLgn;
    private String titleConcert;
    private Boolean isNew;

    public ConcertModel(){}
    public ConcertModel(int id, int artistId, String date, String location, String locationCity, double locationLat, double locationLgn, String titleConcert, Boolean isNew) {
        this.id = id;
        this.artistId = artistId;
        this.date = date;
        this.location = location;
        this.locationCity = locationCity;
        this.locationLat = locationLat;
        this.locationLgn = locationLgn;
        this.titleConcert = titleConcert;
        this.isNew = isNew;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getArtistId() {
        return artistId;
    }

    public void setArtistId(int artistId) {
        this.artistId = artistId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocationCity() {
        return locationCity;
    }

    public void setLocationCity(String locationCity) {
        this.locationCity = locationCity;
    }

    public double getLocationLat() {
        return locationLat;
    }

    public void setLocationLat(double locationLat) {
        this.locationLat = locationLat;
    }

    public double getLocationLgn() {
        return locationLgn;
    }

    public void setLocationLgn(double locationLgn) {
        this.locationLgn = locationLgn;
    }

    public String getTitleConcert() {
        return titleConcert;
    }

    public void setTitleConcert(String titleConcert) {
        this.titleConcert = titleConcert;
    }

    public Boolean isNew() {
        return isNew;
    }

    public void setNew(Boolean aNew) {
        isNew = aNew;
    }
}
