package com.am.booklisting;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by amatanat on 18.05.17.
 */

public class BookLoader extends AsyncTaskLoader<List<Book>> {

    private String mUrl;

    public BookLoader(Context context, String url){
        super(context);
        mUrl = url;
    }

    protected void onStartLoading(){
        forceLoad();
    }

    @Override
    public List<Book> loadInBackground() {

        // check if string url is mull or not
        if (mUrl == null){
            return null;
        }

        // get list of {@link Book} from given url
        List<Book> bookList = SearchData.fetchBookData(mUrl);

        return bookList;
    }
}
