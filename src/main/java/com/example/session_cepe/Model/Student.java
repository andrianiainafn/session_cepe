package com.example.session_cepe.Model;

public class Student {
    int number;
    String firstname,lastname,school;

    public  Student(int number,String firstname,String lastname,String school){
        this.number = number;
        this.firstname = firstname;
        this.lastname = lastname;
        this.school = school;

    }

    public int getNumber() {
        return this.number;
    }

    public String getFirstname() {
        return this.firstname;
    }

    public String getLastname() {
        return this.lastname;
    }

    public String getSchool() {
        return this.school;
    }
}
