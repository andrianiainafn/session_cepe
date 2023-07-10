package com.example.session_cepe.Model;

import java.time.LocalDate;

public class Result {
    String firstname,lastname,school,number;
    LocalDate birth;
    Double mean;

    public Result(String number,String firstname,String lastname,String school,LocalDate birth,Double mean){
        this.number = number;
        this.firstname = firstname;
        this.lastname = lastname;
        this.school = school;
        this.birth = birth;
        this.mean = mean;

    }

    public String getNumber() {
        return this.number;
    }

    public String getFirstname() {return this.firstname;}

    public String getLastname() {
        return this.lastname;
    }

    public String getSchool() {
        return this.school;
    }

    public LocalDate getBirth(){return this.birth;}
    public Double getMean(){return this.mean;}
}
