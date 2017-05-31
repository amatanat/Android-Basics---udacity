package com.am.myparcelableapplication;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by amatanat on 31.05.17.
 */

public class Person implements Parcelable {

    private String name;
    private String email;
    private String address;

    public Person(Parcel parcel){
        name = parcel.readString();
        email = parcel.readString();
        address = parcel.readString();
    }

    public Person() {}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(email);
        dest.writeString(address);
    }

    public static final Parcelable.Creator<Person> CREATOR = new Parcelable.Creator<Person>(){

        @Override
        public Person createFromParcel(Parcel source) {
            return new Person(source);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
