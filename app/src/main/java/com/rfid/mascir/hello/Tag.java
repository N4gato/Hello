package com.rfid.mascir.hello;


import android.view.View;
import android.widget.Toast;

/**
 * Created by mac1 on 3/1/16.
 */
public class Tag {
    private String tagId;
    private String date;
    private String born;
    private String race;
    private String owner;
    private double latitude;
    private double longitude;

    public Tag() {

    }

    // Constracter
    public Tag(String tagId, String date) {
        this.tagId = tagId;
        this.date = date;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getTagId() {
        return tagId;
    }

    public String getDate() {
        return date;
    }

    public String getBorn() {
        return born;
    }

    public String getRace() {
        return race;
    }

    public String getOwner() {
        return owner;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setBorn(String born) {
        this.born = born;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

}
