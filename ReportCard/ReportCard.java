package com.am.reportstudent;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by amatanat on 05.05.17.
 */

public class ReportCard {

    private final String TAG = "ReportCard";

    // store student's name
    private String mStudentsName;

    // store student's grades
    private ArrayList<String> mGrades;

    // string arraylist that stores student's subjects.
    // number of subjects isn't known
    private ArrayList<String> mSubjects;

    /*
    Class constructor
     */
    public ReportCard(String studentName, ArrayList<String> grades, ArrayList<String> subjects){
        this.mStudentsName = studentName;
        this.mGrades = grades;
        this.mSubjects = subjects;
    }

    public String getmStudentsName() {
        return mStudentsName;
    }

    public void setmStudentsName(String mStudentsName) {
        this.mStudentsName = mStudentsName;
    }

    public ArrayList<String> getmGrades() {
        return mGrades;
    }

    public void setmGrades(ArrayList<String> mGrades) {
        this.mGrades = mGrades;
    }

    public ArrayList<String> getmSubjects() {
        return mSubjects;
    }

    public void setmSubjects(ArrayList<String> mSubjects) {
        this.mSubjects = mSubjects;
    }

    @Override
    public String toString() {
        String msg = "ReportCard{" +
                "mStudentsName='" + mStudentsName + '\'' +
                ", mGrades=" + mGrades +
                ", mSubjects=" + mSubjects +
                '}';
        Log.v(TAG,msg);
        return msg;
    }
}
