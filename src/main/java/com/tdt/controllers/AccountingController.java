package com.tdt.controllers;

import com.tdt.entity.Transaction;
import com.tdt.entity.TransactionType;
import com.tdt.entity.User;
import com.tdt.model.CompositionManager;
import com.tdt.model.TransactionManager;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AccountingController {
    @FXML
    private Button refresh;

    @FXML
    private TableView<Transaction> tv_table;
    @FXML
    private TableColumn<Transaction, Integer> tc_numero;
    @FXML
    private TableColumn<Transaction, String> tc_user;
    @FXML
    private TableColumn<Transaction, TransactionType> tc_transaction;
    @FXML
    private TableColumn<Transaction, String> tc_date;
    @FXML
    private TableColumn<Transaction, String> tc_amount;

    @FXML
    public void initialize() {
        System.out.println("Loading pizzas");
        tc_numero.setCellValueFactory(new PropertyValueFactory<>("id"));
        tc_user.setCellValueFactory(p -> {
            User user = p.getValue().getUser();
            if (user != null)
                return new SimpleStringProperty(user.getLastname());
            return new SimpleStringProperty("Erreur");
        });
        tc_transaction.setCellValueFactory(new PropertyValueFactory<>("type"));

        tc_date.setCellValueFactory(p -> {
            Date date = p.getValue().getDate();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
            if (date != null)
                return new SimpleStringProperty(dateFormat.format(date));
            return new SimpleStringProperty("Erreur");
        });

        tc_amount.setCellValueFactory(p -> new SimpleStringProperty(String.format("%.2f €", p.getValue().getAmount())));

        refresh.setOnAction(event -> {
            refresh();
        });
        refresh();
    }

    public void refresh() {
        CompositionManager.refresh();
        tv_table.getItems().setAll(TransactionManager.getAll());
        tv_table.refresh();
    }
}
