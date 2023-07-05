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
    Connection connection = null;
    PreparedStatement preparedStatement = null;


    @FXML
    void cancel(ActionEvent event) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    void save(ActionEvent event) throws ClassNotFoundException {
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
            getQuery();
            insert();
            studentField.setText("");
            subjectField.setText("");
            noteField.setText("");
            schoolyearField.setText("");
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
            preparedStatement.setString(4,noteField.getText());
            preparedStatement.execute();


        }catch (SQLException ex){
            Logger.getLogger(AddStudentController.class.getName()).log(Level.SEVERE,null,ex);
        }
    }
}
