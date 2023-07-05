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

public class AddSchoolController {
    @FXML
    private TextField adressField;

    @FXML
    private TextField designField;

    String query = null;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    @FXML
    private Button btnCancel;
    @FXML
    public void cancel(ActionEvent event) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void saveSchool(ActionEvent event) throws ClassNotFoundException {
        connection = DbConnection.getCon();
        String firstname = designField.getText();
        String lastname = adressField.getText();

        if(firstname.isEmpty() || lastname.isEmpty() ){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please fill the required input !!!");
            alert.showAndWait();
        }else{
            getQuery();
            insert();
            designField.setText("");
            adressField.setText("");
        }

    }
    private  void getQuery(){
        query = "INSERT INTO ecole (numEcole,design,adrese) VALUES(?,?,?)";
    }
    private void insert(){
        try {

            String maxIdQuery = "SELECT MAX(numEcole) FROM ecole";
            PreparedStatement maxIdStatement = connection.prepareStatement(maxIdQuery);
            ResultSet maxIdResult = maxIdStatement.executeQuery();
            maxIdResult.next();
            String maxId = maxIdResult.getString(1);
            maxIdStatement.close();

            int currentNumber = 0;
            if (maxId != null && maxId.startsWith("E-")) {
                String numberPart = maxId.substring(2);
                currentNumber = Integer.parseInt(numberPart);
            }

            String idSchool = "E-" + String.format("%04d", currentNumber + 1);
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,idSchool);
            preparedStatement.setString(2,designField.getText());
            preparedStatement.setString(3, adressField.getText());

            preparedStatement.execute();


        }catch (SQLException ex){
            Logger.getLogger(AddStudentController.class.getName()).log(Level.SEVERE,null,ex);
        }
    }
}
