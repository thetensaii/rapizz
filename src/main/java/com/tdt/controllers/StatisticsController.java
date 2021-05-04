package com.tdt.controllers;

import com.tdt.model.StatisticManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.sql.ResultSet;

public class StatisticsController {
    @FXML
    private Label l_best_client;

    @FXML
    private Label l_lowest_pizza;

    @FXML
    private Label l_highest_pizza;

    @FXML
    private Label l_best_ingredient;

    @FXML
    private Label l_worst_delivery_man;

    @FXML
    private Label l_worst_vehicule;

    @FXML
    public void initialize() throws Exception {
        System.out.println("Loading stats");
        String text;
        ResultSet rs = StatisticManager.getBestClient();
        if (rs == null) {
            text = "Pas de données";
        } else {
            text = rs.getString("lastname") + " " + rs.getString("firstname") + " (" + rs.getFloat("somme") + ")";
        }
        l_best_client.setText(text);

        rs = StatisticManager.getPizzaLowestCommand();
        if (rs == null) {
            text = "Pas de données";
        } else {
            text = rs.getString("pizza_name") + " (" + rs.getInt("nombre") + ")";
        }
        l_lowest_pizza.setText(text);

        rs = StatisticManager.getPizzaHighestCommand();
        if (rs == null) {
            text = "Pas de données";
        } else {
            text = rs.getString("pizza_name") + " (" + rs.getInt("nombre") + ")";
        }
        l_highest_pizza.setText(text);

        rs = StatisticManager.getBestIngredient();
        if (rs == null) {
            text = "Pas de données";
        } else {
            text = rs.getString("ingredient_name") + " (" + rs.getInt("nombre") + ")";
        }
        l_best_ingredient.setText(text);

        rs = StatisticManager.getWorstDeliveryMan();
        if (rs == null) {
            text = "Pas de données";
        } else {
            text = rs.getString("lastname") + " " + rs.getString("firstname") + " (" + rs.getInt("nb_retard") + ")";
        }
        l_worst_delivery_man.setText(text);

        rs = StatisticManager.getWorstVehicule();
        if (rs == null)
            text = "Pas de données";
        else
            text = rs.getString("name") + " (" + rs.getInt("nb_retard") + ")";
        l_worst_vehicule.setText(text);

    }
}
