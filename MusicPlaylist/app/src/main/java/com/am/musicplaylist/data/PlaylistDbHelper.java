package com.am.musicplaylist.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.am.musicplaylist.data.PlaylistContract.PlaylistEntry;

public class PlaylistDbHelper extends SQLiteOpenHelper {

  public static final String DATABASE_NAME = "playlists.db";

  public static final int DATABASE_VERSION = 1;

  private static final String SQL_CREATE_PLAYLISTS_TABLE =
      "CREATE TABLE " + PlaylistEntry.TABLE_NAME_PLAYLISTS + " (" +
          PlaylistEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
          PlaylistEntry.COLUMN_PLAYLIST_NAME + " TEXT NOT NULL, " +
          " FOREIGN KEY (" + PlaylistEntry.COLUMN_PLAYLIST_NAME + ") REFERENCES " +
          PlaylistEntry.TABLE_NAME_SONGS + "(" + PlaylistEntry.COLUMN_SONG_TITLE + "));";

  private static final String SQL_CREATE_SONGS_TABLE =
      "CREATE TABLE " + PlaylistEntry.TABLE_NAME_SONGS + " (" +
          PlaylistEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
          PlaylistEntry.COLUMN_SONG_TITLE +  " TEXT NOT NULL, " +
          PlaylistEntry.COLUMN_SONG_PLAYLIST_ID + " TEXT NOT NULL, " +
          PlaylistEntry.COLUMN_SONG_ARTIST + " TEXT NOT NULL)";


  public PlaylistDbHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);

  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    db.execSQL(SQL_CREATE_PLAYLISTS_TABLE);
    db.execSQL(SQL_CREATE_SONGS_TABLE);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    // ignore update
  }
}
