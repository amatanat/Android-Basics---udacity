package com.am.booklisting;

import java.util.ArrayList;

/**
 * Created by amatanat on 17.05.17.
 */

public class Book {

    // name of the book
    private String mBookName;

    // list of the authors
    private ArrayList<String> mAuthors;

    // book's published date
    private String mPublishedDate;


    public Book(String bookName, ArrayList<String> authors, String publishedDate){
        this.mBookName = bookName;
        this.mAuthors = authors;
        this.mPublishedDate = publishedDate;
    }

    // get book name
    public String getBookName() {
        return mBookName;
    }

    // get published date
    public String getPublishedDate() {
        return mPublishedDate;
    }

    // get list of authors
    public String getAuthors() {
        String authors = getAuthorsString();
        return authors;
    }

    private String getAuthorsString(){
        String autors = mAuthors.get(0);
        for(int i = 1; i < mAuthors.size(); i++){
            autors += "," + mAuthors.get(i);
        }
        return autors;
    }
}
