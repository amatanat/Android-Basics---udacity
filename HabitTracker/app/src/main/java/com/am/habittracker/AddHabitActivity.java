package com.am.habittracker;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.am.habittracker.data.HabitTrackerContract.HabitTrackerEntry;
import com.am.habittracker.data.HabitTrackerDbHelper;

public class AddHabitActivity extends AppCompatActivity {

    private HabitTrackerDbHelper mHabitTrackerDbHelper ;

    private EditText mHabitName;
    private EditText mHabitNote;
    private Spinner  mRepeatSpinner;

    private int mRepeatNumber = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_habit);

        // get views
        mHabitName = (EditText)findViewById(R.id.habit_name);
        mHabitNote = (EditText) findViewById(R.id.habit_note);
        mRepeatSpinner=(Spinner) findViewById(R.id.repeat_spinner);

        // get instance of db helper
        mHabitTrackerDbHelper = new HabitTrackerDbHelper(this);

        setupSpinner();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //inflate menu xml file
        getMenuInflater().inflate(R.menu.menu_add_habit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {

            // Respond to a click on the "Save" menu option
            case R.id.save:

                //insert user entered data into db
                insertDataIntoDB();

                // exit this activity
                finish();

                return true;

            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:

                // Navigate back to parent activity (CatalogActivity)
                NavUtils.navigateUpFromSameTask(this);

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupSpinner(){

        Integer[] items = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> repeatAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, items);

        repeatAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        mRepeatSpinner.setAdapter(repeatAdapter);

        mRepeatSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mRepeatNumber = position + 1;
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mRepeatNumber = 1;
            }
        });
    }

    /*
   Insert data into db
    */
    private void insertDataIntoDB(){

        // get writable db for intersting data
        SQLiteDatabase db = mHabitTrackerDbHelper.getWritableDatabase();

        //get instance of content values
        ContentValues contentValues = new ContentValues();

        // put values for columns
        contentValues.put(HabitTrackerEntry.COLUMN_HABIT, mHabitName.getText().toString().trim());
        contentValues.put(HabitTrackerEntry.COLUMN_NOTE, mHabitNote.getText().toString().trim());
        contentValues.put(HabitTrackerEntry.COLUMN_REPEAT, mRepeatNumber);


        // insert values into table
        long rowID = db.insert(HabitTrackerEntry.TABLE_NAME, null, contentValues);

        if (rowID == -1) {
            Log.e("MainActivity", "Error in inserting data....");
        } else {
            Log.e("MainActivity","row id...." + rowID);
        }
    }
}
