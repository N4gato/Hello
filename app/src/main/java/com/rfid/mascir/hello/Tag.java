package com.rfid.mascir.hello;


import android.view.View;
import android.widget.Toast;

/**
 * Created by mac1 on 3/1/16.
 */
public class Tag {
    private int id;
    private String tagId;
    private String date;
    private double latitude;
    private double longitude;

    public Tag() {

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

    public double getLongitude() {
        return longitude;
    }

    // Constracter
    public Tag(String tagId, String date) {
        this.tagId = tagId;
        this.date = date;
    }
    //setters

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public void setDate(String date) {
        this.date = date;
    }

    //getters
    public int getId() {
        return id;
    }

    public String getTagId() {
        return tagId;
    }

    public String getDate() {
        return date;
    }
}
