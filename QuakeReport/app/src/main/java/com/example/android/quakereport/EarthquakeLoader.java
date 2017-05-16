package com.example.android.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;

/**
 * Created by amatanat on 16.05.17.
 */

  /*
   * Create public {@link EarthquakeLoader} class which extends {@link AsyncTaskLoader}
  */
public class EarthquakeLoader extends AsyncTaskLoader<List<Earthquake>> {

    private String mUrl;

    public EarthquakeLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }


    /*
     * Trigger the loadInBackground() method to execute
    */
    @Override
    protected void onStartLoading() {
        Log.i("EarthquakeLoader", "Task:" + "onStartLoading is called.....");

        forceLoad();
    }


    @Override
    public List<Earthquake> loadInBackground() {
        // Don't perform the request if there are no URLs, or the first URL is null.
        if (mUrl == null) {
            return null;
        }

        Log.i("EarthquakeLoader", "Task:" + "loadInBackground is called.....");


        // get list of earthquakes
        List<Earthquake> result = QueryUtils.fetchEarthquakeData(mUrl);
        return result;
    }
}
