package com.auribises.MyProject.Bean;

import java.io.Serializable;

/**
 * Created by Satvir on 22-May-17.
 */

public class LeavePermission implements Serializable {

    int id;
    String name,rollno,reason;

    public LeavePermission() {
    }

    public LeavePermission(int id, String name, String rollno, String reason) {
        this.id = id;
        this.name = name;
        this.rollno = rollno;
        this.reason = reason;
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

    public String getRollno() {
        return rollno;
    }

    public void setRollno(String rollno) {
        this.rollno = rollno;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "LeavePermission{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", rollno='" + rollno + '\'' +
                ", reason='" + reason + '\'' +
                '}';
    }
}
