package com.example.session_cepe.Controller;

import com.example.session_cepe.DbConnection;
import com.example.session_cepe.Model.School;
import com.example.session_cepe.Model.Subject;
import com.example.session_cepe.SubjectAdd;
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

public class SubjectController implements Initializable {
    @FXML
    private TableColumn<?, ?> CoefCol;

    @FXML
    private TextField DesignField;

    @FXML
    private TextField coefField;

    @FXML
    private TableColumn<?, ?> designCol;

    @FXML
    private TableColumn<?, ?> numberCol;

    @FXML
    private TextField numberField;

    @FXML
    private TableView<Subject> subjecttable;

    String query = null;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    com.example.session_cepe.Model.Subject subject = null;
    ObservableList<Subject> subjectlist = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            loadData();
            subjecttable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            subjecttable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    // Remplissez les champs avec les valeurs de la ligne sélectionnée
                    DesignField.setText(newSelection.getDesign());
                    coefField.setText(Integer.toString(newSelection.getCoef()));
                    numberField.setText(newSelection.getNumber());
                } else {
                    // Si aucune ligne n'est sélectionnée, effacez les champs
                    DesignField.clear();
                    coefField.clear();
                    numberField.clear();
                }
            });
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void loadData() throws ClassNotFoundException, SQLException {
        connection = DbConnection.getCon();
        refreshTable();
        numberCol.setCellValueFactory(new PropertyValueFactory<>("number"));
        designCol.setCellValueFactory(new PropertyValueFactory<>("design"));
        CoefCol.setCellValueFactory(new PropertyValueFactory<>("coef"));
    }
    public void refreshTable() throws SQLException {
        subjectlist.clear();
        query = "SELECT * FROM matiere";
        preparedStatement = connection.prepareStatement(query);
        resultSet = preparedStatement.executeQuery();

        while(resultSet.next()){
            subjectlist.add(new com.example.session_cepe.Model.Subject(
                    resultSet.getString("numMat"),
                    resultSet.getString("designMat"),
                    resultSet.getInt("coef")
            ));
            subjecttable.setItems(subjectlist);
        }
    }
    @FXML
    public void save() throws SQLException, ClassNotFoundException {
        connection = DbConnection.getCon();
        String firstname = DesignField.getText();
        String lastname = coefField.getText();

        if(firstname.isEmpty() || lastname.isEmpty() ){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please fill the required input !!!");
            alert.showAndWait();
        }else{
            getQuery();
            update();
            DesignField.setText("");
            coefField.setText("");
            refreshTable();
        }

    }
    private  void getQuery(){
        query = "UPDATE matiere SET designMat = ? , coef= ? WHERE numMat = ?";
    }
    private void update(){
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,DesignField.getText());
            preparedStatement.setString(2, coefField.getText());
            preparedStatement.setString(3,numberField.getText());
            preparedStatement.execute();

        }catch (SQLException ex){
            Logger.getLogger(AddStudentController.class.getName()).log(Level.SEVERE,null,ex);
        }
    }
    @FXML
    public void cancel(){

    }
    @FXML
    public void deleteSubject() throws ClassNotFoundException, SQLException {
        subjecttable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        ObservableList<Subject> selectedUsers = subjecttable.getSelectionModel().getSelectedItems();
        if (!selectedUsers.isEmpty()) {
            Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationDialog.setTitle("Confirmation");
            confirmationDialog.setHeaderText("Supprimer les ecoles sélectionnés ?");
            confirmationDialog.setContentText("Êtes-vous sûr de vouloir supprimer les ecoles sélectionnés ?");

            Optional<ButtonType> result = confirmationDialog.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                query = "DELETE FROM matiere WHERE numMat = ?";
                connection = DbConnection.getCon();
                preparedStatement = connection.prepareStatement(query);
                for (Subject subject : selectedUsers) {
                    preparedStatement.setString(1, subject.getNumber());
                    preparedStatement.execute();
                }
                refreshTable();
            }
        }

    }
    public void addSubject() throws IOException {
        SubjectAdd home = new SubjectAdd();
        home.affiche();
    }

    @FXML
    void refresh(ActionEvent event) throws SQLException {
        refreshTable();
    }
}
