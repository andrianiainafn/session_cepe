package com.example.session_cepe.Controller;

import com.example.session_cepe.DbConnection;
import com.example.session_cepe.Model.Note;
import com.example.session_cepe.Model.Subject;
import com.example.session_cepe.NoteAdd;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NoteController implements Initializable {
    @FXML
    private TableColumn<Note, Integer> noteCol;

    @FXML
    private TextField noteFiled;

    @FXML
    private TableView<Note> notetable;

    @FXML
    private TableColumn<Note, String> schoolCol;

    @FXML
    private TextField schoolField;

    @FXML
    private TextField searchField;

    @FXML
    private TableColumn<Note, String> studentNumberCol;

    @FXML
    private TextField studentNumberField;

    @FXML
    private TableColumn<Note, String> subjectNumberCol;

    @FXML
    private TextField subjectnumberField;
    String query = null;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    com.example.session_cepe.Model.Note note = null;
    ObservableList<Note> noteList = FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            loadData();
            notetable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            notetable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    // Remplissez les champs avec les valeurs de la ligne sélectionnée
                    studentNumberField.setText(newSelection.getStudentNumber());
                    subjectnumberField.setText(newSelection.getSubjectNumber());
                    schoolField.setText(newSelection.getSchoolYear());
                    noteFiled.setText(Integer.toString(newSelection.getNote()));

                } else {
                    // Si aucune ligne n'est sélectionnée, effacez les champs
                    studentNumberField.clear();
                    subjectnumberField.clear();
                    noteFiled.clear();
                    schoolField.clear();


                }
            });
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void loadData() throws ClassNotFoundException, SQLException {
        connection = DbConnection.getCon();
        refreshTable();
        schoolCol.setCellValueFactory(new PropertyValueFactory<>("schoolYear"));
        studentNumberCol.setCellValueFactory(new PropertyValueFactory<>("studentNumber"));
        subjectNumberCol.setCellValueFactory(new PropertyValueFactory<>("subjectNumber"));
        noteCol.setCellValueFactory(new PropertyValueFactory<>("note"));
    }
    public void refreshTable() throws SQLException {
        noteList.clear();
        query = "SELECT * FROM note";
        preparedStatement = connection.prepareStatement(query);
        resultSet = preparedStatement.executeQuery();

        while(resultSet.next()){
            noteList.add(new com.example.session_cepe.Model.Note(
                    resultSet.getString("anneeScolaire"),
                    resultSet.getString("numEleve"),
                    resultSet.getString("numMat"),
                    resultSet.getInt("note")
            ));
            notetable.setItems(noteList);
        }
    }
    @FXML
    void addNote(ActionEvent event) throws IOException {
        NoteAdd home = new NoteAdd();
        home.affiche();
    }

    @FXML
    void cancel(ActionEvent event) {

    }

    @FXML
    void deleteNote(ActionEvent event) throws ClassNotFoundException, SQLException {
        notetable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        ObservableList<Note> selectedUsers = notetable.getSelectionModel().getSelectedItems();
        if (!selectedUsers.isEmpty()) {
            Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationDialog.setTitle("Confirmation");
            confirmationDialog.setHeaderText("Supprimer les ecoles sélectionnés ?");
            confirmationDialog.setContentText("Êtes-vous sûr de vouloir supprimer les ecoles sélectionnés ?");

            Optional<ButtonType> result = confirmationDialog.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                query = "DELETE FROM note WHERE numMat = ? AND numEleve = ?";
                connection = DbConnection.getCon();
                preparedStatement = connection.prepareStatement(query);
                for (Note note : selectedUsers) {
                    preparedStatement.setString(1, note.getSubjectNumber());
                    preparedStatement.setString(2, note.getStudentNumber());
                    preparedStatement.execute();
                }
                refreshTable();
            }
        }
    }

    @FXML
    void refresh(ActionEvent event) throws SQLException {
        refreshTable();
    }

    @FXML
    void save(ActionEvent event) throws ClassNotFoundException, SQLException {
        connection = DbConnection.getCon();
        String student = studentNumberField.getText();
        String subject = subjectnumberField.getText();
        String school = schoolField.getText();
        String note =  noteFiled.getText();

        if(student.isEmpty() || subject.isEmpty()|| school.isEmpty() || note.isEmpty() ){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please fill the required input !!!");
            alert.showAndWait();
        }else{
            getQuery();
            update();
            studentNumberField.setText("");
            subjectnumberField.setText("");
            noteFiled.setText("");
            schoolField.setText("");
            refreshTable();
        }
    }
    private  void getQuery(){
        query = "UPDATE note SET anneeScolaire = ? , note= ? WHERE numMat = ? AND numEleve = ?";
    }
    private void update(){
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,schoolField.getText());
            preparedStatement.setString(2, noteFiled.getText());
            preparedStatement.setString(3,subjectnumberField.getText());
            preparedStatement.setString(4,studentNumberField.getText());
            preparedStatement.execute();

        }catch (SQLException ex){
            Logger.getLogger(AddStudentController.class.getName()).log(Level.SEVERE,null,ex);
        }
    }

}
