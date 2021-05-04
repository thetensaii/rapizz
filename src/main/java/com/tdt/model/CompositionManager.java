package com.tdt.model;

import com.tdt.core.Database;
import com.tdt.entity.Ingredient;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class CompositionManager {
    public static HashMap<Integer, List<Ingredient>> compositions = new HashMap<>();

    public static List<Ingredient> getIngredients(int pizza_id) {
        if (compositions.containsKey(pizza_id))
            return compositions.get(pizza_id);
        return new ArrayList<>();
    }

    public static void setIngredients(int pizza_id, List<Ingredient> ingredients) {
        compositions.put(pizza_id, ingredients);
    }

    public static void addIngredients(int pizza_id, Ingredient ingredient) {
        List<Ingredient> ingredients = compositions.getOrDefault(pizza_id, new ArrayList<>());
        ingredients.add(ingredient);
        compositions.put(pizza_id, ingredients);
    }

    public static void removeIngredients(int pizza_id, Ingredient ingredient) {
        List<Ingredient> ingredients = compositions.getOrDefault(pizza_id, new ArrayList<>());
        ingredients.remove(ingredient);
        compositions.put(pizza_id, ingredients);
    }

    public static void save() {
        try {
            PreparedStatement st = Database.getInstance().createPreparedStatement("REPLACE INTO composition(fk_pizza_id,fk_ingredient_id) VALUES(?,?)");
            Iterator<Integer> keys = compositions.keySet().iterator();
            while (keys.hasNext()) {
                Integer pizza_id = keys.next();
                List<Ingredient> ingredients = compositions.get(pizza_id);
                for (Ingredient ingredient : ingredients) {
                    System.out.println("Batch: " + ingredient.getName());
                    st.setInt(1, pizza_id);
                    st.setInt(2, ingredient.getId());
                    st.addBatch();
                }
            }
            st.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void refresh() {
        IngredientManager.refresh();
        compositions.clear();
        try {
            ResultSet set = Database.getInstance().query("SELECT * FROM composition;");
            while (set.next()) {
                List<Ingredient> ingredients = compositions.getOrDefault(set.getInt("fk_pizza_id"), new ArrayList<>());
                ingredients.add(IngredientManager.getIngredientById(set.getInt("fk_ingredient_id")));
                compositions.put(set.getInt("fk_pizza_id"), ingredients);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
