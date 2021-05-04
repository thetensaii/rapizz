package com.tdt.model;

import com.tdt.core.Database;
import com.tdt.entity.Ingredient;

import java.sql.ResultSet;
import java.util.ArrayList;

public class IngredientManager {
    public static ArrayList<Ingredient> ingredients = new ArrayList<>();


    public static Ingredient getIngredientById(int id) {
        for (Ingredient ingredient : ingredients) {
            if (ingredient.getId() == id)
                return ingredient;
        }
        return null;
    }

    public static Ingredient getIngredientByName(String name) {
        for (Ingredient ingredient : ingredients) {
            if (ingredient.getName().equalsIgnoreCase(name)) ;
            return ingredient;
        }
        return null;
    }

    public static void addIngredient(Ingredient ingredient) {
        if (ingredients.indexOf(ingredient) < 0)
            ingredients.add(ingredient);
    }

    public static void removeIngredient(Ingredient ingredient) {
        if (ingredients.indexOf(ingredient) > 0)
            ingredients.remove(ingredient);
    }

    public static void refresh() {
        ResultSet results = Database.getInstance().query("SELECT * FROM ingredient;");
        ingredients.clear();
        try {
            while (results.next())
                ingredients.add(new Ingredient(results));
        } catch (Exception e) {
            System.out.println("ERROR : " + e);
        }
    }
}
