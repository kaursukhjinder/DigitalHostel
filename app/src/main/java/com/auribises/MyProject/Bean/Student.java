package com.auribises.MyProject.Bean;

import android.provider.ContactsContract;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Satvir on 04/05/2017.
 */
//Model,Bean and POJO

public class Student implements Serializable {
    //attributes
    int id;
    String name;
    String rollno;
    String roomno;
    String course,hostel,phone,email,address,state,username,password,adminusername,adminpassword,hostelstatus,complaint;
    String date,time,reason,messdue;


    //constructors

    public Student() {
    }


    public Student(int id, String roomno, String name, String rollno, String course, String hostel, String phone, String email, String address, String state, String username, String password, String adminusername, String adminpassword, String hostelstatus, String complaint, String date, String time,String reason,String messdue) {
        this.id = id;
        this.roomno=roomno;
        this.name = name;
        this.rollno = rollno;
        this.course = course;
        this.hostel = hostel;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.state = state;
        this.username = username;
        this.password = password;
        this.adminusername = adminusername;
        this.adminpassword = adminpassword;
        this.hostelstatus = hostelstatus;
        this.complaint = complaint;
        this.date = date;
        this.time = time;
        this.reason = reason;
        this.messdue = messdue;
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

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHostel() {
        return hostel;
    }

    public void setHostel(String hostel) {
        this.hostel = hostel;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAdminusername() {return adminusername;}

    public void setAdminusername(String adminusername) {this.adminusername = adminusername; }

    public String getAdminpassword() {return adminpassword; }

    public void setAdminpassword(String adminpassword) { this.adminpassword = adminpassword; }

    public String getHostelstatus() {
        return hostelstatus;
    }

    public void setHostelstatus(String hostelstatus) {
        this.hostelstatus = hostelstatus;
    }


    public String getComplaint() {
        return complaint;
    }

    public void setComplaint(String complaint) {
        this.complaint = complaint;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getRoomno() {
        return roomno;
    }

    public void setRoomno(String roomno) {
        this.roomno = roomno;
    }

    public String getMessdue() {
        return messdue;
    }

    public void setMessdue(String messdue) {
        this.messdue = messdue;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", rollno='" + rollno + '\'' +
                ", roomno='" + roomno + '\'' +
                ", course='" + course + '\'' +
                ", hostel='" + hostel + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", state='" + state + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", adminusername='" + adminusername + '\'' +
                ", adminpassword='" + adminpassword + '\'' +
                ", hostelstatus='" + hostelstatus + '\'' +
                ", complaint='" + complaint + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", reason='" + reason + '\'' +
                ", messdue='" + messdue + '\'' +
                '}';
    }
}
