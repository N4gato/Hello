package com.rfid.mascir.hello;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import java.util.Date;

/**
 * Created by mac1 on 4/20/16.
 */
public class Form extends AppCompatActivity {
    DbHandler dbHandler = new DbHandler(this, null, null, 1);
    Date date = new Date();
    TextView tagId;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form);
        tagId = (TextView) findViewById(R.id.tagId);
        tagId.setEnabled(false);
        //history.setMovementMethod(new ScrollingMovementMethod()); // make it scrollball



        Intent previously = getIntent(); // gets the previously created intent
        String firstKeyName = previously.getStringExtra("tagId"); // will return "FirstKeyValue"
        tagId.setText(firstKeyName);
    }

    public void dialogShow(String title, String message) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
