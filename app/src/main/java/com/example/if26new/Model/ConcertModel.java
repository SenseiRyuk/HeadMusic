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

public class ConcertModel extends AppCompatActivity {
    @PrimaryKey
    private int id;
    private int artistId;
    private String date;
    private String location;
    private double locationLat;
    private double locationLgn;
    private String titleConcert;
    private boolean isNew;


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

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }
}
