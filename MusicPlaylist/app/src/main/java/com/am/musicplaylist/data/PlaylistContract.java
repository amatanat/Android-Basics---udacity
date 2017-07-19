package com.am.musicplaylist.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;


public class PlaylistContract {

  public static final String CONTENT_AUTHORITY = "com.am.musicplaylist";

  /*
   Content Uri without data type
   * Parse string into URI
    */
  public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

  public static final String PATH_PLAYLISTS = "playlists";

  public static final String PATH_SONGS = "songs";

  private PlaylistContract(){}

  public static final class PlaylistEntry implements BaseColumns {

    public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_PLAYLISTS);

    public static final Uri CONTENT_URI_SONG = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_SONGS);

    public static final String TABLE_NAME_PLAYLISTS = "playlists";

    public static final String TABLE_NAME_SONGS = "songs";

    public static final String _ID = BaseColumns._ID;

    public static final String COLUMN_PLAYLIST_NAME = "name";

    public static final String COLUMN_SONG_TITLE = "title";

    public static final String COLUMN_SONG_ARTIST = "artist";

    public static final String COLUMN_SONG_PLAYLIST_ID = "playlist";

    public static final String CONTENT_LIST_TYPE =
        ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PLAYLISTS;
//
//    public static final String CONTENT_ITEM_TYPE =
//        ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PLAYLISTS;

    public static final String CONTENT_SONG_LIST_TYPE =
        ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_SONGS;

    public static final String CONTENT_SONG_ITEM_TYPE =
        ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_SONGS;

  }

}
