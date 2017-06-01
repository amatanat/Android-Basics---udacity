package com.am.habittracker;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddHabitActivity.class);
                startActivity(intent);
            }
        });

        // get instance of dbhelper
        mHabitTrackerDbHelper = new HabitTrackerDbHelper(this);
    }

    public void onStart(){
        super.onStart();
        readDataFromDB();
    }


    /*
    Read data form db
     */
    private void readDataFromDB(){

        // string array of columns
        String[] projection = {
                HabitTrackerEntry._ID ,
                HabitTrackerEntry.COLUMN_HABIT,
                HabitTrackerEntry.COLUMN_NOTE,
                HabitTrackerEntry.COLUMN_REPEAT

        };

        // get db for reading data
        SQLiteDatabase db = mHabitTrackerDbHelper.getReadableDatabase();

        // get cursor which contains rows and columns
        Cursor cursor = db.query(HabitTrackerEntry.TABLE_NAME, projection, null,null,null,null,null);

        //get index of each column
        int idColumnIndex = cursor.getColumnIndex(HabitTrackerEntry._ID);
        int habitColumnIndex = cursor.getColumnIndex(HabitTrackerEntry.COLUMN_HABIT);
        int noteColumnINdex = cursor.getColumnIndex(HabitTrackerEntry.COLUMN_NOTE);
        int repeatColumnIndex = cursor.getColumnIndex(HabitTrackerEntry.COLUMN_REPEAT);


        try {

            // while cursor has row
            while (cursor.moveToNext()){

                // get values
                String idString = cursor.getString(idColumnIndex);
                int id = Integer.parseInt(idString);
                String habitName = cursor.getString(habitColumnIndex);
                String note = cursor.getString(noteColumnINdex);
                int repeatTime = cursor.getInt(repeatColumnIndex);

                // append text to textview
                mTextView.append(id + " - * " + habitName + " - * " + note + " - * " + repeatTime + "\n");
            }

        } finally {
            // always close cursor when done working with it
            cursor.close();
        }
    }
}
