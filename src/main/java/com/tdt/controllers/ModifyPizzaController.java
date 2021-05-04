package com.tdt.controllers;

import com.tdt.core.Error;
import com.tdt.entity.Ingredient;
import com.tdt.entity.Pizza;
import com.tdt.model.CompositionManager;
import com.tdt.model.IngredientManager;
import com.tdt.model.PizzaManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.util.ArrayList;
import java.util.List;


public class ModifyPizzaController {

    @FXML
    private Button edit;

    @FXML
    private TextField nameField;
    @FXML
    private TextField priceField;

    @FXML
    private ListView<String> ingredientsList;
    @FXML
    private ChoiceBox<Ingredient> ingredientChoice;
    @FXML
    private Button addIngredient;
    @FXML
    private Button removeIngredient;


    private List<Ingredient> ingredients = new ArrayList<>();
    private int pizza_id = -1;

    @FXML
    public void initialize() {
        edit.setOnAction(event -> {
            String name = nameField.getText();
            if (name.length() < 2) {
                Error.showError("Ajout invalide ", "Veuillez donner un nom de pizza valide");
                return;
            }

            String priceString = priceField.getText();
            float price = 0;
            try {
                price = Float.parseFloat(priceString);
            } catch (Exception e) {
                Error.showError("Ajout invalide ", "Veuillez donner un prix valide");
                return;
            }
            if (price <= 1) {
                Error.showError("Ajout invalide ", "Veuillez donner un prix supérieur à 1€");
                return;
            }

            if (ingredientsList.getItems().size() == 0) {
                Error.showError("Ajout invalide ", "Veuillez ajouter au moins 1 ingredient");
                return;
            }

            Pizza pizza = new Pizza(pizza_id, name, price);
            if (pizza_id == -1) {
                long id = PizzaManager.create(pizza);
                CompositionManager.setIngredients((int) id, ingredients);
            } else {
                PizzaManager.update(pizza);
                CompositionManager.setIngredients(pizza_id, ingredients);
            }
            CompositionManager.save();

            Stage stage = (Stage) edit.getScene().getWindow();
            stage.close();
        });

        ingredientChoice.setConverter(new StringConverter<Ingredient>() {
            @Override
            public String toString(Ingredient ingredient) {
                return ingredient.getName();
            }

            @Override
            public Ingredient fromString(String s) {
                return IngredientManager.getIngredientByName(s);
            }
        });
        ingredientChoice.getItems().setAll(IngredientManager.ingredients);

        addIngredient.setOnAction(event -> {
            Ingredient choice = ingredientChoice.getValue();
            if (!ingredients.contains(choice))
                ingredients.add(choice);
            refreshIngredientsList();
        });

        removeIngredient.setOnAction(event -> {
            String name = ingredientsList.getFocusModel().getFocusedItem();
            for (Ingredient ingredient : ingredients)
                if (ingredient.getName().equalsIgnoreCase(name)) {
                    ingredients.remove(ingredient);
                    refreshIngredientsList();
                    return;
                }
        });
        refreshIngredientsList();
    }

    public void refreshIngredientsList() {
        List<String> values = new ArrayList<>();
        for (Ingredient ingredient : ingredients)
            values.add(ingredient.getName());
        ingredientsList.getItems().setAll(values);
    }

    public void setPizzaId(Pizza pizza) {
        this.pizza_id = pizza.getId();
        this.edit.setText("Modifier");
        this.nameField.setText(pizza.getName());
        this.priceField.setText(pizza.getPrice() + "");
        this.ingredients = new ArrayList<>(pizza.getIngredients());
        refreshIngredientsList();
    }
}
