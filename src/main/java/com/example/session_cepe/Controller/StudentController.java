package com.example.session_cepe.Controller;

import com.example.session_cepe.DbConnection;
import com.example.session_cepe.Model.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    private TableColumn<Student, String> adresseCol;
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
    String query = null;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Student student = null;
    ObservableList<Student> studentList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            loadData();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadData() throws ClassNotFoundException, SQLException {

        connection = DbConnection.getCon();
        refreshTable();
        numberCol.setCellValueFactory(new PropertyValueFactory<>("number"));
        schoolCol.setCellValueFactory(new PropertyValueFactory<>("school"));
        firstnameCol.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        lastnameCol.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        adresseCol.setCellValueFactory(new PropertyValueFactory<>("adresse"));

    }
    private void refreshTable() throws SQLException {
        studentList.clear();
        query = "SELECT * FROM etudiant";
        preparedStatement = connection.prepareStatement(query);
        resultSet = preparedStatement.executeQuery();

        while(resultSet.next()){
            studentList.add(new Student(
                    resultSet.getString("numero"),
                    resultSet.getString("school"),
                    resultSet.getString("firstname"),
                    resultSet.getString("lastname"),
                    resultSet.getString("adresse")
            ));
            studenttable.setItems(studentList);
        }

    }
    public void AddStudent(){

    }
    public void DeleteStudent(){

    }
    public void Refresh() throws SQLException {
        refreshTable();
    }
}
