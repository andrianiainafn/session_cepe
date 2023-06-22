package com.example.session_cepe;

public class Student {
    int number;
    String firstname,lastname,adresse,school;

    public  Student(int number,String firstname,String lastname,String adresse,String school){

    }

    public int getNumber() {
        return this.number;
    }

    public String getAdresse() {
        return this.adresse;
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
