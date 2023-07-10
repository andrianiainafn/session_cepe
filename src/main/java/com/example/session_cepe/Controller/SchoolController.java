package com.example.session_cepe.Controller;

import com.example.session_cepe.DbConnection;
import com.example.session_cepe.HelloApplication;
import com.example.session_cepe.Model.School;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

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

public class SchoolController implements Initializable {


    @FXML
    private TextField DesignField;


    @FXML
    private TableColumn<School, String> adressCol;

    @FXML
    private TextField adressField;

    @FXML
    private TableColumn<School, String> designCol;

    @FXML
    private TableColumn<School, String> numberCol;
    @FXML
    private TextField numberField;
    @FXML
    private TableView<com.example.session_cepe.Model.School> schooltable;

    String query = null;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    com.example.session_cepe.Model.School school = null;
    ObservableList<School> schoolList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            loadData();
            schooltable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            schooltable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    // Remplissez les champs avec les valeurs de la ligne sélectionnée
                    DesignField.setText(newSelection.getDesign());
                    adressField.setText(newSelection.getAdresse());
                    numberField.setText(newSelection.getNumber());
                } else {
                    // Si aucune ligne n'est sélectionnée, effacez les champs
                    DesignField.clear();
                    adressField.clear();
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
        adressCol.setCellValueFactory(new PropertyValueFactory<>("adresse"));

    }
    public void refreshTable() throws SQLException {
        schoolList.clear();
        query = "SELECT * FROM ecole";
        preparedStatement = connection.prepareStatement(query);
        resultSet = preparedStatement.executeQuery();

        while(resultSet.next()){
            schoolList.add(new com.example.session_cepe.Model.School(
                    resultSet.getString("numEcole"),
                    resultSet.getString("design"),
                    resultSet.getString("adrese")
            ));
            schooltable.setItems(schoolList);
        }
    }
    @FXML
    public void addShcool(ActionEvent event) throws IOException {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("AddSchool.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage newStage = new Stage();
            newStage.setTitle("Add School");
            newStage.setScene(scene);
            newStage.show();

    }

    @FXML
    public void deleteSchool(ActionEvent event) throws ClassNotFoundException, SQLException {
        ObservableList<School> selectedUsers = schooltable.getSelectionModel().getSelectedItems();
        if (!selectedUsers.isEmpty()) {
            Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationDialog.setTitle("Confirmation");
            confirmationDialog.setHeaderText("Supprimer les ecoles sélectionnés ?");
            confirmationDialog.setContentText("Êtes-vous sûr de vouloir supprimer les ecoles sélectionnés ?");

            Optional<ButtonType> result = confirmationDialog.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                query = "DELETE FROM ecole WHERE numEcole = ?";
                connection = DbConnection.getCon();
                preparedStatement = connection.prepareStatement(query);
                for (School school : selectedUsers) {
                    preparedStatement.setString(1, school.getNumber());
                    preparedStatement.execute();
                }
                refreshTable();
            }
        }
    }
    @FXML
    private void save() throws ClassNotFoundException, SQLException {
        connection = DbConnection.getCon();
        String firstname = DesignField.getText();
        String lastname = adressField.getText();

        if(firstname.isEmpty() || lastname.isEmpty() ){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please fill the required input !!!");
            alert.showAndWait();
        }else{
            getQuery();
            update();
            DesignField.setText("");
            adressField.setText("");
            refreshTable();
        }

    }
    private  void getQuery(){
        query = "UPDATE ecole SET design = ? , adrese= ? WHERE numEcole = ?";
    }
    private void update(){
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,DesignField.getText());
            preparedStatement.setString(2, adressField.getText());
            preparedStatement.setString(3,numberField.getText());
            preparedStatement.execute();


        }catch (SQLException ex){
            Logger.getLogger(AddStudentController.class.getName()).log(Level.SEVERE,null,ex);
        }
    }
    @FXML
    private void cancel()  {

    }
    @FXML
    void refresh(ActionEvent event) throws SQLException {
        refreshTable();
    }
}


