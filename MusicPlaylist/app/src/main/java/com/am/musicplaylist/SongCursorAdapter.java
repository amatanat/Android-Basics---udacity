package com.am.musicplaylist;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;
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
    final int id = cursor.getInt(cursor.getColumnIndexOrThrow(PlaylistEntry._ID));

    // set the text of TextViews
    songTitle.setText(title);
    songArtist.setText(artist);

    Button deleteSong = (Button) view.findViewById(R.id.delete_song);
    final int position = cursor.getPosition();

    // delete song button on click
    deleteSong.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        cursor.moveToPosition(position);
        context.getContentResolver().delete(ContentUris.withAppendedId(PlaylistEntry.CONTENT_URI_SONG, id),
            null, null );
        cursor.requery();
        notifyDataSetChanged();
      }
    });
  }
}
