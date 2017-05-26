package com.am.readnews;

/**
 * Created by amatanat on 26.05.17.
 */

public class News {

    private String mTitle;
    private String mPublicationDate;
    private String mUrl;

    public News(String title, String publicationDate, String url){
        this.mTitle = title;
        this.mPublicationDate = publicationDate;
        this.mUrl = url;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmPublicationDate() {
        return mPublicationDate;
    }

    public void setmPublicationDate(String mPublicationDate) {
        this.mPublicationDate = mPublicationDate;
    }

    public String getmUrl() {
        return mUrl;
    }

    public void setmUrl(String mUrl) {
        this.mUrl = mUrl;
    }
}
