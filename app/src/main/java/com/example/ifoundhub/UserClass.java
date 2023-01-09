package com.example.ifoundhub;

public class UserClass {

    public String fullname, contactNum, email, student_number, year, course, password;

    public UserClass(){

    }

    public UserClass(String fullname, String contactNum, String email, String student_number, String year, String course, String password) {
        this.fullname = fullname;
        this.contactNum = contactNum;
        this.email = email;
        this.student_number = student_number;
        this.year = year;
        this.course = course;
        this.password = password;
    }
}