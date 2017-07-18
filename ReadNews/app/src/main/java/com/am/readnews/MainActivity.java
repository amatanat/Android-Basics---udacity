package com.am.readnews;

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

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>>{

    // String url to get data from internet
    private final String STRING_URL = "http://content.guardianapis.com/search?section=technology&api-key=84e62d46-a76e-49d0-a15f-cc05c61fba1a";

    // unique id for the loader
    private final int UNIQUE_ID = 1;

    private final List<News> mNewsList = new ArrayList<>();
    private NewsAdapter mNewsAdapter;
    private ProgressBar mProgressBar;
    private RecyclerView mRecycleView;
    private TextView mEmptyStateText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // find progressbar in layout
        mProgressBar = (ProgressBar) findViewById(R.id.loading_spinner);

        //find textview in layout
        mEmptyStateText = (TextView) findViewById(android.R.id.empty);

        //find recycleview
        mRecycleView = (RecyclerView) findViewById(R.id.recycler_view);

        // get instance of {@link NewsAdapter}
        mNewsAdapter = new NewsAdapter(mNewsList);

        // get recycleview layoutmanager
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

        // set recycleview layoutmanager
        mRecycleView.setLayoutManager(mLayoutManager);

        // set item animator in recycleview
        mRecycleView.setItemAnimator(new DefaultItemAnimator());

        // set adapter of recycleview
        mRecycleView.setAdapter(mNewsAdapter);

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
            mEmptyStateText.setVisibility(View.VISIBLE);

            // set text of textview
            mEmptyStateText.setText(R.string.no_internet);

        }
    }

    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {
        return new NewsLoader(this, STRING_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> newsList) {

        // change visibility of progress bar
        mProgressBar.setVisibility(View.GONE);

        // clear news adapter
        mNewsAdapter = new NewsAdapter(new ArrayList<News>());

        // if newslist is not null and is not empty then get instance of NewsAdapter
        // and set it as adapter for the recycleview
        if (newsList != null && !newsList.isEmpty()) {
            mNewsAdapter = new NewsAdapter(newsList);
            mRecycleView.setAdapter(mNewsAdapter);

            checkVisibility();
        }
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        // reset news adapter
        mNewsAdapter = new NewsAdapter(new ArrayList<News>());
    }


    /**
    * Check visibility of required views and change them accordingly
    */
    private void checkVisibility(){

        // if list is empty
        if (mNewsAdapter.getItemCount() == 0){

            // change visibility of recycleview to be 'GONE'
            mRecycleView.setVisibility(View.GONE);

            // change visibility of textview to be 'VISIBLE'
            mEmptyStateText.setVisibility(View.VISIBLE);

            // set text of textview
            mEmptyStateText.setText(R.string.no_news);

        } else {
            // change visibility of recycleview to be 'VISIBLE'
            mRecycleView.setVisibility(View.VISIBLE);

            // change visibility of textview to be 'GONE'
            mEmptyStateText.setVisibility(View.GONE);
        }
    }

}
