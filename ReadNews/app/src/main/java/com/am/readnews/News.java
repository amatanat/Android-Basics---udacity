package com.am.readnews;

/*
 * Created by amatanat on 26.05.17.
 */

public class News {

    private String mTitle;
    private String mPublicationDate;
    private String mUrl;
    private String mSectionName;
    private String mPosition;

    public News(String title, String publicationDate, String url, String sectionName, String position){
        this.mTitle = title;
        this.mPublicationDate = publicationDate;
        this.mUrl = url;
        this.mSectionName = sectionName;
        this.mPosition = position;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getPublicationDate() {
        return mPublicationDate;
    }

    public String getUrl() {
        return mUrl;
    }

    public String getSectionName() {
        return mSectionName;
    }

    public String getPosition() {
        return mPosition;
    }

}
