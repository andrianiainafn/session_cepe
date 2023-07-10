package com.example.session_cepe.Controller;

import com.example.session_cepe.DbConnection;
import com.example.session_cepe.Model.Result;
import com.example.session_cepe.Model.Result;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TriController  {


    @FXML
    private TableColumn<Result, String> dateNaissCol;

    @FXML
    private TableColumn<Result, String> firstnameCol;

    @FXML
    private TableColumn<Result, String> lastnameCol;
    @FXML
    private TableColumn<Result, String> meanCol;
    @FXML
    private TableColumn<Result, String> numberCol;

    @FXML
    private TableColumn<Result, String> schoolCol;

    @FXML
    private TableView<Result> studenttable;
    String meanQuery = null;
    String failedQuery = null;
    String sixGradeQuery = null;
    String deliberationQuery = null;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    ObservableList<Result> studentList = FXCollections.observableArrayList();

    @FXML
    void deliberation(ActionEvent event) throws SQLException, ClassNotFoundException {
        getdeliberationQuery();
        getList(deliberationQuery);
    }
    private void getdeliberationQuery(){
        deliberationQuery = "SELECT e.numeleve,nom,prenom,numEcole,dateNaiss,SUM(n.note*m.coef)/SUM(m.coef) as moyenne FROM eleve e JOIN note n ON e.numEleve = n.numEleve JOIN matiere m ON n.numMat = m.numMat GROUP BY e.numEleve HAVING  SUM(n.note*coef)/SUM(m.coef) > 9.74 AND SUM(n.note*coef)/SUM(m.coef) < 10 ";
    }

    @FXML
    void echec(ActionEvent event) throws SQLException, ClassNotFoundException {
        getFailedQuery();
        getList(failedQuery);
    }
    private void getFailedQuery(){
        failedQuery = "SELECT e.numeleve,nom,prenom,numEcole,dateNaiss,SUM(n.note*m.coef)/SUM(m.coef) as moyenne FROM eleve e JOIN note n ON e.numEleve = n.numEleve JOIN matiere m ON n.numMat = m.numMat GROUP BY e.numEleve HAVING SUM(n.note*coef)/SUM(m.coef) < 9.75 ";
    }

    @FXML
    void merite(ActionEvent event) throws SQLException, ClassNotFoundException {
        getMeanQuery();
        getList(meanQuery);
    }
    private void getMeanQuery(){
        meanQuery =" SELECT e.numeleve,nom,prenom,numEcole,dateNaiss,SUM(n.note*m.coef)/SUM(m.coef) as moyenne FROM eleve e JOIN note n ON e.numEleve = n.numEleve JOIN matiere m ON n.\n" +
                "numMat = m.numMat GROUP BY e.numEleve ORDER BY moyenne DESC";
    }

    @FXML
    void sixieme(ActionEvent event) throws SQLException, ClassNotFoundException {
        getSixGradeQuery();
        getList(sixGradeQuery);
    }
    private void getSixGradeQuery(){
        sixGradeQuery = "SELECT e.numeleve,nom,prenom,numEcole,dateNaiss,SUM(n.note*m.coef)/SUM(m.coef) as moyenne FROM eleve e JOIN note n ON e.numEleve = n.numEleve JOIN matiere m ON n.numMat = m.numMat GROUP BY e.numEleve HAVING SUM(n.note*coef)/SUM(m.coef) > 12 OR SUM(n.note*coef)/SUM(m.coef) = 12";
    }
    private void getList(String query) throws SQLException, ClassNotFoundException {
        studentList.clear();
        connection = DbConnection.getCon();
        preparedStatement = connection.prepareStatement(query);
        resultSet = preparedStatement.executeQuery();

        while(resultSet.next()){
            studentList.add(new com.example.session_cepe.Model.Result(
                    resultSet.getString("numEleve"),
                    resultSet.getString("prenom"),
                    resultSet.getString("nom"),
                    resultSet.getString("numEcole"),
                    resultSet.getDate("dateNaiss").toLocalDate(),
                    resultSet.getDouble("moyenne")
            ));
            studenttable.setItems(studentList);
        }
        numberCol.setCellValueFactory(new PropertyValueFactory<>("number"));
        schoolCol.setCellValueFactory(new PropertyValueFactory<>("school"));
        firstnameCol.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        lastnameCol.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        dateNaissCol.setCellValueFactory(new PropertyValueFactory<>("birth"));
        meanCol.setCellValueFactory(new PropertyValueFactory<>("mean"));
        studenttable.setItems(studentList);
        studenttable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                // Remplissez les champs avec les valeurs de la ligne sélectionnée

            } else {
                // Si aucune ligne n'est sélectionnée, effacez les champs

            }
        });
    }


    @FXML
    void relever(ActionEvent event) {

    }

}
