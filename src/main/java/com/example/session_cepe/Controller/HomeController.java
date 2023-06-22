package com.example.session_cepe.Controller;

import com.example.session_cepe.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import org.controlsfx.control.action.Action;

import javax.xml.namespace.QName;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HomeController implements Initializable {
    @FXML
    private StackPane contentArea ;
    
    @FXML
    void HandleHome(ActionEvent event) {

    }

    @FXML
    void HandleNote(ActionEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(HelloApplication.class.getResource("Note.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
    }

    @FXML
    void HandleSchool(ActionEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(HelloApplication.class.getResource("School.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
    }

    @FXML
    void HandleStudent(ActionEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(HelloApplication.class.getResource("Student.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
    }
    @FXML
    void HandleSubject(ActionEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(HelloApplication.class.getResource("Subject.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            Parent fxml = FXMLLoader.load(HelloApplication.class.getResource("Dashboard.fxml"));
            contentArea.getChildren().removeAll();
            contentArea.getChildren().setAll(fxml);
        }catch (IOException ex){
            Logger.getLogger(ModuleLayer.Controller.class.getName()).log(Level.SEVERE,null,ex);
        }
    }
}
