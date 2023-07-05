package com.example.session_cepe.Model;

import java.time.LocalDate;
import java.util.Date;

public class Student {
    String firstname,lastname,school,number;
    LocalDate birth;

    public  Student(String number,String firstname,String lastname,String school,LocalDate birth){
        this.number = number;
        this.firstname = firstname;
        this.lastname = lastname;
        this.school = school;
        this.birth = birth;

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
}
