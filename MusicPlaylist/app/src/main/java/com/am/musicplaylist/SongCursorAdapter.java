package com.am.musicplaylist;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.am.musicplaylist.data.PlaylistContract.PlaylistEntry;


public class SongCursorAdapter extends CursorAdapter {

  public SongCursorAdapter(Context context, Cursor cursor) {
    super(context, cursor, 0);
  }

  @Override
  public View newView(Context context, Cursor cursor, ViewGroup parent) {
    return LayoutInflater.from(context).inflate(R.layout.song_list_item, parent, false);
  }

  @Override
  public void bindView(View view, final Context context, final Cursor cursor) {

    TextView songTitle = (TextView) view.findViewById(R.id.song_title);
    TextView songArtist = (TextView) view.findViewById(R.id.song_artist);


    // get values of corresponding columns from cursor
    String title = cursor.getString(cursor.getColumnIndexOrThrow(PlaylistEntry.COLUMN_SONG_TITLE));
    String artist = cursor.getString(cursor.getColumnIndexOrThrow(PlaylistEntry.COLUMN_SONG_ARTIST));
    final String id = cursor.getString(cursor.getColumnIndexOrThrow(PlaylistEntry.COLUMN_SONG_PLAYLIST_ID));

    // set text of textviews
    songTitle.setText(title);
    songArtist.setText(artist);

    Button deleteSong = (Button) view.findViewById(R.id.delete_song);
    deleteSong.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        context.getContentResolver().delete(ContentUris.withAppendedId(PlaylistEntry.CONTENT_URI_SONG, Long.parseLong(id)),
            null, null );
      }
    });
  }
}
