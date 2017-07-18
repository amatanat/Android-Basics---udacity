package com.am.musicplaylist.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.am.musicplaylist.data.PlaylistContract.PlaylistEntry;

public class PlaylistDbHelper extends SQLiteOpenHelper {

  public static final String DATABASE_NAME = "playlists.db";

  public static final int DATABASE_VERSION = 1;

  private static final String SQL_CREATE_PLAYLISTS_TABLE =
      "CREATE TABLE " + PlaylistEntry.TABLE_NAME + " (" +
          PlaylistEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
          PlaylistEntry.COLUMN_PLAYLIST_NAME + " TEXT NOT NULL)";
//          ProductEntry.COLUMN_PRODUCT_PRICE + " TEXT NOT NULL," +
//          ProductEntry.COLUMN_PRODUCT_QUANTITY + " INTEGER NOT NULL," +
//          ProductEntry.COLUMN_PRODUCT_SUPPLIER + " TEXT," +
//          ProductEntry.COLUMN_PRODUCT_SUPPLIER_EMAIL + " TEXT NOT NULL," +
//          ProductEntry.COLUMN_PRODUCT_PICTURE + " TEXT)";

  public PlaylistDbHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);

  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    db.execSQL(SQL_CREATE_PLAYLISTS_TABLE);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

  }
}
