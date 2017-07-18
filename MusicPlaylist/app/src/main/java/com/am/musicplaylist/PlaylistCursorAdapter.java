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

    // find tetxview with the corresponding id
    TextView playlistName = (TextView) view.findViewById(R.id.tv_playlist_name);

    // get values of corresponding column from cursor
    String name = cursor.getString(cursor.getColumnIndexOrThrow(PlaylistEntry.COLUMN_PLAYLIST_NAME));

    // set text of textview
    playlistName.setText(name);
  }
}
