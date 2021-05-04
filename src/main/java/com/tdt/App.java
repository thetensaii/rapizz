package com.tdt;

import com.tdt.model.StatisticManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * JavaFX App
 */
public class App extends Application {
    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("primary"));
        scene.getStylesheets().add(getClass().getResource("/com/tdt/css/style.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Rapizz");
        stage.show();

//        System.out.println(PizzaManager.getAll());
//        for(Pizza p : PizzaManager.getAll()){
//            System.out.println("id = " + p.getId() +" -- " + p.getName() + " : " + p.getPrice());
//        }

//        IngredientManager.refresh();
//        System.out.println(IngredientManager.getIngredients(1));
//        for(Ingredient i : IngredientManager.getIngredients(1)){
//            System.out.println(i.getId() + " : " + i.getName());
//        }
//
//        for(DeliveryMan dm : DeliveryManManager.getAll()){
//            System.out.println("Delivery man firstname : " + dm.getFirstname());
//        }
//
//        Pizza pizza = PizzaManager.getById(1);
//        System.out.println("NOM DE LA PIZZA : " + pizza.getName());

        ResultSet rs = StatisticManager.getBestClient();
        try {
            System.out.println(rs.getString("lastname") + " " + rs.getString("firstname") + " " + rs.getFloat("somme"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/com/tdt/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}