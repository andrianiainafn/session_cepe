module com.example.session_cepe {
    requires javafx.controls;
    requires javafx.fxml;
    requires mysql.connector.j;
    requires java.sql;

    requires org.controlsfx.controls;
    requires fontawesomefx;

    opens com.example.session_cepe to javafx.fxml;
    exports com.example.session_cepe;
    exports com.example.session_cepe.Controller;
    opens com.example.session_cepe.Controller to javafx.fxml;
    exports com.example.session_cepe.Model;
    opens com.example.session_cepe.Model to javafx.fxml;
}