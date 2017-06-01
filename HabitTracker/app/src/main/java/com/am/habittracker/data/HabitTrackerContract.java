package com.am.habittracker.data;

import android.provider.BaseColumns;

/**
 * Created by amatanat on 01.06.17.
 */

public class HabitTrackerContract {

    private HabitTrackerContract(){}

    public static final class HabitTrackerEntry implements BaseColumns{

        // constants for table name and columns

        public static final String TABLE_NAME = "habits";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_HABIT= "Habit";
        public static final String  COLUMN_NOTE = "Note";
        public static final String COLUMN_REPEAT = "Repeat";

    }
}
