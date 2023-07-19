package com.example.session_cepe.Controller;

import com.example.session_cepe.DbConnection;
import com.example.session_cepe.Model.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddStudentController {

    String query = null;
    String moyQuery = null;
    String checkquery = null;

    Connection connection = null;
    PreparedStatement preparedStatement = null;
    PreparedStatement checkStatement = null;
    Student student = null;
    ResultSet resultSet = null;
    ResultSet newresultSet = null;

    private boolean update;
    @FXML
    private DatePicker birthField;

    @FXML
    private TextField fnameField;

    @FXML
    private TextField lnameFiled;

    @FXML
    private TextField schoolField;
    @FXML
    private Button btnCancel;
    @FXML
    void Cancel(ActionEvent event) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    void Save(ActionEvent event) throws ClassNotFoundException, SQLException {
        connection = DbConnection.getCon();
        String firstname = fnameField.getText();
        String lastname = lnameFiled.getText();
        String birth = String.valueOf(birthField.getValue());
        String school = schoolField.getText();

        if(firstname.isEmpty() || lastname.isEmpty() || birth.isEmpty() || school.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please fill the required input !!!");
            alert.showAndWait();
        }else{
            /*getMoyQuery();
            insertMoy();*/
            getVerificationQuery();
            checkValidSchool();
            while (newresultSet.next()){
                if(newresultSet.getInt("COUNT(*)") == 0){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("Please Enter  valid school !!!");
                    alert.showAndWait();
                }else{
                    getQuery();
                    insert();
                    fnameField.setText("");
                    lnameFiled.setText("");
                    schoolField.setText("");
                    birthField.setValue(null);
                }
            }

        }
    }
    private  void getQuery(){
        query = "INSERT INTO eleve (numEleve,numEcole,nom,prenom,dateNaiss) VALUES(?,?,?,?,?)";
    }
    private void insert(){
        try {
            String maxIdQuery = "SELECT MAX(numEleve) FROM eleve";
            PreparedStatement maxIdStatement = connection.prepareStatement(maxIdQuery);
            ResultSet maxIdResult = maxIdStatement.executeQuery();
            maxIdResult.next();
            String maxId = maxIdResult.getString(1);
            maxIdStatement.close();

            int currentNumber = 0;
            if (maxId != null && maxId.startsWith("EL-")) {
                String numberPart = maxId.substring(3);
                currentNumber = Integer.parseInt(numberPart);
            }

            String idStudent = "EL-" + String.format("%04d", currentNumber + 1);
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,idStudent);
            preparedStatement.setString(2,schoolField.getText());
            preparedStatement.setString(3,fnameField.getText());
            preparedStatement.setString(4,lnameFiled.getText());
            preparedStatement.setString(5,String.valueOf(birthField.getValue()));
            preparedStatement.execute();


        }catch (SQLException ex){
            Logger.getLogger(AddStudentController.class.getName()).log(Level.SEVERE,null,ex);
        }
    }
    /*private void getMoyQuery(){moyQuery = "INSERT INTO moyenne (anneeScolaire,numEleve,moyenne,totale,coefTot) VALUES(?,?,?,?,?) ";}
    private void insertMoy() throws SQLException {
        try {
        String maxIdQuery = "SELECT MAX(numEleve) FROM eleve";
        PreparedStatement maxIdStatement = connection.prepareStatement(maxIdQuery);
        ResultSet maxIdResult = maxIdStatement.executeQuery();
        maxIdResult.next();
        String maxId = maxIdResult.getString(1);
        maxIdStatement.close();

        int currentNumber = 0;
        if (maxId != null && maxId.startsWith("EL-")) {
            String numberPart = maxId.substring(3);
            currentNumber = Integer.parseInt(numberPart);
        }

        String idStudent = "EL-" + String.format("%04d", currentNumber + 1);
        preparedStatement = connection.prepareStatement(moyQuery);
        preparedStatement.setString(1,"2022-2023");
        preparedStatement.setString(2,idStudent);
        preparedStatement.setDouble(3,0);
        preparedStatement.setDouble(4,0);
        preparedStatement.setInt(5,0);
        preparedStatement.execute();


    }catch (SQLException ex){
        Logger.getLogger(AddStudentController.class.getName()).log(Level.SEVERE,null,ex);
    }
    }*/
    void getVerificationQuery(){
        checkquery = "SELECT COUNT(*) FROM ecole WHERE numEcole = ?";
    }
    void checkValidSchool() throws ClassNotFoundException, SQLException {
        connection = DbConnection.getCon();
        checkStatement = connection.prepareStatement(checkquery);
        checkStatement.setString(1,schoolField.getText());
        newresultSet = checkStatement.executeQuery();
    }
    public void setUpdate(boolean b) {
        this.update = b;
    }
}
