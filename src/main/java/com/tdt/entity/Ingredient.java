package com.tdt.entity;

import com.tdt.core.Entity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Ingredient extends Entity {
    private String name;

    public Ingredient(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Ingredient(ResultSet rs) throws SQLException {
        this.id = rs.getInt("ingredient_id");
        this.name = rs.getString("ingredient_name");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
