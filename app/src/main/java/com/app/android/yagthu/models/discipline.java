package com.app.android.yagthu.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Object: Discipline model
 * Used by: Application Context
 *
 * @author Fllo (Florent Blot) & Tasa (Thierry Allard Saint Albin)
 * @version 1.0
 */
public class Discipline implements  Parcelable {

    private String name;
    private ArrayList<Course> courseArrayList;

    public Discipline(String name) {
        this.name = name;
        courseArrayList = new ArrayList();
    }

    public Discipline(Parcel source) {
        this.name = source.readString();
        source.readTypedList(courseArrayList, Course.CREATOR);
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Course> getCourseArrayList() {
        return courseArrayList;
    }
    public void setCourseArrayList(ArrayList<Course> courseArrayList) {
        this.courseArrayList = courseArrayList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeTypedList(courseArrayList);
    }

    // Parcelable creator
    public static final Parcelable.Creator<Discipline> CREATOR = new Parcelable.Creator<Discipline>() {
        @Override
        public Discipline createFromParcel(Parcel source) {
            return new Discipline(source);
        }

        @Override
        public Discipline[] newArray(int size) {
            return new Discipline[size];
        }
    };
}



