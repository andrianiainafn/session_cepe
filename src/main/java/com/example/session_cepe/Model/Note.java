package com.example.session_cepe.Model;

public class Note {
    String schoolYear,studentNumber,subjectNumber;
    int note;
    public Note(String schoolYear,String studentNumber,String subjectNumber,int note){
        this.schoolYear = schoolYear;
        this.subjectNumber = subjectNumber;
        this.studentNumber = studentNumber;
        this.note = note;
    }

    public String getSchoolYear(){return  this.schoolYear;}
    public String getStudentNumber(){return this.studentNumber;}
    public String getSubjectNumber(){return this.subjectNumber;}
    public int getNote(){return this.note;}

}
