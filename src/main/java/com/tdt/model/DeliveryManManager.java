package com.tdt.model;

import com.tdt.core.Database;
import com.tdt.entity.DeliveryMan;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DeliveryManManager {
    public static ArrayList<DeliveryMan> getAll() {

        ArrayList<DeliveryMan> deliveryMen = new ArrayList<DeliveryMan>();
        ResultSet rs = Database.getInstance().query("SELECT * FROM delivery_man WHERE delivery_man_id > 0");

        try {
            while (rs.next()) {
                deliveryMen.add(new DeliveryMan(rs));
            }
        } catch (Exception e) {
            System.out.println("ERROR : " + e);
        }

        return deliveryMen;
    }

    public static DeliveryMan getById(int id) {
        try {
            PreparedStatement ps = Database.getInstance().createPreparedStatement("SELECT * FROM delivery_man Where delivery_man_id = ?");
            ps.setInt(1, id);
            ResultSet result = ps.executeQuery();
            result.next();

            return new DeliveryMan(result);

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static DeliveryMan getByFirstname(String firstname) {
        try {
            PreparedStatement ps = Database.getInstance().createPreparedStatement("SELECT * FROM delivery_man Where firstname = ?");
            ps.setString(1, firstname);
            ResultSet result = ps.executeQuery();
            result.next();

            return new DeliveryMan(result);

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean deleteById(int id) {
        try {
            PreparedStatement ps = Database.getInstance().createPreparedStatement("DELETE FROM delivery_man WHERE delivery_man_id = ?");
            ps.setInt(1, id);
            ps.executeUpdate();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static boolean create(DeliveryMan deliveryMan) {
        try {
            PreparedStatement ps = Database.getInstance().createPreparedStatement("INSERT INTO delivery_man (firstname, lastname) VALUES (? ,?)");

            ps.setString(1, deliveryMan.getFirstname());
            ps.setString(2, deliveryMan.getLastname());

            ps.executeUpdate();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean update(DeliveryMan deliveryMan) {
        try {
            PreparedStatement ps = Database.getInstance().createPreparedStatement("UPDATE delivery_man SET firstname = ?, lastname = ? Where delivery_man_id = ?");

            ps.setString(1, deliveryMan.getFirstname());
            ps.setString(2, deliveryMan.getLastname());
            ps.setInt(3, deliveryMan.getId());

            ps.executeUpdate();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean save(DeliveryMan deliveryMan) {
        if ((Integer) deliveryMan.getId() == 0)
            return create(deliveryMan);
        else
            return update(deliveryMan);
    }
}
