package com.am.habittracker.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.am.habittracker.data.HabitTrackerContract.HabitTrackerEntry;

/**
 * Created by amatanat on 01.06.17.
 */

public class HabitTrackerDbHelper extends SQLiteOpenHelper{

    // database name
    private static final String DATABASE_NAME = "habits.db";

    // database version
    private static final int DATABASE_VERSION = 1;

    //create table sql statement
    private static final String CREATE_HABITS_TABLE =
            "CREATE TABLE " + HabitTrackerEntry.TABLE_NAME + " (" +
                    HabitTrackerEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    HabitTrackerEntry.COLUMN_HABIT + " TEXT NOT NULL, " +
                    HabitTrackerEntry.COLUMN_NOTE + " TEXT, " +
                    HabitTrackerEntry.COLUMN_REPEAT + " INTEGER)";

    public HabitTrackerDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_HABITS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
