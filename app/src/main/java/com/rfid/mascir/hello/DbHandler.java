package com.rfid.mascir.hello;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mac1 on 3/1/16.
 */
public class DbHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "mascir.db";
    private static final String TABLE_NAME = "tags";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TAGID = "tagId";
    //here some shit
    private static final java.sql.Date COLUMN_DATE = "date";

    public DbHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE" + TABLE_NAME + "(" +
                COLUMN_ID + "INTEGER PRIMARY KEY AUTOINCREMENT" +
                COLUMN_TAGID + "TEXT" +
                COLUMN_DATE + "DATE" +
                 ");";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTE" + TABLE_NAME);
        onCreate(db);

    }

    public void addTag(Tag tag){
        ContentValues values = new ContentValues();
        values.put(COLUMN_TAGID, tag.getTagId());
        values.put(COLUMN_DATE, tag.getDate());

    }

    public void deleteTag(String tagId){

    }


}
