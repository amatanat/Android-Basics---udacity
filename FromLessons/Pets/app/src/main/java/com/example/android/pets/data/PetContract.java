package com.example.android.pets.data;

/*
 * Created by amatanat on 29.05.17.
 */

import android.net.Uri;
import android.provider.BaseColumns;

public final class PetContract {

    // Authority name for the {@link PetProvider}
    public static final String CONTENT_AUTHORITY = "com.example.android.pets";

    /*
     Content Uri without data type
     * Parse string into URI
      */
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    // table name for the {@link PetProvider} Content URI
    public static final String PATH_PETS = "pets";

    private PetContract(){}

    /* Inner class that defines the table contents of the pets table */
    public static final class PetEntry implements BaseColumns {

        // Content URI with the table name. Appends string table name to the base URI
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_PETS);

        // Table name
        public static final String TABLE_NAME = "pets";

        // Column id
        public static final String _ID = BaseColumns._ID;

        // Column name: name
        public static final String COLUMN_PET_NAME = "name";

        // Column name breed
        public static final String COLUMN_PET_BREED = "breed";

        // Column name gender
        public static final String COLUMN_PET_GENDER = "gender";

        // Column name weight
        public static final String COLUMN_PET_WEIGHT = "weight";

        public static final int GENDER_MALE = 1;
        public static final int GENDER_FEMALE = 2;
        public static final int GENDER_UNKNOWN = 0;

    }
}
