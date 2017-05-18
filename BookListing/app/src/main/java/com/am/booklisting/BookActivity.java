package com.am.booklisting;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BookActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>>{

    // string url
    private final String REQUEST_URL = "https://www.googleapis.com/books/v1/volumes?q=";

    // unique id for the loader
    private final int UNIQUE_ID = 1;

    private BookAdapter mBookAdapter;
    private ListView mBookListView;
    private ProgressBar mProgressBar;
    private TextView mEmptyStateTextView;

    private String keyword;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        // set layout of the activity
        setContentView(R.layout.activity_book);

        // get user entered data
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            //The key argument here must match that used in the other activity
            keyword = extras.getString("Keyword");
        }

        // find progressbar
        mProgressBar = (ProgressBar) findViewById(R.id.loading_spinner);

        // find listview
        mBookListView = (ListView) findViewById(R.id.list);

        // find textview
        mEmptyStateTextView = (TextView) findViewById(android.R.id.empty);

        // set textview as empty state view for the listview
        mBookListView.setEmptyView(mEmptyStateTextView);

        // create instance of {@link BookAdapter}
        mBookAdapter = new BookAdapter(this, new ArrayList<Book>());

        // set adapter of listview
        mBookListView.setAdapter(mBookAdapter);

        //check network connectivity
        ConnectivityManager cm = (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        if (isConnected){

            // if device connected to the network

            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant  and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(UNIQUE_ID, null, this);

        } else {
            // change visibility of progress bar
            mProgressBar.setVisibility(View.GONE);

            // set text of empty state
            mEmptyStateTextView.setText(R.string.no_internet);
        }
    }

    @Override
    public Loader<List<Book>> onCreateLoader(int id, Bundle args) {
        Log.i("onCreateLoader",  "Get results for this URL .... " + REQUEST_URL + keyword);
        return new BookLoader(BookActivity.this, REQUEST_URL+keyword);
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> books) {

        // change visibility of progress bar
        mProgressBar.setVisibility(View.GONE);

        // set text of empty state
        mEmptyStateTextView.setText(R.string.no_books);

        // clear book adapter
        mBookAdapter.clear();

        if (books != null && !books.isEmpty()){
            mBookAdapter.addAll(books);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        mBookAdapter.clear();
    }
}
