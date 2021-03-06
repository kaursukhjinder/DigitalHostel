package com.auribises.MyProject.Bean;

import java.io.Serializable;

/**
 * Created by Satvir on 24-May-17.
 */

public class Entry implements Serializable {
    int id;
    String name;
    String rollno;
    String time;
    String date;

    public Entry() {
    }

    public Entry(int id, String name, String rollno, String time, String date) {
        this.id = id;
        this.name = name;
        this.rollno = rollno;
        this.time = time;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public  String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRollno() {
        return rollno;
    }

    public void setRollno(String rollno) {
        this.rollno = rollno;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Entry{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", rollno='" + rollno + '\'' +
                ", time='" + time + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
