package com.app.android.yagthu.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;


/**
 * Object: User model
 * Used by: Application Context
 *
 * @author Fllo (Florent Blot) & Tasa (Thierry Allard Saint Albin)
 * @version 1.0
 */
public class User implements Parcelable {

    // Elements
    private String  userId;
    private String token;
    private String smartphone_id;
    private String name;
    private String firstName;
    private String login;
    private String username;
    private String email;
    private String photosName;
    private String classId;
    private boolean admin = false,
                    professor = false,
                    emailVerified = false;
    private ArrayList<Attendance>   attendanceArrayList;
//    private ArrayList<Course>       courseArrayList;
    private ArrayList<Grades>       gradesArrayList;
    private ArrayList<Photos>       photosArrayList;

    // Constructor
    public User() { }

    public User(String userId, String token, String smartphone_id, String name,
                String firstName, String login, String username, String email,
                boolean admin, boolean professor, boolean emailVerified, String classId, String photosName) {
        this.userId = userId;
        this.token = token;
        this.smartphone_id = smartphone_id;
        this.name = name;
        this.firstName = firstName;
        this.login = login;
        this.username = username;
        this.email = email;
        this.admin = admin;
        this.professor = professor;
        this.emailVerified = emailVerified;
        this.classId = classId;
        attendanceArrayList = new ArrayList<Attendance>();
//        courseArrayList = new ArrayList<Course>();
        gradesArrayList = new ArrayList<Grades>();
        photosArrayList = new ArrayList<Photos>();
        this.photosName = photosName;
    }

    public User(Parcel source) {
        this.userId = source.readString();
        this.token = source.readString();
        this.smartphone_id = source.readString();
        this.name = source.readString();
        this.firstName = source.readString();
        this.login = source.readString();
        this.username = source.readString();
        this.email = source.readString();
        this.classId = source.readString();
        this.admin = Boolean.parseBoolean(source.readString());
        this.professor = Boolean.parseBoolean(source.readString());
        this.emailVerified = Boolean.parseBoolean(source.readString());

        source.readTypedList(attendanceArrayList, Attendance.CREATOR);
//        source.readTypedList(courseArrayList, Course.CREATOR);
        source.readTypedList(gradesArrayList, Grades.CREATOR);
        source.readTypedList(photosArrayList, Photos.CREATOR);

        this.photosName = source.readString();

    }

    // Getter/setter
    public void setUserId(String userId) { this.userId = userId; }
    public String getUserId() { return this.userId; }

    public void setToken(String token) { this.token = token; }
    public String getToken() { return this.token; }

    public void setSmartphoneId(String smartphone_id) { this.smartphone_id = smartphone_id; }
    public String getSmartphoneId() { return this.smartphone_id; }

    public void setName(String name) { this.name = name; }
    public String getName() { return this.name; }

    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getFirstName() { return this.firstName; }

    public void setLogin(String login) { this.login = login; }
    public String getLogin() { return this.login; }

    public void setUsername(String username) { this.username = username; }
    public String getUsername() { return this.username; }

    public void setEmail(String email) { this.email = email; }
    public String getEmail() { return this.email; }

    public void setClassId(String classId) { this.classId = classId; }
    public String getClassId() { return this.classId; }

    public void setAdmin(boolean admin) { this.admin = admin; }
    public boolean getAdmin() { return this.admin; }

    public void setProfessor(boolean professor) { this.professor = professor; }
    public boolean getProfessor() { return this.professor; }

    public void setEmailVerified(boolean emailVerified) { this.emailVerified = emailVerified; }
    public boolean getEmailVerified() { return this.emailVerified; }

    public ArrayList<Attendance> getAttendanceArrayList() {
        return attendanceArrayList;
    }
    public void setAttendanceArrayList(ArrayList<Attendance> attendanceArrayList) {
        this.attendanceArrayList = attendanceArrayList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    // Write parcel
    @Override
        public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userId);
        dest.writeString(this.token);
        dest.writeString(this.smartphone_id);
        dest.writeString(this.name);
        dest.writeString(this.firstName);
        dest.writeString(this.login);
        dest.writeString(this.username);
        dest.writeString(this.email);
        dest.writeString(this.classId);
        dest.writeByte((byte) (this.admin ? 1 : 0));
        dest.writeByte((byte) (this.professor ? 1 : 0));
        dest.writeByte((byte) (this.emailVerified ? 1 : 0));
        dest.writeTypedList(this.attendanceArrayList);
      //  dest.writeTypedList(this.courseArrayList);
        dest.writeTypedList(this.gradesArrayList);
        dest.writeTypedList(this.photosArrayList);
        dest.writeString(this.photosName);
    }

    // Parcelable creator
    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getPhotosName() {
        return photosName;
    }

    public void setPhotosName(String photosName) {
        this.photosName = photosName;
    }
}
