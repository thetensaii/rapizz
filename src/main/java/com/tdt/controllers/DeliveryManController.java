package com.tdt.controllers;

import com.tdt.core.Error;
import com.tdt.entity.DeliveryMan;
import com.tdt.model.DeliveryManManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DeliveryManController {
    @FXML
    private TextField t_lastname;

    @FXML
    private TextField t_firstname;

    @FXML
    private Button b_save;

    private int id;
    private DeliveryMan deliveryMan;

    @FXML
    public void initialize() {
        System.out.println(id);
        deliveryMan = new DeliveryMan();
    }

    @FXML
    public void buttonSaveClicked(ActionEvent e) {
        if (t_lastname.getText().length() < 3) {
            Error.showError("Champs invalide : ", "Le nom doit être d'au moins 3 caractères.");
            return;
        }

        if (t_firstname.getText().length() < 3) {
            Error.showError("Champs invalide : ", "Le prénom doit être d'au moins 3 caractères.");
            return;
        }

        deliveryMan.setLastname(t_lastname.getText());
        deliveryMan.setFirstname(t_firstname.getText());


        if (e.getSource() == b_save) {
            DeliveryManManager.save(deliveryMan);
            Stage stage = (Stage) b_save.getScene().getWindow();
            stage.close();
        }


    }

    public void setDeliveryManId(int id) {
        this.id = id;
        if (id != 0) {
            deliveryMan = DeliveryManManager.getById(id);
            t_lastname.setText(deliveryMan.getLastname());
            t_firstname.setText(deliveryMan.getFirstname());
        }

    }
}
