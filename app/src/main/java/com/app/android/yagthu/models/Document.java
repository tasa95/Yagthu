package com.app.android.yagthu.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Object: Documents model
 * Used by: Application Context
 *
 * @author Fllo (Florent Blot) & Tasa (Thierry Allard Saint Albin)
 * @version 1.0
 */
public class Document implements Parcelable {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    private String name ;
    private String id;
    private String courseId;

    public Document(String name, String id, String courseId) {
        this.name = name;
        this.id = id;
        this.courseId = courseId;
    }

    public Document()
    {}


    public Document(Parcel source) {
        this.name = source.readString();
        this.id =  source.readString();
        this.courseId =  source.readString();
    }




    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.id);
        dest.writeString(this.courseId);
    }

    // Parcelable creator
    public static final Parcelable.Creator<Document> CREATOR = new Parcelable.Creator<Document>() {
        @Override
        public Document createFromParcel(Parcel source) {
            return new Document(source);
        }

        @Override
        public Document[] newArray(int size) {
            return new Document[size];
        }
    };



}
