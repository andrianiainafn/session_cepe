module com.example.session_cepe {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.example.session_cepe to javafx.fxml;
    exports com.example.session_cepe;
    exports com.example.session_cepe.Controller;
    opens com.example.session_cepe.Controller to javafx.fxml;
}