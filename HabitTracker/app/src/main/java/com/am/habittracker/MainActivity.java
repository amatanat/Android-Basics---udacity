package com.am.habittracker;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.am.habittracker.data.HabitTrackerContract.HabitTrackerEntry;
import com.am.habittracker.data.HabitTrackerDbHelper;

public class MainActivity extends AppCompatActivity {

    private HabitTrackerDbHelper mHabitTrackerDbHelper ;

    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // find tetxview
        mTextView = (TextView) findViewById(R.id.textview_id);

        // get instance of dbhelper
        mHabitTrackerDbHelper = new HabitTrackerDbHelper(this);

        insertDataIntoDB();
        readDataFromDB();

    }

    /*
    Insert data into db
     */
    private void insertDataIntoDB(){

        // get writeable db for intersting data
        SQLiteDatabase db = mHabitTrackerDbHelper.getWritableDatabase();

        //get instance of content values
        ContentValues contentValues = new ContentValues();

        // put values for columns
        contentValues.put(HabitTrackerEntry.COLUMN_HABIT, "Drink water");
        contentValues.put(HabitTrackerEntry.COLUMN_REPEAT, "2L per day");
        contentValues.put(HabitTrackerEntry.COLUMN_PROGRESS, 2);

        // insert values into table
        long rowID = db.insert(HabitTrackerEntry.TABLE_NAME, null, contentValues);

        if (rowID == -1) {
            Log.e("MainActivity", "Error in inserting data....");
        } else {
            Log.e("MainActivity","row id...." + rowID);
        }
    }

    /*
    Read data form db
     */
    private void readDataFromDB(){

        // string array of columns
        String[] projection = {
                HabitTrackerEntry._ID ,
                HabitTrackerEntry.COLUMN_HABIT ,
                HabitTrackerEntry.COLUMN_REPEAT ,
                HabitTrackerEntry.COLUMN_PROGRESS
        };

        // get db for reading data
        SQLiteDatabase db = mHabitTrackerDbHelper.getReadableDatabase();

        // get cursor which contains rows and columns
        Cursor cursor = db.query(HabitTrackerEntry.TABLE_NAME, projection, null,null,null,null,null);

        // set text of textview
        mTextView.setText("Number of rows...."  + cursor.getCount() + "\n");

        //get index of each column
        int idColumnIndex = cursor.getColumnIndex(HabitTrackerEntry._ID);
        int habitColumnIndex = cursor.getColumnIndex(HabitTrackerEntry.COLUMN_HABIT);
        int repeatColumnIndex = cursor.getColumnIndex(HabitTrackerEntry.COLUMN_REPEAT);
        int progressColumnIndex = cursor.getColumnIndex(HabitTrackerEntry.COLUMN_PROGRESS);

        try {

            // while cursor has row
            while (cursor.moveToNext()){

                // get values
                String idString = cursor.getString(idColumnIndex);
                int id =Integer.parseInt(idString);
                String habitName = cursor.getString(habitColumnIndex);
                String repeatTime = cursor.getString(repeatColumnIndex);
                String progressAmount = cursor.getString(progressColumnIndex);
                int progress = Integer.parseInt(progressAmount);

                // append text to textview
                mTextView.append(id + " - " + habitName + " - " + repeatTime + " - " + progress + "\n");
            }

        } finally {
            // always close cursor when done working with it
            cursor.close();
        }

    }
}
