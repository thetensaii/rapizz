module com.tdt {
    requires mysql.connector.java;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.tdt.controllers to javafx.fxml;
    opens com.tdt.entity to javafx.base;
    opens com.tdt.core to javafx.base;
    exports com.tdt;
    exports com.tdt.controllers;
}