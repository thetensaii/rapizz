package com.tdt.model;


import com.tdt.core.Database;
import com.tdt.entity.Pizza;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PizzaManager {
    public static List<Pizza> getAll() {

        List<Pizza> pizzas = new ArrayList<Pizza>();
        ResultSet results = Database.getInstance().query("SELECT * FROM pizza");

        try {
            while (results.next()) {
                // pizzas.add(new Pizza(Integer.parseInt(results.getString("id")), results.getString("label")));
                pizzas.add(new Pizza(results));
            }
        } catch (Exception e) {
            System.out.println("ERROR : " + e);
        }

        return pizzas;
    }

    public static Pizza getById(int id) {
        try {
            PreparedStatement ps = Database.getInstance().createPreparedStatement("SELECT * FROM pizza WHERE pizza_id = ?");
            ps.setInt(1, id);
            ResultSet result = ps.executeQuery();
            result.next();

            return new Pizza(result);

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean deleteById(int id) {
        try {
            // Suppression liaison pizza ingredients
            // CompositionManager.deletePizza(id);

            PreparedStatement ps = Database.getInstance().createPreparedStatement("DELETE FROM pizza WHERE pizza_id = ?");
            ps.setInt(1, id);
            ps.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static long create(Pizza pizza) {
        try {
            PreparedStatement ps = Database.getInstance().createPreparedStatement("INSERT INTO pizza(pizza_name, price) VALUES (? ,?)", Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, pizza.getName());
            ps.setFloat(2, pizza.getPrice());

            ps.executeUpdate();
            ResultSet result = ps.getGeneratedKeys();
            if (result.next())
                return result.getLong(1);
            return -1;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static boolean update(Pizza pizza) {
        try {
            PreparedStatement ps = Database.getInstance().createPreparedStatement("UPDATE pizza SET pizza_name = ?, price = ? Where pizza_id = ?");

            ps.setString(1, pizza.getName());
            ps.setFloat(2, pizza.getPrice());
            ps.setInt(3, pizza.getId());

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean save(Pizza pizza) {
        if ((Integer) pizza.getId() == null)
            return create(pizza) >= 0;
        else
            return update(pizza);
    }
}
