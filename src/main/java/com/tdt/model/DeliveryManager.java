package com.tdt.model;

import com.tdt.core.Database;
import com.tdt.entity.Delivery;

import java.sql.*;
import java.util.ArrayList;

public class DeliveryManager {

    static final String TABLE_NAME = "delivery";

    public static ArrayList<Delivery> getAll() {
        ArrayList<Delivery> deliveries = new ArrayList<Delivery>();

        ResultSet rs = Database.getInstance().query("SELECT * FROM " + TABLE_NAME);

        try {
            while (rs.next()) {
                deliveries.add(new Delivery(rs));
            }
        } catch (Exception e) {
            System.out.println("ERROR : " + e);
        }

        return deliveries;
    }

    public static Delivery getById(int id) {
        try {
            PreparedStatement ps = Database.getInstance().createPreparedStatement("SELECT * FROM " + TABLE_NAME + " WHERE delivery_id = ?");
            ps.setInt(1, id);
            ResultSet result = ps.executeQuery();
            if (result.next()) {
                return new Delivery(result);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static boolean deleteById(int id) {
        try {

            PreparedStatement ps = Database.getInstance().createPreparedStatement("DELETE * FROM " + TABLE_NAME + " WHERE delivery_id = ?");
            ps.setInt(1, id);
            ResultSet result = ps.executeQuery();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static long create(Delivery delivery) {
        try {
            PreparedStatement ps = Database.getInstance().createPreparedStatement("INSERT INTO " + TABLE_NAME + "(start_time, expected_delivery_time, delivery_time, fk_delivery_man_id, fk_vehicule_id) VALUES (? ,?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

            if (delivery.getStartTime() != null)
                ps.setTimestamp(1, Timestamp.from(delivery.getStartTime().toInstant()));
            else
                ps.setTimestamp(1, null);
            if (delivery.getExpectedDeliveryTime() != null)
                ps.setTimestamp(2, Timestamp.from(delivery.getExpectedDeliveryTime().toInstant()));
            else
                ps.setTimestamp(2, null);
            if (delivery.getDeliveryTime() != null)
                ps.setTimestamp(3, Timestamp.from(delivery.getDeliveryTime().toInstant()));
            else
                ps.setTimestamp(3, null);
            if (delivery.getDeliveryMan() != null)
                ps.setInt(4, delivery.getDeliveryMan().getId());
            else
                ps.setInt(4, 0);
            if (delivery.getVehicule() != null)
                ps.setInt(5, delivery.getVehicule().getId());
            else
                ps.setInt(5, 0);

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

    public static boolean update(Delivery delivery) {
        try {
            PreparedStatement ps = Database.getInstance().createPreparedStatement("UPDATE " + TABLE_NAME + " SET start_time = ?,  expected_delivery_time = ?, delivery_time = ?, fk_delivery_man_id = ?, fk_vehicule_id = ? WHERE delivery_id = ?");

            if (delivery.getStartTime() != null)
                ps.setTimestamp(1, Timestamp.from(delivery.getStartTime().toInstant()));
            else
                ps.setTimestamp(1, null);
            if (delivery.getExpectedDeliveryTime() != null)
                ps.setTimestamp(2, Timestamp.from(delivery.getExpectedDeliveryTime().toInstant()));
            else
                ps.setTimestamp(2, null);
            if (delivery.getDeliveryTime() != null)
                ps.setTimestamp(3, Timestamp.from(delivery.getDeliveryTime().toInstant()));
            else
                ps.setTimestamp(3, null);
            ps.setInt(4, delivery.getDeliveryMan().getId());
            ps.setInt(5, delivery.getVehicule().getId());
            ps.setInt(6, delivery.getId());
            ps.executeUpdate();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean save(Delivery delivery) {
        if ((Integer) delivery.getId() == null)
            return create(delivery) != -1;
        else
            return update(delivery);
    }
}

