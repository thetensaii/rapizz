package com.tdt.controllers;

import com.tdt.entity.User;
import com.tdt.model.UserManager;
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

public class UsersController {

    @FXML
    private Button b_add_user;

    @FXML
    private TableView<User> table;

    @FXML
    private TableColumn<User, Integer> tc_numero;

    @FXML
    private TableColumn<User, String> tc_lastname;

    @FXML
    private TableColumn<User, String> tc_firstname;

    @FXML
    private TableColumn<User, Float> tc_amount;

    @FXML
    private TableColumn<User, Integer> tc_operation;

    @FXML
    private Button b_refresh;

    @FXML
    public void initialize() {
        tc_numero.setCellValueFactory(new PropertyValueFactory<>("id"));
        tc_lastname.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        tc_firstname.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        tc_amount.setCellValueFactory(new PropertyValueFactory<>("amount"));


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
                        User user = getTableView().getItems().get(getIndex());
                        try {
                            getFormUser(user.getId());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        System.out.println("Modify: " + user.getLastname() + "   " + user.getFirstname());
                    });
                    delete.setOnAction(event -> {
                        User user = getTableView().getItems().get(getIndex());
                        System.out.println("Delete: " + user.getLastname() + "   " + user.getFirstname());
                        UserManager.deleteById(user.getId());
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

        table.getItems().setAll(UserManager.getAll());

    }

    @FXML
    private void addUserClicked(ActionEvent e) throws Exception {
        getFormUser(0);
    }

    private void getFormUser(int id) throws Exception {
        Stage newWindow = new Stage();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tdt/user.fxml"));
        Parent formScene = loader.load();

        UserController controller = loader.getController();
        controller.setUserId(id);

        // Specifies the modality for new window.
        newWindow.initModality(Modality.WINDOW_MODAL);

        // Specifies the owner Window (parent) for new window
        Stage parentWindow = (Stage) b_add_user.getScene().getWindow();
        newWindow.initOwner(parentWindow);

        newWindow.setScene(new Scene(formScene));

        newWindow.show();
    }

    public void refresh() {
        table.getItems().setAll(UserManager.getAll());
        table.refresh();
    }

}
