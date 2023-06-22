package com.example.session_cepe.Controller;

import com.example.session_cepe.Model.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class StudentController implements Initializable {
    @FXML
    private Button addStudent;
    @FXML
    private Button deleteStudent;
    @FXML
    private TextField searchField;
    @FXML
    private Button deleteStudent1;
    @FXML
    private TableView<Student> studenttable;
    @FXML
    private TableColumn<Student, String> actionCol;
    @FXML
    private TableColumn<Student, String> firstnameCol;

    @FXML
    private TableColumn<Student, String> lastnameCol;

    @FXML
    private TableColumn<Student, String> numberCol;

    @FXML
    private TableColumn<Student, String> schoolCol;
    @FXML
    void AddStudent(ActionEvent event) {

    }

    @FXML
    void DeleteStudent(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadData();
    }

    private void loadData() {

    }
}
