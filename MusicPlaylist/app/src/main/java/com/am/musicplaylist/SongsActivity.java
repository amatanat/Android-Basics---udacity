package com.am.musicplaylist;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import com.airbnb.lottie.LottieAnimationView;
import com.am.musicplaylist.data.PlaylistContract.PlaylistEntry;

public class SongsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>  {

  private final int LOADER_INIT = 300;
  private final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 200;

  private LottieAnimationView mLottieAnimationView;
  private String mPlaylistName;
  private ListView mListView;
  private SongCursorAdapter mSongCursorAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_songs);

    mLottieAnimationView = (LottieAnimationView) findViewById(R.id.song_emptyview_image);
    mLottieAnimationView.setAnimation("EmptyState.json");
    mLottieAnimationView.loop(true);
    mLottieAnimationView.playAnimation();

    mSongCursorAdapter = new SongCursorAdapter(this, null);
    mListView = (ListView) findViewById(R.id.song_listview);
    mListView.setAdapter(mSongCursorAdapter);

    Intent intent = getIntent();
    if (intent != null) {
      if (intent.hasExtra("playlistName")) {
        mPlaylistName = intent.getStringExtra("playlistName");
        getSupportActionBar().setTitle(mPlaylistName);
      }
    }

    // get emptyview id and set it as emptyview in listview
    View emptyView = findViewById(R.id.song_empty_view);
    mListView.setEmptyView(emptyView);

    getSupportLoaderManager().initLoader(LOADER_INIT, null, this);
  }


  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_song, menu);
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    switch (id) {
      case R.id.new_song:
        // source https://developer.android.com/training/permissions/requesting.html
        if (ContextCompat.checkSelfPermission(SongsActivity.this,
            Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {

          // Should we show an explanation?
          if (ActivityCompat.shouldShowRequestPermissionRationale(SongsActivity.this,
              Manifest.permission.READ_EXTERNAL_STORAGE)) {

            // Show an explanation to the user *asynchronously* -- don't block
            // this thread waiting for the user's response! After the user
            // sees the explanation, try again to request the permission.

          } else {

            // No explanation needed, we can request the permission.

            ActivityCompat.requestPermissions(SongsActivity.this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);

            // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
            // app-defined int constant. The callback method gets the
            // result of the request.
          }
        } else {
          addSong();
        }

        return true;
      case R.id.delete_song:
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  //source https://developer.android.com/training/permissions/requesting.html
  @Override
  public void onRequestPermissionsResult(int requestCode,
      String permissions[], int[] grantResults) {
    switch (requestCode) {
      case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE: {
        // If request is cancelled, the result arrays are empty.
        if (grantResults.length > 0
            && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

          // permission was granted
          addSong();
        }
        return;
      }
    }
  }

  private void addSong() {
    Intent intent = new Intent(Intent.ACTION_PICK,
        android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
    if (intent.resolveActivity(getPackageManager()) != null){
      startActivityForResult(intent, 1);
    }
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    Uri uri;
    if (requestCode == 1) {
      if (data.getData() != null) {
        uri = data.getData();
        Log.i("SongsActivity", "URI..." + uri);
        Cursor musicCursor = getContentResolver().query(uri, null, null, null, null);

        if (musicCursor != null && musicCursor.moveToFirst()) {
          //get columns
          int titleColumn = musicCursor.getColumnIndex
              (android.provider.MediaStore.Audio.Media.TITLE);
          Log.i("SongsActivity", "titleColumn..." + titleColumn);
          int artistColumn = musicCursor.getColumnIndex
              (android.provider.MediaStore.Audio.Media.ARTIST);
          Log.i("SongsActivity", "artistColumn..." + artistColumn);
          //add songs to list
          do {
            String thisTitle = musicCursor.getString(titleColumn);
            String thisArtist = musicCursor.getString(artistColumn);
            insertDataIntoDatabase(thisTitle, thisArtist);
          }
          while (musicCursor.moveToNext());
        }
      }
    }
  }

  private void insertDataIntoDatabase(String title, String artist) {

    ContentValues values = new ContentValues();

    values.put(PlaylistEntry.COLUMN_SONG_TITLE, title);
    values.put(PlaylistEntry.COLUMN_SONG_ARTIST, artist);
    Log.i("SongsActivity", "song title..." + title);
    Log.i("SongsActivity", "song artist..." + artist);

    Uri resultUri = getContentResolver().insert(PlaylistEntry.CONTENT_URI_SONG, values);

    Log.i("SongsActivity", "Result uri in insert method......" + resultUri);
  }

  @Override
  public Loader<Cursor> onCreateLoader(int id, Bundle args) {
    String[] projection = {
        PlaylistEntry._ID,
        PlaylistEntry.COLUMN_SONG_TITLE,
        PlaylistEntry.COLUMN_SONG_ARTIST
    };
    // This loader will execute ContentProvider's query method in a background thread
    return new CursorLoader(this, PlaylistEntry.CONTENT_URI_SONG, projection, null, null, null);
  }

  @Override
  public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
    mSongCursorAdapter.swapCursor(data);
  }

  @Override
  public void onLoaderReset(Loader<Cursor> loader) {
    mSongCursorAdapter.swapCursor(null);
  }
}
