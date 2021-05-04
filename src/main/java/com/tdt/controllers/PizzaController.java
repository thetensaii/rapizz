package com.tdt.controllers;

import com.tdt.entity.Ingredient;
import com.tdt.entity.Pizza;
import com.tdt.model.CompositionManager;
import com.tdt.model.PizzaManager;
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

import java.util.List;


public class PizzaController {

    @FXML
    private Button add_pizza;
    @FXML
    private Button refresh;

    @FXML
    private TableView<Pizza> table;
    @FXML
    private TableColumn<Pizza, Integer> numero;
    @FXML
    private TableColumn<Pizza, String> name;
    @FXML
    private TableColumn<Pizza, String> ingredients;
    @FXML
    private TableColumn<Pizza, Float> price_naine;
    @FXML
    private TableColumn<Pizza, Float> price_humaine;
    @FXML
    private TableColumn<Pizza, Float> price_ogresse;
    @FXML
    private TableColumn<Pizza, Integer> operation;

    @FXML
    public void initialize() {
        System.out.println("Loading pizzas");
        numero.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        ingredients.setCellValueFactory(p -> {
            List<Ingredient> ingredients = p.getValue().getIngredients();
            String content = "";
            if (ingredients != null)
                for (Ingredient ingredient : ingredients)
                    content += ingredient.getName() + "; ";
            return new SimpleStringProperty(content);
        });
        price_naine.setCellValueFactory(new PropertyValueFactory<>("price"));
        price_humaine.setCellValueFactory(new PropertyValueFactory<>("price"));
        price_ogresse.setCellValueFactory(new PropertyValueFactory<>("price"));
        price_naine.setCellFactory(tc -> new TableCell<>() {
            @Override
            protected void updateItem(Float price, boolean empty) {
                super.updateItem(price, empty);
                if (!empty) {
                    price = (2.0f / 3.0f) * price;
                    setText(String.format("%.2f €", price.doubleValue()));
                } else {
                    setText("");
                }
            }
        });

        price_humaine.setCellFactory(tc -> new TableCell<>() {
            @Override
            protected void updateItem(Float price, boolean empty) {
                super.updateItem(price, empty);
                if (!empty)
                    setText(String.format("%.2f €", price.doubleValue()));
                else
                    setText("");
            }
        });
        price_ogresse.setCellFactory(tc -> new TableCell<>() {
            @Override
            protected void updateItem(Float price, boolean empty) {
                super.updateItem(price, empty);
                if (!empty) {
                    price = (4.0f / 3.0f) * price;
                    setText(String.format("%.2f €", price.doubleValue()));
                } else {
                    setText("");
                }
            }
        });

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
                        Pizza pizza = getTableView().getItems().get(getIndex());
                        openModifyPizzaController(pizza);
                        System.out.println("Modify: " + pizza.getName() + "   " + pizza.getPrice());
                    });
                    delete.setOnAction(event -> {
                        Pizza pizza = getTableView().getItems().get(getIndex());
                        System.out.println("Delete: " + pizza.getName() + "   " + pizza.getPrice());
                        PizzaManager.deleteById(pizza.getId());
                        refresh();
                    });
                    hbox.getChildren().clear();
                    hbox.getChildren().add(modify);
                    hbox.getChildren().add(delete);
                    setGraphic(hbox);
                }
            }
        });

        add_pizza.setOnAction(event -> {
            openModifyPizzaController(null);
        });

        refresh.setOnAction(event -> {
            refresh();
        });
        refresh();
    }

    public void openModifyPizzaController(Pizza pizza) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/tdt/edit_pizza.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            if (pizza != null) {
                ModifyPizzaController controller = fxmlLoader.getController();
                controller.setPizzaId(pizza);
            }
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.DECORATED);
            stage.setTitle("Add Pizza");
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
        CompositionManager.refresh();
        table.getItems().setAll(PizzaManager.getAll());
        table.refresh();
    }
}
