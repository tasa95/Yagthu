package com.app.android.yagthu.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Object: Grades model
 * Used by: Application Context
 *
 * @author Fllo (Florent Blot) & Tasa (Thierry Allard Saint Albin)
 * @version 1.0
 */
public class Grades implements Parcelable {

    private String name;
    private int grades;
    private String id;
    private String disciplineId;
    private String userId;
    private int semester;
    private String professorName;
    private String coef;
    private String ects;
    private String cc1;
    private String cc2;
    private String test;
    private String type;

    public Grades() { }

    public Grades(String name, int grades, String id,
                  String disciplineId, String userId, int semester, String professorName,
                  String coef, String ects, String cc1, String cc2, String test, String type) {
        this.name = name;
        this.grades = grades;
        this.id = id;
        this.disciplineId = disciplineId;
        this.userId = userId;
        this.semester = semester;
        this.professorName = professorName;
        this.coef = coef;
        this.ects = ects;
        this.cc1 = cc1;
        this.cc2 = cc2;
        this.test = test;
        this.type = type;
    }

    public Grades(Parcel source) {
        this.name = source.readString();
        this.grades = source.readInt();
        this.id = source.readString();
        this.disciplineId = source.readString();
        this.userId = source.readString();
        this.semester = source.readInt();
        this.professorName = source.readString();
        this.coef = source.readString();
        this.ects = source.readString();
        this.cc1 = source.readString();
        this.cc2 = source.readString();
        this.test = source.readString();
        this.type = source.readString();
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getGrades() {
        return grades;
    }
    public void setGrades(int grades) {
        this.grades = grades;
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

    public int getSemester() {
        return semester;
    }
    public void setSemester(int semester) {
        this.semester = semester;
    }

    public String getProfessorName() {
        return professorName;
    }
    public void setProfessorName(String professorName) {
        this.professorName = professorName;
    }

    public String getCoef() {
        return coef;
    }
    public void setCoef(String coef) {
        this.coef = coef;
    }

    public String getEcts() {
        return ects;
    }
    public void setEcts(String ects) {
        this.ects = ects;
    }

    public String getCc1() {
        return cc1;
    }
    public void setCc1(String cc1) {
        this.cc1 = cc1;
    }

    public String getCc2() {
        return cc2;
    }
    public void setCc2(String cc2) {
        this.cc2 = cc2;
    }

    public String getTest() {
        return test;
    }
    public void setTest(String test) {
        this.test = test;
    }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    // Parcelable creator
    public static final Parcelable.Creator<Grades> CREATOR = new Parcelable.Creator<Grades>() {
        @Override
        public Grades createFromParcel(Parcel source) {
            return new Grades(source);
        }

        @Override
        public Grades[] newArray(int size) {
            return new Grades[size];
        }
    };
}
