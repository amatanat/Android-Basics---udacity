package com.example.android.quakereport;

import java.sql.Date;
import java.sql.Time;

/**
 * Created by amatanat on 09.05.17.
 */

public class Earthquake {

    private double mMagnitude;
    private String mLocation;
    private Long mTimeInMilliSeconds;
    private String mUrl;

    public Earthquake(double magnitude, String location, Long timeInMilliSeconds){
        this. mMagnitude = magnitude;
        this.mLocation = location;
        this.mTimeInMilliSeconds = timeInMilliSeconds;
    }

    public Earthquake(double magnitude, String location, Long timeInMilliSeconds, String url){
        this. mMagnitude = magnitude;
        this.mLocation = location;
        this.mTimeInMilliSeconds = timeInMilliSeconds;
        this.mUrl = url;
    }

    public String getUrl() {
        return mUrl;
    }

    public double getMagnitude() {
        return mMagnitude;
    }

    public String getLocation() {
        return mLocation;
    }

    public Long getTimeInMilliSeconds() {
        return mTimeInMilliSeconds;
    }

}
