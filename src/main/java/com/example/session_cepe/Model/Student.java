package com.example.session_cepe.Model;

public class Student {
    int number;
    String firstname,lastname,adresse,school;

    public  Student(int number,String firstname,String lastname,String adresse,String school){
        this.number = number;
        this.firstname = firstname;
        this.lastname = lastname;
        this.school = school;
        this.adresse = adresse;

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
