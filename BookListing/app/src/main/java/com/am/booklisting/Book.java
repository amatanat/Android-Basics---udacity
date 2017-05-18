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

    // book description
    private String mDescription;

    // info url of the book
    private String mUrl;

    /*
    * Constructor for the class
    */
    public Book(String bookName, String publishedDate, String description, String url, ArrayList<String> authors){
        this.mBookName = bookName;
        this.mPublishedDate = publishedDate;
        this.mDescription = description;
        this.mUrl = url;
        this.mAuthors = authors;
    }

    public Book(String bookName, ArrayList<String> authors){
        this.mBookName = bookName;
        this.mAuthors = authors;
    }

    // get book name
    public String getBookName() {
        return mBookName;
    }

    // get published date
    public String getPublishedDate() {
        return mPublishedDate;
    }

    // get description
    public String getDescription() {
        return mDescription;
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

    // get url
    public String getUrl() {
        return mUrl;
    }
}
