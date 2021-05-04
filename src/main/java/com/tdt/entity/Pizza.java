package com.tdt.entity;

import com.tdt.core.*;
import com.tdt.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Pizza extends Entity {
    private String name;
    private float price;

    public Pizza(int id, String name, float price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Pizza(ResultSet rs) throws SQLException {
        id = rs.getInt("pizza_id");
        name = rs.getString("pizza_name");
        price = rs.getFloat("price");
        System.out.println(price);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public List<Ingredient> getIngredients() {
        return CompositionManager.getIngredients(id);
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        CompositionManager.setIngredients(id, ingredients);
    }

}
