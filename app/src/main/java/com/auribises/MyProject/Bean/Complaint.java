package com.auribises.MyProject.Bean;

import java.io.Serializable;

/**
 * Created by Satvir on 22-May-17.
 */

public class Complaint implements Serializable{
    int id;
    String name,roomno,complaint;

    public Complaint() {
    }

    public Complaint(int id, String name, String roomno, String complaint) {
        this.id=id;
        this.name = name;
        this.roomno = roomno;
        this.complaint = complaint;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoomno() {
        return roomno;
    }

    public void setRoomno(String roomno) {
        this.roomno = roomno;
    }

    public String getComplaint() {
        return complaint;
    }

    public void setComplaint(String complaint) {
        this.complaint = complaint;
    }


    @Override
    public String toString() {
        return "Complaint{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", roomno='" + roomno + '\'' +
                ", complaint='" + complaint + '\'' +
                '}';
    }
}
