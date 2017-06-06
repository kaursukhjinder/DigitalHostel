package com.auribises.MyProject.Bean;

import java.io.Serializable;

/**
 * Created by Satvir on 29-May-17.
 */

public class Login implements Serializable {
    int id;
    String name,rollno,roomno,phone,email,password,address,state;

    public Login(int id, String name, String rollno, String roomno, String phone, String email,String password, String address, String state) {
        this.id = id;
        this.name = name;
        this.rollno = rollno;
        this.roomno = roomno;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.address = address;
        this.state = state;
    }

    public Login() {
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

    public String getRoomno() {
        return roomno;
    }

    public void setRoomno(String roomno) {
        this.roomno = roomno;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
        return "Login{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", rollno='" + rollno + '\'' +
                ", roomno='" + roomno + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", address='" + address + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
