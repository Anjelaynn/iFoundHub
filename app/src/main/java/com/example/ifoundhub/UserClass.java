package com.example.ifoundhub;

public class UserClass {

    public String fullname, contactNum, age, email, student_number, year, course, password, as;

    public UserClass(){

    }

    public UserClass(String fullname, String contactNum, String age, String email, String student_number, String year, String course, String password, String as) {
        this.fullname = fullname;
        this.contactNum = contactNum;
        this.age = age;
        this.email = email;
        this.student_number = student_number;
        this.year = year;
        this.course = course;
        this.password = password;
        this.as = as;
    }
}