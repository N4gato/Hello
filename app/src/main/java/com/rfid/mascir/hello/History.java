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
                    buffer.append("Tag ID :" + c.getString(0) + "\n");
                    buffer.append("Date :" + c.getString(1) + "\n");
                    buffer.append("Born :" + c.getString(2) + "\n");
                    buffer.append("Lat :" + c.getString(3) + " | ");
                    buffer.append("Long :" + c.getString(4) + "\n");
                    buffer.append("Owner :" + c.getString(5) + "\n");
                    buffer.append("Race :" + c.getString(6) + "\n\n");
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
                    buffer.append("Tag ID :" + c.getString(0) + "\n");
                    buffer.append("Date :" + c.getString(1) + "\n");
                    buffer.append("Born :" + c.getString(2) + "\n");
                    buffer.append("Lat :" + c.getString(3) + " | ");
                    buffer.append("Long :" + c.getString(4) + "\n");
                    buffer.append("Owner :" + c.getString(5) + "\n");
                    buffer.append("Race :" + c.getString(6) + "\n\n");
                }
                history.setText(buffer.toString());
    }




    protected void OnDestroy(){
        super.onDestroy();
        dbHandler.close();
    }

}


