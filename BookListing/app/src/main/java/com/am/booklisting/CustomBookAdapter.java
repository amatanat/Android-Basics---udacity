package com.am.booklisting;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by amatanat on 18.05.17.
 */

// create custom adapter for {@link RecyclerView}
public class CustomBookAdapter extends RecyclerView.Adapter<CustomBookAdapter.CustomViewHolder> {

    // list of books
    private List<Book> mBookList;

    /*
    * constructor for custom adapter
    */
    public CustomBookAdapter(List<Book> bookList){
        this.mBookList = bookList;
    }

    // view holder
    public class CustomViewHolder extends RecyclerView.ViewHolder {
        private TextView bookName, authors, publishedDate;

        private CustomViewHolder(View view) {
            super(view);
            // find textview ID for book title
            bookName = (TextView) view.findViewById(R.id.book_name);

            // find textview ID for author name
            authors = (TextView) view.findViewById(R.id.author_name);

            // find textview id for published date
            publishedDate = (TextView) view.findViewById(R.id.date);
        }
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // inflate layout for custom adapter
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.book_list, parent, false);

        return new CustomViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int position) {

        // get position of current book
        Book book = mBookList.get(position);

        // set book title
        customViewHolder.bookName.setText(book.getBookName());

        // set book author
        customViewHolder.authors.setText(book.getAuthors());

        // set published date
        customViewHolder.publishedDate.setText(book.getPublishedDate());
    }

    @Override
    public int getItemCount() {
        // get booklist size
        return mBookList.size();
    }

}
