package com.example.session_cepe;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Home {
    public  void affiche() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Home.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 910, 510);
        Stage newStage = new Stage();
        newStage.setTitle("Home");
        newStage.setScene(scene);
        newStage.show();
    }

}
