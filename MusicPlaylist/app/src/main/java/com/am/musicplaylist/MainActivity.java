package com.am.musicplaylist;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.airbnb.lottie.LottieAnimationView;
import com.am.musicplaylist.data.PlaylistContract.PlaylistEntry;
import es.dmoral.toasty.Toasty;

public class MainActivity extends AppCompatActivity implements
    LoaderManager.LoaderCallbacks<Cursor> {

  private final String LOG_TAG = MainActivity.class.getName();
  private final int LOADER_INIT = 100;
  private final String PLAYLIST_NAME = "playlistName";

  private LottieAnimationView mLottieAnimationView;
  private String mPlaylistName;
  private ListView mListView;
  private PlaylistCursorAdapter mPlaylistCursorAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    // get {@link LottieAnimationView} and set its animation
    mLottieAnimationView = (LottieAnimationView) findViewById(R.id.emptyview_image);
    mLottieAnimationView.setAnimation("EmptyState.json");
    mLottieAnimationView.loop(true);
    mLottieAnimationView.playAnimation();

    mPlaylistCursorAdapter = new PlaylistCursorAdapter(this, null);

    mListView = (ListView) findViewById(R.id.listview);

    // set adapter of ListView
    mListView.setAdapter(mPlaylistCursorAdapter);

    // ListView item click
    mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        //  open {@link SongsActivity} on ListView item (playlist name) click
        Intent intent = new Intent(MainActivity.this, SongsActivity.class);

        // get playlist name from TextView
        String playlistName =  ((TextView) view.findViewById(R.id.tv_playlist_name)).getText().toString();

        // send playlist name with intent
        intent.putExtra(PLAYLIST_NAME, playlistName);

        // send Content Uri with the id of the clicked item
        intent.setData(ContentUris.withAppendedId(PlaylistEntry.CONTENT_URI, id));
        startActivity(intent);
      }
    });

    // get RecyclerView id and set it as emptyview in ListView
    View emptyView = findViewById(R.id.empty_view);
    mListView.setEmptyView(emptyView);

    // initialize loader
    getSupportLoaderManager().initLoader(LOADER_INIT, null, this);
  }


  @Override
  public Loader<Cursor> onCreateLoader(int id, Bundle args) {
    String[] projection = {
        PlaylistEntry._ID,
        PlaylistEntry.COLUMN_PLAYLIST_NAME
    };
    // This loader will execute ContentProvider's (PlaylistProvider) query method in a background thread
    return new CursorLoader(this, PlaylistEntry.CONTENT_URI, projection, null, null, null);
  }

  @Override
  public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
    mPlaylistCursorAdapter.swapCursor(data);
  }

  @Override
  public void onLoaderReset(Loader<Cursor> loader) {
    mPlaylistCursorAdapter.swapCursor(null);
  }


  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem menuItem) {
    switch (menuItem.getItemId()) {
      case R.id.new_playlist:

        // create new playlist
        showCreatePlaylistDialog();

        return true;
      case R.id.delete_playlists:

        // delete all playlists
        deleteDataFromDatabase();

        return true;
      default:
        return super.onOptionsItemSelected(menuItem);
    }
  }

  /**
   * This method is used to create new playlist
   */
  private void showCreatePlaylistDialog() {

    AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
    alertDialog.setTitle(R.string.playlist);

    final EditText input = new EditText(MainActivity.this);
    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.MATCH_PARENT,
        LinearLayout.LayoutParams.MATCH_PARENT);
    input.setLayoutParams(lp);
    input.setHint(R.string.playlist_name);
    alertDialog.setView(input);

    alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {

      public void onClick(DialogInterface dialog, int id) {

        // User clicked the "OK" button
        // check entered playlist name

        String inputData = input.getText().toString().trim();

        //if input is empty show toast
        if (TextUtils.isEmpty(inputData)) {

          // show error toast message
          Toasty.error(MainActivity.this, "Please enter title. Playlist title cannot be empty",
                Toast.LENGTH_SHORT, true).show();
        } else {

          // if input isn't empty set playlist name and insert into db

          mPlaylistName = inputData;
          insertDataIntoDatabase(mPlaylistName);
        }
      }
    });

    alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

      public void onClick(DialogInterface dialog, int id) {

        //  User clicked the "Cancel" button, so dismiss the dialog
        if (dialog != null) {
          dialog.dismiss();
        }
      }
    });
    alertDialog.show();
  }

  /**
   * This method is used to insert string as playlist name into db in 'playlist' table
   * @param data - is the to be inserted  playlist name
   */
  private void insertDataIntoDatabase(String data) {

    ContentValues values = new ContentValues();

    values.put(PlaylistEntry.COLUMN_PLAYLIST_NAME, data);

    Uri resultUri = getContentResolver().insert(PlaylistEntry.CONTENT_URI, values);

    Log.i(LOG_TAG, "Result uri in insert method......" + resultUri);
  }

  /**
   * This method is used to delete all playlists from db
   */
  private void deleteDataFromDatabase() {
    getContentResolver().delete(PlaylistEntry.CONTENT_URI, null, null);
  }
}
