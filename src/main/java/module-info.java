module com.project.seabattle {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.project.seabattle to javafx.fxml;
    exports com.project.seabattle;
}