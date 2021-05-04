package com.tdt.controllers;

import com.tdt.core.Error;
import com.tdt.entity.Vehicule;
import com.tdt.entity.VehiculeType;
import com.tdt.model.VehiculeManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;


public class ModifyVehicleController {

    @FXML
    private Button edit;

    @FXML
    private TextField nameField;
    @FXML
    private ChoiceBox<VehiculeType> typeChoice;

    private int vehicle_id = -1;

    @FXML
    public void initialize() {
        typeChoice.setConverter(new StringConverter<VehiculeType>() {
            @Override
            public String toString(VehiculeType type) {
                return type.name();
            }

            @Override
            public VehiculeType fromString(String s) {
                return null;
            }
        });
        typeChoice.getItems().addAll(VehiculeType.values());

        edit.setOnAction(event -> {
            String name = nameField.getText();
            if (name.length() < 2) {
                Error.showError("Ajout invalide ", "Veuillez donner un nom de vehicule valide");
                return;
            }

            VehiculeType type = typeChoice.getValue();
            if (type == null) {
                Error.showError("Ajout invalide ", "Veuillez donner un type de vehicule valide");
                return;
            }
            Vehicule vehicule = new Vehicule(vehicle_id, name, type);

            if (vehicle_id == -1) {
                long id = VehiculeManager.create(vehicule);
            } else {
                VehiculeManager.update(vehicule);
            }

            Stage stage = (Stage) edit.getScene().getWindow();
            stage.close();
        });

    }


    public void setVehicleId(Vehicule vehicule) {
        this.vehicle_id = vehicule.getId();
        this.edit.setText("Modifier");
        this.nameField.setText(vehicule.getName());
        this.typeChoice.setValue(vehicule.getType());
    }
}
