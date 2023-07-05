package com.example.session_cepe.Controller;

import com.example.session_cepe.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

public class UpdateStudentController {
    private String fname;
    private String number;
    private String lname;
    private String school;
    private LocalDate birth;

    @FXML
    private DatePicker birthField;

    @FXML
    private static TextField fnameField;

    @FXML
    private TextField lnameFiled;

    @FXML
    private TextField schoolField;

    public UpdateStudentController(String number, String firstname, String lastname, LocalDate birth, String school) {
        this.number = number;
        this.fname = firstname;
        this.lname = lastname;
        this.school = school;
        this.birth = birth;
    }

    @FXML
    void Cancel(ActionEvent event) {

    }

    @FXML
    void Save(ActionEvent event) {

    }
    static String numEleve;
    public void affiche() throws IOException {
        numEleve  = number;
        fnameField.setText(fname);
        birthField.setValue(birth);
        lnameFiled.setText(lname);
        schoolField.setText(school);
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("UpdateStudent.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage newStage = new Stage();
        newStage.setTitle("Add new Student");
        newStage.setScene(scene);
        newStage.show();
    }


}
