package com.tdt.core;

import javafx.scene.control.Alert;

public class Error {

    public static void showError(String headerMessage, String contentMessage) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText(headerMessage);
        errorAlert.setContentText(contentMessage);
        errorAlert.showAndWait();
    }
}
