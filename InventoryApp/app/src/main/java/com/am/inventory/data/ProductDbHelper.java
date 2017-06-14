package com.am.inventory.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.am.inventory.data.ProductContract.ProductEntry;

/**
 * Created by amatanat on 09.06.17.
 */

public class ProductDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "products.db";

    public static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_PRODUCTS_TABLE =
            "CREATE TABLE " + ProductEntry.TABLE_NAME + " (" +
                    ProductEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    ProductEntry.COLUMN_PRODUCT_NAME + " TEXT NOT NULL," +
                    ProductEntry.COLUMN_PRODUCT_PRICE + " INTEGER NOT NULL," +
                    ProductEntry.COLUMN_PRODUCT_QUANTITY + " INTEGER NOT NULL," +
                    ProductEntry.COLUMN_PRODUCT_SUPPLIER + " TEXT," +
                    ProductEntry.COLUMN_PRODUCT_SUPPLIER_EMAIL + " TEXT)" ;
                  //  ProductEntry.COLUMN_PRODUCT_PICTURE + " INTEGER NOT NULL)";


    public ProductDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_PRODUCTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
