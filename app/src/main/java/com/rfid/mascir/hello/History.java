package com.rfid.mascir.hello;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.Date;

/**
 * Created by mac1 on 4/1/16.
 */
public class History extends AppCompatActivity {

    DbHandler dbHandler = new DbHandler(this, null, null, 1);
    Date date = new Date();
    TextView history;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.historylayout);
        history = (TextView) findViewById(R.id.history_TextView);
        history.setMovementMethod(new ScrollingMovementMethod()); // make it scrollball


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        history();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
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
    }

    public void dialogShow(String title, String message) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public void history (){

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
                history.setText(buffer.toString());
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
        getMenuInflater().inflate(R.menu.main_history, menu);
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
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        dialogShow("on","Handle navigation view item clicks here.");
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            //dialogShow("on","Handle the camera action");
            Intent intent = new Intent(this, History.class);
            startActivity(intent);
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    protected void OnDestroy(){
        super.onDestroy();
        dbHandler.close();
    }

}


