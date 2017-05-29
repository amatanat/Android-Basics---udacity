package com.am.readnews;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/*
 * Created by amatanat on 26.05.17.
 */

public class NewsLoader extends AsyncTaskLoader<List<News>> {

    private String mUrl;

    public NewsLoader(Context context, String url){
        super(context);
        mUrl = url;
    }

    protected void onStartLoading(){
        forceLoad();
    }

    @Override
    public List<News> loadInBackground() {

        // check if string url is null or not
        if (mUrl == null){
            return null;
        }

        // get list of {@link News} from given url

        return Query.fetchData(mUrl);
    }
}
