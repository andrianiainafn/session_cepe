package com.example.session_cepe.Controller;

import com.example.session_cepe.HelloApplication;
import com.example.session_cepe.Home;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
    @FXML
    private void connectTODashboard() throws IOException {
        Home home = new Home();
        home.affiche();
    }

}