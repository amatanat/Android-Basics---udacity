package com.example.android.quakereport;

import java.sql.Date;
import java.sql.Time;

/**
 * Created by amatanat on 09.05.17.
 */

public class Earthquake {

    private String mMagnitute;
    private String mLocation;
    private Long mTimeInMilliSeconds;

    public Earthquake(String magnitute, String location, Long timeInMilliSeconds){
        this. mMagnitute = magnitute;
        this.mLocation = location;
        this.mTimeInMilliSeconds = timeInMilliSeconds;
    }


    public String getMagnitute() {
        return mMagnitute;
    }

    public void setMagnitute(String magnitute) {
        mMagnitute = magnitute;
    }

    public String getLocation() {
        return mLocation;
    }

    public void setLocation(String location) {
        mLocation = location;
    }

    public Long getTimeInMilliSeconds() {
        return mTimeInMilliSeconds;
    }

    public void setTimeInMilliSeconds(Long timeInMilliSeconds) {
        mTimeInMilliSeconds = timeInMilliSeconds;
    }
}
