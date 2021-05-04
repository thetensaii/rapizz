package com.tdt.controllers;

import com.tdt.entity.*;
import com.tdt.model.CommandManager;
import com.tdt.model.DeliveryManager;
import com.tdt.model.TransactionManager;
import javafx.beans.property.SimpleStringProperty;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class CommandController {
    @FXML
    private Button bt_add_command;
    @FXML
    private Button bt_refresh;

    @FXML
    private TableView<Command> table;
    @FXML
    private TableColumn<Command, Integer> tc_numero;
    @FXML
    private TableColumn<Command, String> tc_content;
    @FXML
    private TableColumn<Command, Float> tc_price;
    @FXML
    private TableColumn<Command, String> tc_date;
    @FXML
    private TableColumn<Command, String> tc_status;
    @FXML
    private TableColumn<Command, String> tc_user;

    @FXML
    private TableColumn<Command, Integer> tc_operation;


    @FXML
    public void initialize() {
        System.out.println("Loading commands");
        tc_numero.setCellValueFactory(new PropertyValueFactory<>("id"));
        tc_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        tc_content.setCellValueFactory(p -> {
            Pizza pizza = p.getValue().getPizza();
            return new SimpleStringProperty(pizza.getName());
        });
        tc_user.setCellValueFactory(p -> {
            User user = p.getValue().getClient();
            return new SimpleStringProperty(user.getLastname());
        });
        tc_date.setCellValueFactory(p -> {
            Delivery delivery = p.getValue().getDelivery();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
            if (delivery != null && delivery.getStartTime() != null)
                return new SimpleStringProperty(dateFormat.format(delivery.getStartTime()));
            return new SimpleStringProperty("Erreur");
        });

        tc_status.setCellValueFactory(p -> {
            Delivery delivery = p.getValue().getDelivery();
            if (delivery != null) {
                if (delivery.getDeliveryTime() != null) {
                    if (delivery.getDeliveryTime().getTime() > delivery.getExpectedDeliveryTime().getTime())
                        return new SimpleStringProperty("Remboursée");
                    return new SimpleStringProperty("Livrée");

                }
                else if (delivery.getDeliveryMan() != null && delivery.getDeliveryMan().getId() != 0)
                    return new SimpleStringProperty("Livraison");
            }
            return new SimpleStringProperty("En Attente");
        });

        tc_operation.setCellValueFactory(new PropertyValueFactory<>("id"));
        tc_operation.setCellFactory(tc -> new TableCell<>() {
            final Button deliver = new Button("\uD83D\uDCE9");
            final Button finish = new Button("✔");
            final Button delete = new Button("\uD83D\uDDD1");
            final HBox hbox = new HBox();

            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty) {
                    setText(null);
                    finish.setOnAction(event -> {
                        Command command = getTableView().getItems().get(getIndex());
                        System.out.println("Finish: " + command.getId());
                        Delivery delivery = command.getDelivery();
                        delivery.setDeliveryTime(new Date());
                        DeliveryManager.update(delivery);
                        if (delivery.getDeliveryTime().getTime() > delivery.getExpectedDeliveryTime().getTime()) {
                            System.out.println("Command: " + command.getId() + " need to be refund");
                            Transaction refund = new Transaction(0, command.getPrice(), new Date(), TransactionType.REFUND, command.getClient());
                            TransactionManager.create(refund);
                        }
                    });
                    deliver.setOnAction(event -> {
                        Command command = getTableView().getItems().get(getIndex());
                        openDeliveryController(command);
                        System.out.println("Deliver: " + command.getId());
                    });
                    delete.setOnAction(event -> {
                        Command command = getTableView().getItems().get(getIndex());
                        System.out.println("Delete: " + command.getId());
                        CommandManager.deleteById(command.getId());
                        refresh();
                    });
                    hbox.getChildren().clear();
                    Delivery delivery = getTableView().getItems().get(getIndex()).getDelivery();
                    if (delivery != null && delivery.getDeliveryMan() != null && delivery.getDeliveryTime() == null && delivery.getDeliveryMan().getId() != 0) {
                        hbox.getChildren().add(finish);
                    } else if (delivery != null && delivery.getDeliveryTime() == null) {
                        hbox.getChildren().add(deliver);
                    }
                    hbox.getChildren().add(delete);
                    setGraphic(hbox);
                }
            }
        });

        bt_refresh.setOnAction(event -> {
            refresh();
        });

        bt_add_command.setOnAction(event -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/tdt/add_command.fxml"));
                Parent root = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initStyle(StageStyle.DECORATED);
                stage.setTitle("Add Command");
                stage.setScene(new Scene(root));

                stage.setOnCloseRequest(e -> {
                    System.out.println("On close event");
                    refresh();
                });
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        refresh();
    }

    public void openDeliveryController(Command command) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/tdt/select_delivery.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            if (command != null) {
                SelectDeliveryManController controller = fxmlLoader.getController();
                controller.setCommand(command);
            }
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.DECORATED);
            stage.setTitle("Set Delivery");
            stage.setScene(new Scene(root));

            stage.setOnCloseRequest(event -> {
                System.out.println("On close event");
                refresh();
            });

            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void refresh() {
        table.getItems().setAll(CommandManager.getAll());
        table.refresh();
    }
}
