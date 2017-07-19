package com.am.musicplaylist;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;
import com.am.musicplaylist.data.PlaylistContract.PlaylistEntry;

public class PlaylistCursorAdapter  extends CursorAdapter {

  public PlaylistCursorAdapter(Context context, Cursor cursor) {
    super(context, cursor, 0);
  }

  @Override
  public View newView(Context context, Cursor cursor, ViewGroup parent) {
    return LayoutInflater.from(context).inflate(R.layout.playlist_list_item, parent, false);
  }

  @Override
  public void bindView(View view, final Context context, final Cursor cursor) {

    // find the TextView with a corresponding id
    TextView playlistName = (TextView) view.findViewById(R.id.tv_playlist_name);

    // get the values of the corresponding column from a cursor
    String name = cursor.getString(cursor.getColumnIndexOrThrow(PlaylistEntry.COLUMN_PLAYLIST_NAME));

    // set the text of a TextView
    playlistName.setText(name);
  }
}
