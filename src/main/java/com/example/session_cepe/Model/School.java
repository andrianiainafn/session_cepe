package com.example.session_cepe.Model;

public class School {
    String number,design,adresse;

    public School(String number,String design,String adresse){
        this.number = number;
        this.design = design;
        this.adresse = adresse;
    }

    public String getNumber(){return this.number;}
    public String getDesign(){return this.design;}
    public String getAdresse(){return this.adresse;}

}
