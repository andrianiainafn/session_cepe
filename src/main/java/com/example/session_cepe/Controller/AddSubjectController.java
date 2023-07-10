package com.example.session_cepe.Controller;

import com.example.session_cepe.DbConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddSubjectController {
    @FXML
    private TextField coefField;

    @FXML
    private TextField designField;
    @FXML
    private Button btnSave;
    String query = null;
    Connection connection = null;
    PreparedStatement preparedStatement = null;

    @FXML
    void cancel(ActionEvent event) {
        Stage stage = (Stage) btnSave.getScene().getWindow();
        stage.close();
    }

    @FXML
    void save(ActionEvent event) throws ClassNotFoundException {
        connection = DbConnection.getCon();
        String firstname = designField.getText();
        String lastname = coefField.getText();

        if(firstname.isEmpty() || lastname.isEmpty() ){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please fill the required input !!!");
            alert.showAndWait();
        }else{
            getQuery();
            insert();
            designField.setText("");
            coefField.setText("");
        }
    }
    private  void getQuery(){
        query = "INSERT INTO matiere (numMat,designMat,coef) VALUES(?,?,?)";
    }
    private void insert(){
        try {
            String maxIdQuery = "SELECT MAX(numMat) FROM matiere";
            PreparedStatement maxIdStatement = connection.prepareStatement(maxIdQuery);
            ResultSet maxIdResult = maxIdStatement.executeQuery();
            maxIdResult.next();
            String maxId = maxIdResult.getString(1);
            maxIdStatement.close();

            int currentNumber = 0;
            if (maxId != null && maxId.startsWith("M-")) {
                String numberPart = maxId.substring(2);
                currentNumber = Integer.parseInt(numberPart);
            }

            String idMat = "M-" + String.format("%04d", currentNumber + 1);
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,idMat);
            preparedStatement.setString(2,designField.getText());
            preparedStatement.setString(3,coefField.getText());

            preparedStatement.execute();


        }catch (SQLException ex){
            Logger.getLogger(AddStudentController.class.getName()).log(Level.SEVERE,null,ex);
        }
    }
}
