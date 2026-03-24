module main {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires com.fasterxml.jackson.databind;

    opens main to javafx.fxml;
    exports main;
    exports main.ui;
    opens main.ui to javafx.fxml;
    exports main.ui.user;
    exports main.ui.admin;
    exports main.ui.director;
    exports main.ui.сharts;

    opens main.ui.user to javafx.fxml, javafx.controls;
    opens main.ui.admin to javafx.fxml;
    opens main.ui.director to javafx.fxml;

    exports main.enums;
    opens main.enums;
    opens main.entities to com.google.gson;
    exports main.entities;
    opens main.connection to com.google.gson;
    exports main.connection;
    opens main.connection.TCP to com.google.gson;
    exports main.connection.TCP;
    exports main.ui.operations.add;
    opens main.ui.operations.add to javafx.fxml;
    exports main.ui.operations.delete;
    opens main.ui.operations.delete to javafx.fxml;
    exports main.ui.operations.update;
    opens main.ui.operations.update to javafx.fxml;
    opens main.ui.сharts to javafx.controls, javafx.fxml;

}