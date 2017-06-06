package com.auribises.MyProject.Bean;

/**
 * Created by Aman on 22/05/2017.
 */

public class AllStudent {
    int id;
    String name;
    String rollno;


    String course,roomno,hostel,phone,email,address,state;

    public AllStudent() {
    }

    public AllStudent(int id, String name, String rollno, String course,  String roomno ,String hostel, String phone, String email, String address, String state) {
        this.id = id;
        this.name = name;
        this.rollno = rollno;
        this.course = course;
        this.roomno = roomno;
        this.hostel = hostel;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.state = state;
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

    public String getRoomno() {
        return roomno;
    }

    public void setRoomno(String rollno) {
        this.roomno = roomno;
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

    @Override
    public String toString() {
        return "Details of Student\n"+
                "\nId is: " + id +
                "\nName is: " + name +
                "\nRollno is: "+rollno+
                "\nCourse is:  " +course+
                "\nRoomno is: "+roomno+
                "\nPhone is: " + phone +
                "\nEmail is: " + email +
               "\nAddress is: "+address+
                "\nState is: "+state;

    }
}
