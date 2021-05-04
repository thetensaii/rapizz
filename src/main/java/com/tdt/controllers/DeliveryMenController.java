package com.tdt.controllers;

import com.tdt.entity.DeliveryMan;
import com.tdt.model.DeliveryManManager;
import javafx.event.ActionEvent;
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

public class DeliveryMenController {
    @FXML
    private Button b_add_delivery_man;

    @FXML
    private TableView<DeliveryMan> table;

    @FXML
    private TableColumn<DeliveryMan, Integer> tc_numero;

    @FXML
    private TableColumn<DeliveryMan, String> tc_lastname;

    @FXML
    private TableColumn<DeliveryMan, String> tc_firstname;

    @FXML
    private TableColumn<DeliveryMan, Integer> tc_operation;

    @FXML
    private Button b_refresh;

    @FXML
    public void initialize() {
        tc_numero.setCellValueFactory(new PropertyValueFactory<>("id"));
        tc_lastname.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        tc_firstname.setCellValueFactory(new PropertyValueFactory<>("firstname"));


        tc_operation.setCellValueFactory(new PropertyValueFactory<>("id"));
        tc_operation.setCellFactory(tc -> new TableCell<>() {
            final Button modify = new Button("\uD83D\uDD8D");
            final Button delete = new Button("\uD83D\uDDD1");
            final HBox hbox = new HBox();

            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty) {
                    setText(null);
                    modify.setOnAction(event -> {
                        DeliveryMan deliveryMan = getTableView().getItems().get(getIndex());
                        try {
                            getFormDeliveryMan(deliveryMan.getId());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        System.out.println("Modify: " + deliveryMan.getLastname() + "   " + deliveryMan.getFirstname());
                    });
                    delete.setOnAction(event -> {
                        DeliveryMan deliveryMan = getTableView().getItems().get(getIndex());
                        System.out.println("Delete: " + deliveryMan.getLastname() + "   " + deliveryMan.getFirstname());
                        DeliveryManManager.deleteById(deliveryMan.getId());
                        refresh();
                    });
                    hbox.getChildren().clear();
                    hbox.getChildren().add(modify);
                    hbox.getChildren().add(delete);
                    setGraphic(hbox);
                }
            }
        });

        b_refresh.setOnAction(event -> {
            refresh();
        });

        table.getItems().setAll(DeliveryManManager.getAll());

    }

    @FXML
    private void addDeliveryManClicked(ActionEvent e) throws Exception {
        getFormDeliveryMan(0);
    }

    private void getFormDeliveryMan(int id) throws Exception {
        Stage newWindow = new Stage();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tdt/delivery_man.fxml"));
        Parent formScene = loader.load();

        DeliveryManController controller = loader.getController();
        controller.setDeliveryManId(id);

        // Specifies the modality for new window.
        newWindow.initModality(Modality.WINDOW_MODAL);

        // Specifies the owner Window (parent) for new window
        Stage parentWindow = (Stage) b_add_delivery_man.getScene().getWindow();
        newWindow.initOwner(parentWindow);

        newWindow.setScene(new Scene(formScene));

        newWindow.show();
    }

    public void refresh() {
        table.getItems().setAll(DeliveryManManager.getAll());
        table.refresh();
    }

}
