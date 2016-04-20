package com.rfid.mascir.hello;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mac1 on 3/1/16.
 */
public class DbHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 7;
    private static final String DATABASE_NAME = "mascir.db";
    private static final String TABLE_NAME = "Tag";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TAGID = "tagId";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_LATITUDE = "latitude";
    private static final String COLUMN_LENGITUDE = "langitude";

    public DbHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /*String query = "CREATE TABLE " + TABLE_NAME + " ( " +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
                COLUMN_TAGID + " TEXT , " +
                COLUMN_DATE + " TEXT , " +
                COLUMN_LATITUDE + " TEXT , " +
                COLUMN_LENGITUDE + " TEXT  " +
                 ");"; */
        String query =" CREATE TABLE Tag ( " +
                "    tagId text NOT NULL, " +
                "    date text NOT NULL, " +
                "    born text NOT NULL, " +
                "    latitude text NOT NULL, " +
                "    langitude text NOT NULL, " +
                "    owner text NOT NULL, " +
                "    race text NOT NULL, " +
                "    CONSTRAINT Tag_pk PRIMARY KEY (tagId) " +
                "); " +
                " " +
                "CREATE TABLE Vaccin ( " +
                "    idVaccin integer NOT NULL, " +
                "    type text NOT NULL, " +
                "    date text NOT NULL, " +
                "    status text NOT NULL, " +
                "    remark text NOT NULL, " +
                "    Tag_tagId text NOT NULL, " +
                "    CONSTRAINT Vaccin_pk PRIMARY KEY (idVaccin), " +
                "    CONSTRAINT Vaccin_Tag FOREIGN KEY (Tag_tagId) " +
                "    REFERENCES Tag (tagId) " +
                "); " +
                " " +
                "CREATE TABLE Doctor ( " +
                "    idDoc integer NOT NULL, " +
                "    adress text NOT NULL, " +
                "    dLan text NOT NULL, " +
                "    dLong text NOT NULL, " +
                "    Vaccin_idVaccin integer NOT NULL, " +
                "    CONSTRAINT Doctor_pk PRIMARY KEY (idDoc), " +
                "    CONSTRAINT Doctor_Vaccin FOREIGN KEY (Vaccin_idVaccin) " +
                "    REFERENCES Vaccin (idVaccin) " +
                "); ";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS Doctor");
        db.execSQL("DROP TABLE IF EXISTS Vaccin");
        db.execSQL("DROP TABLE IF EXISTS Tag");
        onCreate(db);
    }

    public void addTag(Tag tag){
        ContentValues values = new ContentValues();
        values.put(COLUMN_TAGID, tag.getTagId());
        values.put(COLUMN_DATE, tag.getDate());
        values.put(COLUMN_LATITUDE, tag.getLatitude());
        values.put(COLUMN_LENGITUDE, tag.getLongitude());
        values.put("owner", tag.getOwner());
        values.put("race", tag.getRace());
        values.put("born", tag.getBorn());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_NAME, null, values);
    }

    public boolean isThere(String tagId){

        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT TagId FROM Tag where tagId = "+tagId;
        Cursor c = db.rawQuery(query , null);

        if (c.getCount() == 0) {
            return false;
        }else return true;
    }

    public Cursor db2string(){
        //String data = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM "+TABLE_NAME+" WHERE 1 ORDER BY date DESC ;";
        Cursor c = db.rawQuery(query , null);
//        c.moveToFirst();
//
//        while (!c.isAfterLast()){
//            if (c.getString(c.getColumnIndex("tagId")) != null) {
//                data +=c.getString(c.getColumnIndex("tagId"));
//                data +=" ";
//            }
//        }


        return c;
    }

    public void closeDb(){
        SQLiteDatabase db = getWritableDatabase();
        db.close();

    }
    public void deleteTag(String tagId){

    }


}
