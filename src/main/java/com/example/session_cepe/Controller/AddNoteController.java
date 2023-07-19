package com.example.session_cepe.Controller;

import com.example.session_cepe.DbConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddNoteController {
    @FXML
    private Button btnCancel;
    @FXML
    private TextField noteField;

    @FXML
    private TextField schoolyearField;

    @FXML
    private TextField studentField;

    @FXML
    private TextField subjectField;
    String query = null;
    String checkquery = null;
    //String moyQuery = null;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    PreparedStatement  checkStatement = null;
    ResultSet newresultSet = null;


    @FXML
    void cancel(ActionEvent event) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    void save(ActionEvent event) throws ClassNotFoundException, SQLException {
        connection = DbConnection.getCon();
        String studentNumber = studentField.getText();
        String subjectNumber = subjectField.getText();
        String note = noteField.getText();
        String schoolYear = schoolyearField.getText();
        if(subjectNumber.isEmpty() || studentNumber.isEmpty() || note.isEmpty() || schoolYear.isEmpty() ){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please fill the required input !!!");
            alert.showAndWait();
        }else{
            getVerificationQuery();
            checkValidSchool();
            while (newresultSet.next()){
                if(newresultSet.getInt("COUNT(*)") == 0){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("Please Enter  valid Student !!!");
                    alert.showAndWait();
                }else{
                    getQuery();
                    insert();
                    studentField.setText("");
                    subjectField.setText("");
                    noteField.setText("");
                    schoolyearField.setText("");}
            }
        }
    }
    private  void getQuery(){
        query = "INSERT INTO note (anneeScolaire,numEleve,numMat,note) VALUES(?,?,?,?)";
    }
    private void insert(){
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,schoolyearField.getText());
            preparedStatement.setString(2,studentField.getText());
            preparedStatement.setString(3,subjectField.getText());
            preparedStatement.setDouble(4, Double.parseDouble(noteField.getText()));
            preparedStatement.execute();


        }catch (SQLException ex){
            Logger.getLogger(AddStudentController.class.getName()).log(Level.SEVERE,null,ex);
        }
    }
    void getVerificationQuery(){
        checkquery = "SELECT COUNT(*) FROM eleve WHERE numEleve = ?";
    }
    void checkValidSchool() throws ClassNotFoundException, SQLException {
        connection = DbConnection.getCon();
        checkStatement = connection.prepareStatement(checkquery);
        checkStatement.setString(1,studentField.getText());
        newresultSet = checkStatement.executeQuery();
    }

    /*private  void getMoyQuery(){
        moyQuery = "UPDATE moyenne SET anneeScolaire = ? ,  = ?, dateNaiss = ? WHERE numEleve = ?";
    }
    private void update(){
        try {
            preparedStatement = connection.prepareStatement(moyQuery);
            preparedStatement.setString(1,fnameField.getText());
            preparedStatement.setString(2, lnameFiled.getText());
            preparedStatement.setString(3,schoolField.getText());
            preparedStatement.setString(4,String.valueOf(birthField.getValue()));
            preparedStatement.setString(5,numberField.getText());
            preparedStatement.execute();

        }catch (SQLException ex){
            Logger.getLogger(AddStudentController.class.getName()).log(Level.SEVERE,null,ex);
        }
    }*/
}
