package com.example.session_cepe.Model;

public class Subject {
    String number,design;
    int coef;

    public Subject(String number,String design,int coef){
        this.number = number;
        this.design = design;
        this.coef = coef;
    }

    public String getNumber(){return this.number;}
    public String getDesign(){return this.design;}
    public int getCoef(){return this.coef;}
}
