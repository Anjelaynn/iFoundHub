package com.example.ifoundhub;

public class UserRoleConstructor {

    public String fullname, contactNum, age, email, student_number, year, course, password, userRole;

    public UserRoleConstructor(){

    }

    public UserRoleConstructor(String fullname, String contactNum, String age, String email, String student_number, String year, String course, String password, String userRole) {
        this.fullname = fullname;
        this.contactNum = contactNum;
        this.age = age;
        this.email = email;
        this.student_number = student_number;
        this.year = year;
        this.course = course;
        this.password = password;
        this.userRole = userRole;
    }

    public String getStudent_number() {
        return student_number;
    }

    public void setStudent_number(String student_number) {
        this.student_number = student_number;
    }

}
