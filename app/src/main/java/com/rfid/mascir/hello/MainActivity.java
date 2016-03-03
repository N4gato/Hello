package com.rfid.mascir.hello;

import android.app.AlertDialog;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Console;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    TextView  textView;
    Button Valid;
    Button history;
    EditText editText;

    String tagId;
    //create the Tag

    GPSTracker gps;

    Tag tag = new Tag();
    Date date = new Date() ;
    DbHandler dbHandler = new DbHandler(this,null,null,1);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        textView =  (TextView) findViewById(R.id.textView);
        Valid = (Button) findViewById(R.id.button);
        history = (Button) findViewById(R.id.history);
        editText = (EditText) findViewById(R.id.editText);

        gps = new GPSTracker(MainActivity.this);


       //s watch.setText(buffer);


    }

    /* (non-Javadoc)
  * @see android.app.Activity#onStart()
  */
    @Override
    protected void onStart() {

        super.onStart();
        valid();
        history();
    }



    public void valid(){
        Valid.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                tagId = String.valueOf(editText.getText());
                tag.setTagId(tagId);
                tag.setDate(String.valueOf(date));

                if (gps.canGetLocation()) {
                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();
                    tag.setLatitude(latitude);
                    tag.setLongitude(longitude);

                    Toast.makeText(
                            getApplicationContext(),
                            "Your Location is -\nLat: " + latitude + "\nLong: "
                                    + longitude, Toast.LENGTH_LONG).show();
                } else {
                    gps.showSettingsAlert();
                }
                ////////////////////////////////////////////////
                dbHandler.addTag(tag);
                Snackbar.make(v, "Tag Serial saved", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                //editText.setText(" ");
            }
        });
    }


    public void history (){
        history.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Cursor c = dbHandler.db2string();
                if (c.getCount() == 0) {
                    //watch.setText("NO DATA AVAILIBALE");
                    dialogShow("Error", "NO DATA AVAILIBALE");
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (c.moveToNext()) {
                    buffer.append("ID :" + c.getString(0) + "\n");
                    buffer.append("TagID :" + c.getString(1) + "\n");
                    buffer.append("DATE :" + c.getString(2) + "\n");
                    buffer.append("Lat :" + c.getString(3) + " | ");
                    buffer.append("Long :" + c.getString(4) + "\n\n");
                }

                dialogShow("History", buffer.toString());

                //show all data
            }
        });

    }


    public void dialogShow(String title,String message){

        AlertDialog.Builder builder = new AlertDialog.Builder(this );
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();


    }

    /* (non-Javadoc)
    * @see android.app.Activity#onPause()
    */
    @Override
    protected void onPause() {
        super.onPause();

    }

    protected void OnDestroy(){
        super.onDestroy();
        dbHandler.close();
        System.out.println("deconnection done");
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}

