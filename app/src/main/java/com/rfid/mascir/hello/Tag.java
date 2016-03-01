package com.rfid.mascir.hello;

import java.util.Date;

/**
 * Created by mac1 on 3/1/16.
 */
public class Tag {
    private int id;
    private String tagId;
    private java.sql.Date date;


    // Constracter
    public Tag(String tagId, java.sql.Date date) {
        this.tagId = tagId;
        this.date = date;
    }
    //setters
    public void setId(int id) {
        this.id = id;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public void setDate(java.sql.Date date) {
        this.date = date;
    }
    //getters
    public int getId() {
        return id;
    }

    public String getTagId() {
        return tagId;
    }

    public java.sql.Date getDate() {
        return date;
    }
}
