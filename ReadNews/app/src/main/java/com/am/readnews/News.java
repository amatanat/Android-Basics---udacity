package com.am.readnews;

/**
 * Created by amatanat on 26.05.17.
 */

public class News {

    private String mTitle;
    private String mPublicationDate;
    private String mUrl;
    private String mSectionName;

    public News(String title, String publicationDate, String url, String sectionName){
        this.mTitle = title;
        this.mPublicationDate = publicationDate;
        this.mUrl = url;
        this.mSectionName = sectionName;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getPublicationDate() {
        return mPublicationDate;
    }

    public void setPublicationDate(String publicationDate) {
        mPublicationDate = publicationDate;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public String getSectionName() {
        return mSectionName;
    }

    public void setSectionName(String sectionName) {
        mSectionName = sectionName;
    }
}
