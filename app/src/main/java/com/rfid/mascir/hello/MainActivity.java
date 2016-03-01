package com.rfid.mascir.hello;

import android.content.Context;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

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
    EditText editText;
    String tagId;
    Editable tagIdRaw;
    private final static String TAG = "TestActivity";
    String FILENAME = "data";
    FileOutputStream outputStream;

    //just to try
    FileInputStream ifs = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        textView =  (TextView) findViewById(R.id.textView);
        Valid = (Button) findViewById(R.id.button);
        editText = (EditText) findViewById(R.id.editText);





    }

    /* (non-Javadoc)
  * @see android.app.Activity#onStart()
  */
    @Override
    protected void onStart() {

        super.onStart();
        Log.i(TAG, "On Start .....");
        Valid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tagId = String.valueOf(editText.getText());
                //String prepare = String.format("%s", tagId + new Date());
                String prepare = "hello saving " ;
                try {
                    outputStream = openFileOutput(FILENAME, Context.MODE_PRIVATE);
                    outputStream.write(prepare.getBytes());
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Snackbar.make(v, "Tag Serial saved", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                //editText.setText(" ");

            }
        });
    }
    /* (non-Javadoc)
    * @see android.app.Activity#onPause()
    */
    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "On Pause .....");

        try {
            ifs = openFileInput(FILENAME);
            String dataR = String.valueOf(ifs.read());
            System.out.println("data read :"+ Arrays.toString(dataR.getBytes()));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
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


    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "On Destroy .....");
    }


    /* (non-Javadoc)
    * @see android.app.Activity#onRestart()
    */
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "On Restart .....");

    }

    /* (non-Javadoc)
    * @see android.app.Activity#onResume()
    */
    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "On Resume .....");
    }

    /* (non-Javadoc)
    * @see android.app.Activity#onStop()
    */
    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "On Stop .....");
    }
}

