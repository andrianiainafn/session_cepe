package com.example.session_cepe.Controller;

import com.example.session_cepe.DbConnection;
import com.example.session_cepe.Model.Result;
import com.example.session_cepe.Model.Result;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import com.itextpdf.text.Document;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.Phaser;

public class TriController  implements Initializable {


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
    PreparedStatement newpreparedStatement = null;
    ResultSet newresultSet = null;
    ResultSet resultSet = null;
    @FXML
    private TextField idField;

    ObservableList<Result> studentList = FXCollections.observableArrayList();

    @FXML
    void deliberation(ActionEvent event) throws SQLException, ClassNotFoundException {
        getdeliberationQuery();
        getList(deliberationQuery);
        studenttable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                // Remplissez les champs avec les valeurs de la ligne sélectionnée
                idField.setText(newSelection.getNumber());
            } else {
                // Si aucune ligne n'est sélectionnée, effacez les champs
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText(newSelection.getNumber());
                alert.showAndWait();
            }
        });
    }
    private void getdeliberationQuery(){
        deliberationQuery = "SELECT e.numeleve,nom,prenom,numEcole,dateNaiss,SUM(n.note*m.coef)/SUM(m.coef) as moyenne FROM eleve e JOIN note n ON e.numEleve = n.numEleve JOIN matiere m ON n.numMat = m.numMat GROUP BY e.numEleve HAVING  SUM(n.note*coef)/SUM(m.coef) > 9.74 AND SUM(n.note*coef)/SUM(m.coef) < 10 ";
    }

    @FXML
    void echec(ActionEvent event) throws SQLException, ClassNotFoundException {
        getFailedQuery();
        getList(failedQuery);
        studenttable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                // Remplissez les champs avec les valeurs de la ligne sélectionnée
                idField.setText(newSelection.getNumber());
            } else {
                // Si aucune ligne n'est sélectionnée, effacez les champs
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText(newSelection.getNumber());
                alert.showAndWait();
            }
        });
    }
    private void getFailedQuery(){
        failedQuery = "SELECT e.numeleve,nom,prenom,numEcole,dateNaiss,SUM(n.note*m.coef)/SUM(m.coef) as moyenne FROM eleve e JOIN note n ON e.numEleve = n.numEleve JOIN matiere m ON n.numMat = m.numMat GROUP BY e.numEleve HAVING SUM(n.note*coef)/SUM(m.coef) < 9.75 ";
    }

    @FXML
    void merite(ActionEvent event) throws SQLException, ClassNotFoundException {
        getMeanQuery();
        getList(meanQuery);
        studenttable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                // Remplissez les champs avec les valeurs de la ligne sélectionnée
                idField.setText(newSelection.getNumber());
            } else {
                // Si aucune ligne n'est sélectionnée, effacez les champs
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText(newSelection.getNumber());
                alert.showAndWait();
            }
        });
    }
    private void getMeanQuery(){
        meanQuery =" SELECT e.numeleve,nom,prenom,numEcole,dateNaiss,SUM(n.note*m.coef)/SUM(m.coef) as moyenne FROM eleve e JOIN note n ON e.numEleve = n.numEleve JOIN matiere m ON n.\n" +
                "numMat = m.numMat GROUP BY e.numEleve ORDER BY moyenne DESC";
    }

    @FXML
    void sixieme(ActionEvent event) throws SQLException, ClassNotFoundException {
        getSixGradeQuery();
        getList(sixGradeQuery);
        studenttable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                // Remplissez les champs avec les valeurs de la ligne sélectionnée
                idField.setText(newSelection.getNumber());
            } else {
                // Si aucune ligne n'est sélectionnée, effacez les champs
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText(newSelection.getNumber());
                alert.showAndWait();
            }
        });
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
                    Math.round(resultSet.getDouble("moyenne") * 100.0) / 100.0
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
    }


    @FXML
    void relever(ActionEvent event) throws FileNotFoundException, DocumentException, ClassNotFoundException, SQLException {
        String queryPdf = "SELECT * FROM eleve e JOIN note n ON e.numEleve = n.numEleve JOIN matiere m ON n.numMat = m.numMat JOIN ecole ec ON ec.numEcole = e.numEcole WHERE e.numEleve = ? ";
        String querySuitePdf = "SELECT e.numeleve,nom,prenom,ec.design,dateNaiss,SUM(n.note*m.coef)/SUM(m.coef) as moyenne,SUM(n.note*m.coef) as somepond FROM eleve e JOIN ecole ec ON e.numEcole = ec.numEcole  JOIN note n ON e.numEleve = n.numEleve JOIN matiere m ON n.numMat = m.numMat WHERE  e.numEleve = ?  GROUP BY e.numEleve;";
        connection = DbConnection.getCon();
        preparedStatement = connection.prepareStatement(queryPdf);
        preparedStatement.setString(1,idField.getText());
        newpreparedStatement = connection.prepareStatement(querySuitePdf);
        newpreparedStatement.setString(1,idField.getText());
        resultSet = preparedStatement.executeQuery();
        newresultSet = newpreparedStatement.executeQuery();

        String file_name = "/home/andrianiainafn/Bureau/RL"+ idField.getText() + ".pdf";
        Document document = new Document();
        PdfWriter.getInstance(document,new FileOutputStream(file_name));
        document.open();
        Paragraph para = new Paragraph("RELEVE DE NOTE");
        document.add(para);
        while (newresultSet.next()){
            para = new Paragraph("Annee Scolaire : 2022-2023" );
            document.add(para);
            para = new Paragraph("Nom : " + newresultSet.getString("nom"));
            document.add(para);
            para = new Paragraph("Prenom : " + newresultSet.getString("prenom"));
            document.add(para);
            para = new Paragraph("Date de Naissance : " + newresultSet.getString("dateNaiss"));
            document.add(para);
            para = new Paragraph("Ecole : " + newresultSet.getString("design"));
            document.add(para);
        }


        PdfPTable table = new PdfPTable(4);
        PdfPCell c1 = new PdfPCell();
        c1.addElement(new Paragraph("Matieres"));
        table.addCell(c1);

        c1 = new PdfPCell();
        c1.addElement(new Paragraph("Coefficient"));
        table.addCell(c1);

        c1 = new PdfPCell();
        c1.addElement(new Paragraph("Note"));
        table.addCell(c1);

        c1 = new PdfPCell();
        c1.addElement(new Paragraph("Note pondere"));
        table.addCell(c1);
        while(resultSet.next()){
            table.addCell(resultSet.getString("designMat"));
            table.addCell(String.valueOf(resultSet.getInt("coef")));
            table.addCell(String.valueOf(resultSet.getInt("note")));
            table.addCell(String.valueOf(resultSet.getInt("note")*resultSet.getInt("coef")));
        }
        PdfPCell emptyCell = new PdfPCell();
        emptyCell.setBorder(PdfPCell.NO_BORDER);
        table.addCell(emptyCell);
        table.addCell(emptyCell);
        table.addCell(emptyCell);
        newresultSet = newpreparedStatement.executeQuery();
        while (newresultSet.next()){
            table.addCell(String.valueOf(newresultSet.getInt("somepond")));
            PdfPCell mergedCell = new PdfPCell(new Phrase("moyenne"));
            mergedCell.setColspan(3);
            table.addCell(mergedCell);
            table.addCell(String.valueOf(Math.round(newresultSet.getDouble("moyenne") * 100.0) / 100.0));
        }
        table.completeRow();
        document.add(table);
        document.close();
        System.out.println("tapitra");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            getMeanQuery();
            getList(meanQuery);
            studenttable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    // Remplissez les champs avec les valeurs de la ligne sélectionnée
                    idField.setText(newSelection.getNumber());
                } else {
                    // Si aucune ligne n'est sélectionnée, effacez les champs
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText(newSelection.getNumber());
                    alert.showAndWait();
                }
            });
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
