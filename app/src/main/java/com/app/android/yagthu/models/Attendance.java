package com.app.android.yagthu.models;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Object: Attendance model
 * Used by: Application Context
 *
 * @author Fllo (Florent Blot) & Tasa (Thierry Allard Saint Albin)
 * @version 1.0
 */
public class Attendance implements Parcelable {

    private String id;
    private String userId;
    private String courseId;
    private String dateStart;
    private String dateEnd;
    private String jutified;
    private String courseName;
    private String type;

    public Attendance() { }

    public Attendance(String id, String userId, String courseId, String dateStart,
                      String dateEnd, String jutified, String courseName, String type) {
        this.id = id;
        this.userId = userId;
        this.courseId = courseId;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.jutified = jutified;
        this.courseName = courseName;
        this.type = type;
    }

    public Attendance(Parcel source) {
        this.id = source.readString();
        this.userId =  source.readString();
        this.courseId = source.readString();
        this.dateStart = source.readString();
        this.dateEnd =  source.readString();
        this.jutified = source.readString();
        this.courseName = source.readString();
        this.type = source.readString();
    }

    public String getCourseId() {
        return courseId;
    }
    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDateStart() {
        return dateStart;
    }
    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }
    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getJustified() {
        return jutified;
    }
    public void setJustified(String jutified) {
        this.jutified = jutified;
    }

    public String getCourseName() {
        return courseName;
    }
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.userId);
        dest.writeString(this.courseId);
        dest.writeString(this.dateStart);
        dest.writeString(this.dateEnd);
        dest.writeString(this.jutified);
        dest.writeString(this.courseName);
        dest.writeString(this.type);
    }

    // Parcelable creator
    public static final Parcelable.Creator<Attendance> CREATOR = new Parcelable.Creator<Attendance>() {
        @Override
        public Attendance createFromParcel(Parcel source) {
            return new Attendance(source);
        }

        @Override
        public Attendance[] newArray(int size) {
            return new Attendance[size];
        }
    };
}
