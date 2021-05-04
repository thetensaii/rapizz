package com.tdt.controllers;

import com.tdt.core.Error;
import com.tdt.entity.Command;
import com.tdt.entity.Delivery;
import com.tdt.entity.DeliveryMan;
import com.tdt.entity.Vehicule;
import com.tdt.model.DeliveryManManager;
import com.tdt.model.DeliveryManager;
import com.tdt.model.VehiculeManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class SelectDeliveryManController {
    @FXML
    private Button validate;


    @FXML
    private ChoiceBox<DeliveryMan> deliveryManChoice;
    @FXML
    private ChoiceBox<Vehicule> vehicleChoice;

    private Command command;

    @FXML
    public void initialize() {
        validate.setOnAction(event -> {
            DeliveryMan deliveryMan = deliveryManChoice.getValue();
            if (deliveryMan == null) {
                Error.showError("Livreur invalide", "Veuillez selectionner un livreur");
                return;
            }

            Vehicule vehicle = vehicleChoice.getValue();
            if (vehicle == null) {
                Error.showError("Véhicule invalide", "Veuillez selectionner un véhicule");
                return;
            }
            Delivery delivery = command.getDelivery();
            delivery.setDeliveryMan(deliveryMan);
            delivery.setVehicule(vehicle);
            DeliveryManager.update(delivery);

            Stage stage = (Stage) validate.getScene().getWindow();
            stage.close();
        });

        deliveryManChoice.setConverter(new StringConverter<DeliveryMan>() {
            @Override
            public String toString(DeliveryMan deliveryMan) {
                return deliveryMan.getFirstname();
            }

            @Override
            public DeliveryMan fromString(String s) {
                System.out.println(s);
                return DeliveryManManager.getByFirstname(s);
            }
        });
        deliveryManChoice.getItems().setAll(DeliveryManManager.getAll());

        vehicleChoice.setConverter(new StringConverter<Vehicule>() {
            @Override
            public String toString(Vehicule vehicle) {
                return vehicle.getName();
            }

            @Override
            public Vehicule fromString(String s) {
                return VehiculeManager.getByType(s);
            }
        });
        vehicleChoice.getItems().setAll(VehiculeManager.getAll());

    }


    public void setCommand(Command command) {
        this.command = command;
    }
}
