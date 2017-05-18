package com.am.booklisting;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amatanat on 18.05.17.
 */

public class BookAdapter extends ArrayAdapter<Book> {

    // Initialize ArrayAdapter's internal storage for the context and the list
    // second argument is used when ArrayAdapter is population the textview
    // since this is a custom adapter for two the textviews, adapter isn't goind to use second argument.
    public BookAdapter(@NonNull Context context, List<Book> bookList) {
        super(context, 0, bookList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.book_list, parent, false);
        }

        // Get the {@link Book} object located at this position in the list
        Book currentBook = getItem(position);

        // find textview for the authors name in xml file
        TextView mAuthorName = (TextView) listItemView.findViewById(R.id.author_name);

        // get list of authors for current book
        ArrayList<String> authors = currentBook.getAuthors();

        // get instance of {@link StringBuilder} then add authors list to it
        // convert {@link StringBuilder} to string add set it as a text for textview
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(authors);
        mAuthorName.setText(stringBuilder.toString());

        // find textview for book's name and set it's text for current book
        TextView mBookName = (TextView) listItemView.findViewById(R.id.book_name);
        mBookName.setText(currentBook.getBookName());

        // return list item view
        return listItemView;
    }

}
