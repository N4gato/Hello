package com.rfid.mascir.hello;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
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

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    TextView  textView;
    Button Valid;
    Button history;
    Button test;
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
                Snackbar.make(view, "Sending email ...", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();


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

                String repport = buffer.toString();


                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{"bouayad.n4@gmail.com"});
                email.putExtra(Intent.EXTRA_SUBJECT, "Repport tag " + date);
                email.putExtra(Intent.EXTRA_TEXT, repport);
                email.setType("message/rfc822");
                startActivity(Intent.createChooser(email, "Please choose the trasmission channel"));

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        textView =  (TextView) findViewById(R.id.textView);
        Valid = (Button) findViewById(R.id.button);
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
    }



    public void valid(){
        Valid.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                tagId = String.valueOf(editText.getText());
                editText.setText("");
                tag.setTagId(tagId);
                tag.setDate(String.valueOf(date));

                if(dbHandler.isThere(tagId)){
                    Intent intent = new Intent(MainActivity.this, History.class);
                    startActivity(intent);
                }else{
                    Intent form = new Intent(MainActivity.this, Form.class);
                    form.putExtra("tagId", tagId);
                    startActivity(form);
                    return ;
                }

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
                tag.setBorn("04/14/1993");
                tag.setOwner("otmane Bouayad");
                tag.setRace("Wild");
                ////////////////////////////////////////////////
                dbHandler.addTag(tag);
                Snackbar.make(v, "Tag Serial saved", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                //editText.setText(" ");
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

    protected void OnDestroy(){
        super.onDestroy();
        dbHandler.close();
        System.out.println("deconnection done");
    }

    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    //setings
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            // Handle the setting action
            dialogShow("on","Handle setting");

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        //dialogShow("on","Handle navigation view item clicks here.");
        int id = item.getItemId();

        if (id == R.id.nav_history) {
            // Handle the camera action
            //dialogShow("on","Handle the camera action");
            Intent intent = new Intent(MainActivity.this, History.class);
            startActivity(intent);
        //} else if (id == R.id.nav_gallery) {

        //} else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {
            Cursor c = dbHandler.db2string();
            if (c.getCount() == 0) {
                //watch.setText("NO DATA AVAILIBALE");
                dialogShow("Error", "NO DATA AVAILIBALE");
                return false;
            }
            StringBuffer buffer = new StringBuffer();
            while (c.moveToNext()) {
                buffer.append("ID :" + c.getString(0) + "\n");
                buffer.append("TagID :" + c.getString(1) + "\n");
                buffer.append("DATE :" + c.getString(2) + "\n");
                buffer.append("Lat :" + c.getString(3) + " | ");
                buffer.append("Long :" + c.getString(4) + "\n\n");
            }

            String repport = buffer.toString();


            Intent email = new Intent(Intent.ACTION_SEND);
            email.putExtra(Intent.EXTRA_EMAIL, new String[]{"bouayad.n4@gmail.com"});
            email.putExtra(Intent.EXTRA_TEXT, repport);
            email.putExtra(Intent.EXTRA_SUBJECT, "Repport tag " + date);
            email.setType("message/rfc822");
            startActivity(Intent.createChooser(email, "Please choose the trasmission channel"));

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}



