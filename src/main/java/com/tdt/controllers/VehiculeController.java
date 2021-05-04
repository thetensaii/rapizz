package com.tdt.controllers;

import com.tdt.entity.Vehicule;
import com.tdt.entity.VehiculeType;
import com.tdt.model.VehiculeManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class VehiculeController {

    @FXML
    private Button add_vehicule;
    @FXML
    private Button refresh;

    @FXML
    private TableView<Vehicule> table;
    @FXML
    private TableColumn<Vehicule, Integer> numero;
    @FXML
    private TableColumn<Vehicule, String> name;
    @FXML
    private TableColumn<Vehicule, VehiculeType> type;
    @FXML
    private TableColumn<Vehicule, Integer> operation;

    @FXML
    public void initialize() {
        System.out.println("Loading vehicules");
        numero.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));

        operation.setCellValueFactory(new PropertyValueFactory<>("id"));
        operation.setCellFactory(tc -> new TableCell<>() {
            final Button modify = new Button("\uD83D\uDD8D");
            final Button delete = new Button("\uD83D\uDDD1");
            final HBox hbox = new HBox();

            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty) {
                    setText(null);
                    modify.setOnAction(event -> {
                        Vehicule vehicule = getTableView().getItems().get(getIndex());
                        openModifyVehicleController(vehicule);
                    });
                    delete.setOnAction(event -> {
                        Vehicule vehicule = getTableView().getItems().get(getIndex());
                        System.out.println("Delete: " + vehicule.getType());
                        VehiculeManager.deleteById(vehicule.getId());
                        refresh();
                    });
                    hbox.getChildren().clear();
                    hbox.getChildren().add(modify);
                    hbox.getChildren().add(delete);
                    setGraphic(hbox);
                }
            }
        });

        add_vehicule.setOnAction(event -> {
            openModifyVehicleController(null);
        });

        refresh.setOnAction(event -> {
            refresh();
        });
        refresh();
    }

    public void openModifyVehicleController(Vehicule vehicule) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/tdt/edit_vehicle.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            if (vehicule != null) {
                ModifyVehicleController controller = fxmlLoader.getController();
                controller.setVehicleId(vehicule);
            }
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.DECORATED);
            stage.setTitle("Add Vehicle");
            stage.setScene(new Scene(root));

            stage.setOnCloseRequest(event -> {
                refresh();
            });

            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void refresh() {
        table.getItems().setAll(VehiculeManager.getAll());
        table.refresh();
    }
}
