package com.tdt.controllers;

import com.tdt.entity.*;
import com.tdt.model.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.math.BigDecimal;
import java.util.Date;

public class AddCommandController {


    @FXML
    private ChoiceBox<User> userChoice;
    @FXML
    private ChoiceBox<Pizza> pizzaChoice;
    @FXML
    private ChoiceBox<PizzaType> typeChoice;

    @FXML
    private Label info;
    EventHandler<ActionEvent> onChange = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            if (userChoice.getValue() != null && pizzaChoice.getValue() != null && typeChoice.getValue() != null) {
                float amount = userChoice.getValue().getAmount();
                float price = round(pizzaChoice.getValue().getPrice() * typeChoice.getValue().price, 2);
                info.setText("Solde: " + amount + "(-" + price + ")");
            } else {
                info.setText("");
            }
        }
    };
    @FXML
    private Button add;

    public static float round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }

    @FXML
    public void initialize() {
        userChoice.setConverter(new StringConverter<User>() {
            @Override
            public String toString(User user) {
                return user.getLastname();
            }

            @Override
            public User fromString(String s) {
                return null;
            }
        });
        userChoice.getItems().setAll(UserManager.getAll());
        userChoice.setOnAction(onChange);

        pizzaChoice.setConverter(new StringConverter<Pizza>() {
            @Override
            public String toString(Pizza pizza) {
                return pizza.getName();
            }

            @Override
            public Pizza fromString(String s) {
                return null;
            }
        });
        pizzaChoice.getItems().setAll(PizzaManager.getAll());
        pizzaChoice.setOnAction(onChange);

        typeChoice.setConverter(new StringConverter<PizzaType>() {
            @Override
            public String toString(PizzaType type) {
                return type.getName();
            }

            @Override
            public PizzaType fromString(String s) {
                return null;
            }
        });
        typeChoice.getItems().addAll(PizzaType.values());
        typeChoice.setOnAction(onChange);

        add.setOnAction(event -> {
            if (userChoice.getValue() != null && pizzaChoice.getValue() != null && typeChoice.getValue() != null) {
                float amount = userChoice.getValue().getAmount();
                float price = round(pizzaChoice.getValue().getPrice() * typeChoice.getValue().price, 2);
                User user = userChoice.getValue();
                Pizza pizza = pizzaChoice.getValue();
                PizzaType typePizza = typeChoice.getValue();
                Date date = new Date();

                Delivery delivery = new Delivery(0, date, new Date(date.getTime() + (30 * 60000)), null, null, null);
                delivery.setId((int) DeliveryManager.create(delivery));

                Transaction transaction = new Transaction(0, price, date, TransactionType.PAYMENT, user);
                transaction.setId((int) TransactionManager.create(transaction));

                Command command = new Command(0, price, delivery, user, transaction, pizza, typePizza);
                CommandManager.create(command);

                Stage stage = (Stage) add.getScene().getWindow();
                stage.close();
            }
        });
    }


}
