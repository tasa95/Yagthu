package com.app.android.yagthu.models;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;

/**
 * Object: Course model
 * Used by: Application Context
 *
 * @author Fllo (Florent Blot) & Tasa (Thierry Allard Saint Albin)
 * @version 1.0
 */
public class Course implements Parcelable {

    private String name;
    private Date startDate;
    private Date endDate;
    private boolean occured ;
    private String id;
    private String disciplineId ;
    private String userId ;
    private String classId;
    private ArrayList<Document> documentArrayList;

    public Course() { }

    public Course(String name, Date startDate, Date endDate, boolean occured,
                  String id, String disciplineId, String userId, String classId) {
        name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.occured = occured;
        this.id = id;
        this.disciplineId = disciplineId;
        this.userId = userId;
        this.classId = classId;
        documentArrayList = new ArrayList<Document>();
    }

    public Course(Parcel source) {
        this.name = source.readString();
        this.startDate =  new Date( source.readLong());
        this.endDate = new Date( source.readLong());
        this.occured = source.readByte() != 0;
        this.id = source.readString();
        this.disciplineId = source.readString();
        this.userId = source.readString();
        this.classId = source.readString();
        source.readTypedList(documentArrayList,Document.CREATOR);
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        name = name;
    }

    public Date getStartDate() {
        return startDate;
    }
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public boolean isOccured() {
        return occured;
    }
    public void setOccured(boolean occured) {
        this.occured = occured;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getDisciplineId() {
        return disciplineId;
    }
    public void setDisciplineId(String disciplineId) {
        this.disciplineId = disciplineId;
    }

    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getClassId() {
        return classId;
    }
    public void setClassId(String classId) {
        this.classId = classId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeLong(this.startDate.getTime());
        dest.writeLong(this.endDate.getTime());
        dest.writeByte((byte) (occured ? 1 : 0));
        dest.writeString(this.id);
        dest.writeString(this.disciplineId);
        dest.writeString(this.userId);
        dest.writeString(this.classId);
    }

    // Parcelable creator
    public static final Parcelable.Creator<Course> CREATOR = new Parcelable.Creator<Course>() {
        @Override
        public Course createFromParcel(Parcel source) {
            return new Course(source);
        }

        @Override
        public Course[] newArray(int size) {
            return new Course[size];
        }
    };
}
