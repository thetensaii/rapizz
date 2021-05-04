package com.tdt.model;

import com.tdt.core.Database;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StatisticManager {
    public static ResultSet getBestClient() {
        ResultSet rs = Database.getInstance().query("SELECT lastname, firstname, SUM(transaction.amount) somme\n" +
                "\tFROM user JOIN transaction ON user_id = fk_client_id\n" +
                "    GROUP BY lastname, firstname\n" +
                "    ORDER BY somme DESC\n" +
                "    LIMIT 1;");
        try {
            rs.next();
        } catch (SQLException e) {
            return null;
        }
        return rs;
    }

    public static ResultSet getPizzaLowestCommand() {
        ResultSet rs = Database.getInstance().query("SELECT pizza_name, count(*) nombre\n" +
                "\tFROM pizza JOIN command ON pizza_id = fk_pizza_id\n" +
                "    GROUP BY pizza_id\n" +
                "    ORDER BY nombre\n" +
                "    LIMIT 1;");
        try {
            if (!rs.next())
                return null;
        } catch (SQLException e) {
            return null;
        }
        return rs;
    }

    public static ResultSet getPizzaHighestCommand() {
        ResultSet rs = Database.getInstance().query("SELECT pizza_name, count(*) nombre\n" +
                "\tFROM pizza JOIN command ON pizza_id = fk_pizza_id\n" +
                "    GROUP BY pizza_id\n" +
                "    ORDER BY nombre DESC\n" +
                "    LIMIT 1;");
        try {
            if (!rs.next())
                return null;
        } catch (SQLException e) {
            return null;
        }
        return rs;
    }

    public static ResultSet getBestIngredient() {
        ResultSet rs = Database.getInstance().query("SELECT ingredient_name , count(*) as nombre\n" +
                "\tFROM ingredient i , composition cp , command cm, pizza p\n" +
                "    WHERE i.ingredient_id = cp.fk_ingredient_id\n" +
                "    AND cp.fk_pizza_id = p.pizza_id \n" +
                "    AND p.pizza_id = cm.fk_pizza_id\n" +
                "    GROUP BY ingredient_name\n" +
                "    ORDER BY nombre DESC;");
        try {
            if (!rs.next())
                return null;
        } catch (SQLException e) {
            return null;
        }
        return rs;
    }

    public static ResultSet getWorstDeliveryMan() {
        ResultSet rs = Database.getInstance().query("SELECT lastname, firstname, count(*) nb_retard\n" +
                "\tFROM delivery JOIN delivery_man ON fk_delivery_man_id = delivery_man_id\n" +
                "    WHERE delivery_time > expected_delivery_time\n" +
                "    GROUP BY lastname, firstname;");

        try {
            if (!rs.next())
                return null;

        } catch (SQLException e) {
            return null;
        }
        return rs;
    }

    public static ResultSet getWorstVehicule() {
        ResultSet rs = Database.getInstance().query("SELECT vehicule_id, name, type, count(*) nb_retard\n" +
                "FROM delivery JOIN vehicule ON fk_vehicule_id = vehicule_id\n" +
                "WHERE delivery_time > expected_delivery_time\n" +
                "GROUP BY vehicule_id, name, type;");

        try {
            if (!rs.next())
                return null;
        } catch (SQLException e) {
            return null;
        }
        return rs;
    }

}
