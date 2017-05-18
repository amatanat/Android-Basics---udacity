package com.am.booklisting;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BooksActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {

    // string url
    private final String REQUEST_URL = "https://www.googleapis.com/books/v1/volumes?q=";

    // unique id for the loader
    private final int UNIQUE_ID = 1;

    private List<Book> movieList = new ArrayList<>();
    private RecyclerView recyclerView;
    private CustomBookAdapter mAdapter;
    private ProgressBar mProgressBar;
    private TextView mEmptyStateTextView;

    private String keyword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set layout for this activity
        setContentView(R.layout.custom_book_adapter);

        // get user entered data
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            //The key argument here must match that used in the other activity
            keyword = extras.getString("Keyword");
        }

        // find progressbar
        mProgressBar = (ProgressBar) findViewById(R.id.loading_spinner);

        // find textview
        mEmptyStateTextView = (TextView) findViewById(android.R.id.empty);

        // find recycleview
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        // get instance of {@link CustomBookAdapter}
        mAdapter = new CustomBookAdapter(movieList);

        // get recycleview layoutmanager
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

        // set recycleview layoutmanager
        recyclerView.setLayoutManager(mLayoutManager);

        // set item animator in recycleview
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        // set adapter of recycleview
        recyclerView.setAdapter(mAdapter);

        // if keyword is empty modify view visibilities
        if (keyword.isEmpty()){
            mProgressBar.setVisibility(View.GONE);
            mEmptyStateTextView.setVisibility(View.VISIBLE);
            mEmptyStateTextView.setText(R.string.no_books);
        }

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

            // change visibility of textview to be 'VISIBLE'
            mEmptyStateTextView.setVisibility(View.VISIBLE);

            // set text of textview
            mEmptyStateTextView.setText(R.string.no_internet);

        }
    }

    @Override
    public Loader<List<Book>> onCreateLoader(int id, Bundle args) {
        return new BookLoader(this, REQUEST_URL + keyword);
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> books) {
        // change visibility of progress bar
        mProgressBar.setVisibility(View.GONE);

        // clear book adapter
        mAdapter = new CustomBookAdapter(new ArrayList<Book>());

        if (books != null && !books.isEmpty()){
           mAdapter = new CustomBookAdapter(books);
            recyclerView.setAdapter(mAdapter);
            checkVisibility();
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        mAdapter = new CustomBookAdapter(new ArrayList<Book>());
    }

    private void checkVisibility(){
        if (mAdapter.getItemCount() == 0){

            // change visibility of recycleview to be 'GONE'
            recyclerView.setVisibility(View.GONE);

            // change visibility of textview to be 'VISIBLE'
            mEmptyStateTextView.setVisibility(View.VISIBLE);

            // set text of textview
            mEmptyStateTextView.setText(R.string.no_books);
        } else {
            // change visibility of recycleview to be 'VISIBLE'
            recyclerView.setVisibility(View.VISIBLE);

            // change visibility of textview to be 'GONE'
            mEmptyStateTextView.setVisibility(View.GONE);
        }
    }

}
