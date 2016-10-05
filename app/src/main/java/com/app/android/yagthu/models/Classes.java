package com.app.android.yagthu.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Object: Class model
 * Used by: Application Context
 *
 * @author Fllo (Florent Blot) & Tasa (Thierry Allard Saint Albin)
 * @version 1.0
 */
public class Classes implements Parcelable {

    private String name;
    private String classId;
    private ArrayList<Course> courseArrayList;

    public Classes() { }

    public Classes(String name, String classId) {
        this.courseArrayList = new ArrayList<Course>();
        this.name = name;
        this.classId = classId;
    }

    public Classes(ArrayList<Course> courseArrayList, String name, String classId) {
        this.courseArrayList = courseArrayList;
        this.name = name;
        this.classId = classId;
    }

    public Classes(Parcel source) {
        this.name = source.readString();
        this.classId = source.readString();
        source.readTypedList(this.courseArrayList , Course.CREATOR);
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getClassId() {
        return classId;
    }
    public void setClassId(String classId) {
        this.classId = classId;
    }

    public ArrayList<Course> getCourseArrayList() {
        return courseArrayList;
    }
    public void setCourseArrayList(ArrayList<Course> courseArrayList) {
        this.courseArrayList = courseArrayList;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.classId);
        dest.writeTypedList(courseArrayList);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    // Parcelable creator
    public static final Parcelable.Creator<Classes> CREATOR = new Parcelable.Creator<Classes>() {
        @Override
        public Classes createFromParcel(Parcel source) {
            return new Classes(source);
        }

        @Override
        public Classes[] newArray(int size) {
            return new Classes[size];
        }
    };
}
