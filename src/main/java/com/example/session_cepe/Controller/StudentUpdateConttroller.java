package com.example.session_cepe.Controller;

import com.example.session_cepe.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class StudentUpdateConttroller {
    private String fname;
    private String lname;
    private String school;
    private LocalDate birth;
    private String number;

    @FXML
    private DatePicker birthField;

    @FXML
    private TextField fnameField;

    @FXML
    private TextField lnameFiled;

    @FXML
    private TextField schoolField;
    public StudentUpdateConttroller() {

    }
    public StudentUpdateConttroller(String number, String firstname, String lastname, LocalDate birth, String school) {
        this.number = number;
        this.fname = firstname;
        this.lname = lastname;
        this.birth = birth;
        this.school = school;

    }
    private void setTextField(){
        lnameFiled.setText(this.fname);
    }


    @FXML
    void Cancel(ActionEvent event) {

    }

    @FXML
    void Save(ActionEvent event) {

    }
    public void affiche() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("StudentUpdate.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage newStage = new Stage();
        newStage.setTitle("Update Student");
        newStage.setScene(scene);
        newStage.show();
    }
}
