package com.example.session_cepe.Controller;

import com.example.session_cepe.*;

import com.example.session_cepe.Model.Student;
import com.example.session_cepe.Model.Subject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StudentController implements Initializable {
    @FXML
    private DatePicker birthField;
    @FXML
    private TextField fnameField;

    @FXML
    private TextField lnameFiled;
    @FXML
    private TextField schoolField;
    @FXML
    private Button addStudent;

    @FXML
    private TextField numberField;
    @FXML
    private Button deleteStudent;
    @FXML
    private TextField searchField;
    @FXML
    private Button deleteStudent1;
    @FXML
    private TableView<com.example.session_cepe.Model.Student> studenttable;
    @FXML
    private TableColumn<com.example.session_cepe.Model.Student, String> dateNaissCol ;
    @FXML
    private TableColumn<com.example.session_cepe.Model.Student, String> firstnameCol;

    @FXML
    private TableColumn<com.example.session_cepe.Model.Student, String> lastnameCol;

    @FXML
    private TableColumn<com.example.session_cepe.Model.Student, String> numberCol;

    @FXML
    private TableColumn<com.example.session_cepe.Model.Student, String> schoolCol;

    String query = null;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    com.example.session_cepe.Model.Student student = null;
    ObservableList<com.example.session_cepe.Model.Student> studentList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            loadData();
            studenttable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            studenttable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    // Remplissez les champs avec les valeurs de la ligne sélectionnée
                    fnameField.setText(newSelection.getFirstname());
                    numberField.setText(newSelection.getNumber());
                    lnameFiled.setText(newSelection.getLastname());
                    schoolField.setText(newSelection.getSchool());
                    birthField.setValue(newSelection.getBirth());
                } else {
                    // Si aucune ligne n'est sélectionnée, effacez les champs
                    fnameField.clear();
                    lnameFiled.clear();
                    schoolField.clear();
                    numberField.clear();
                }
            });
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void AddStudent(ActionEvent event) throws IOException {
        StudentAdd home = new StudentAdd();
        home.affiche();
    }

    @FXML
    void DeleteStudent(ActionEvent event) throws ClassNotFoundException, SQLException {
        studenttable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        ObservableList<Student> selectedUsers = studenttable.getSelectionModel().getSelectedItems();
        if (!selectedUsers.isEmpty()) {
            Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationDialog.setTitle("Confirmation");
            confirmationDialog.setHeaderText("Supprimer les ecoles sélectionnés ?");
            confirmationDialog.setContentText("Êtes-vous sûr de vouloir supprimer les ecoles sélectionnés ?");

            Optional<ButtonType> result = confirmationDialog.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                query = "DELETE FROM eleve WHERE numEleve = ?";
                connection = DbConnection.getCon();
                preparedStatement = connection.prepareStatement(query);
                for (Student std : selectedUsers) {
                    preparedStatement.setString(1, std.getNumber());
                    preparedStatement.execute();
                }
                refreshTable();
            }
        }
    }



    private void loadData() throws ClassNotFoundException, SQLException {

        connection = DbConnection.getCon();
        refreshTable();
        numberCol.setCellValueFactory(new PropertyValueFactory<>("number"));
        schoolCol.setCellValueFactory(new PropertyValueFactory<>("school"));
        firstnameCol.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        lastnameCol.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        dateNaissCol.setCellValueFactory(new PropertyValueFactory<>("birth"));
        studenttable.setItems(studentList);

    }
    private void refreshTable() throws SQLException {
        studentList.clear();
        query = "SELECT * FROM eleve";
        preparedStatement = connection.prepareStatement(query);
        resultSet = preparedStatement.executeQuery();

        while(resultSet.next()){
            studentList.add(new com.example.session_cepe.Model.Student(
                    resultSet.getString("numEleve"),
                    resultSet.getString("prenom"),
                    resultSet.getString("nom"),
                    resultSet.getString("numEcole"),
                    resultSet.getDate("dateNaiss").toLocalDate()
            ));
            studenttable.setItems(studentList);
        }

    }

    @FXML
    void cancel(ActionEvent event) {

    }

    @FXML
    void save(ActionEvent event) throws ClassNotFoundException, SQLException {
        connection = DbConnection.getCon();
        String number = numberField.getText();
        String firstname = fnameField.getText();
        String school = schoolField.getText();
        String lastname =  lnameFiled.getText();
        String birth = String.valueOf(birthField.getValue());

        if(number.isEmpty() || firstname.isEmpty()|| school.isEmpty() || lastname.isEmpty() || birth.isEmpty() ){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please fill the required input !!!");
            alert.showAndWait();
        }else{
            getQuery();
            update();
            numberField.setText("");
            fnameField.setText("");
            lnameFiled.setText("");
            schoolField.setText("");
            birthField.setValue(null);
            refreshTable();
        }
    }
    private  void getQuery(){
        query = "UPDATE eleve SET nom = ? , prenom= ?, numEcole = ?, dateNaiss = ? WHERE numEleve = ?";
    }
    private void update(){
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,fnameField.getText());
            preparedStatement.setString(2, lnameFiled.getText());
            preparedStatement.setString(3,schoolField.getText());
            preparedStatement.setString(4,String.valueOf(birthField.getValue()));
            preparedStatement.setString(5,numberField.getText());
            preparedStatement.execute();

        }catch (SQLException ex){
            Logger.getLogger(AddStudentController.class.getName()).log(Level.SEVERE,null,ex);
        }
    }

    public void refresh() throws SQLException {
        refreshTable();
    }

}
