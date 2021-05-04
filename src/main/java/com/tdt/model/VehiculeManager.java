package com.tdt.model;

import com.tdt.core.Database;
import com.tdt.entity.Vehicule;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class VehiculeManager {

    public static ArrayList<Vehicule> getAll() {
        ArrayList<Vehicule> vehicules = new ArrayList<Vehicule>();
        ResultSet results = Database.getInstance().query("SELECT * FROM vehicule WHERE vehicule_id > 0");

        try {
            while (results.next()) {
                vehicules.add(new Vehicule(results));
            }
        } catch (Exception e) {
            System.out.println("ERROR VehiculeManager : " + e);
        }

        return vehicules;
    }

    public static Vehicule getById(int id) {
        try {
            PreparedStatement ps = Database.getInstance().createPreparedStatement("SELECT * FROM vehicule Where vehicule_id = ?");
            ps.setInt(1, id);
            ResultSet result = ps.executeQuery();
            result.next();

            return new Vehicule(result);

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Vehicule getByType(String type) {
        try {
            PreparedStatement ps = Database.getInstance().createPreparedStatement("SELECT * FROM vehicule Where type = ? AND vehicule_id > 0");
            ps.setString(1, type);
            ResultSet result = ps.executeQuery();
            result.next();

            return new Vehicule(result);

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean deleteById(int id) {
        try {

            PreparedStatement ps = Database.getInstance().createPreparedStatement("DELETE * FROM vehicule WHERE vehicule_id = ?");
            ps.setInt(1, id);
            ResultSet result = ps.executeQuery();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static long create(Vehicule vehicule) {
        try {
            PreparedStatement ps = Database.getInstance().createPreparedStatement("INSERT INTO vehicule(type,name) VALUES (? ,?)", Statement.RETURN_GENERATED_KEYS);

            if (vehicule.getType() != null)
                ps.setString(1, vehicule.getType().name());
            else
                ps.setString(1, null);
            ps.setString(2, vehicule.getName());

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

    public static boolean update(Vehicule vehicule) {
        try {
            PreparedStatement ps = Database.getInstance().createPreparedStatement("UPDATE vehicule SET type = ?, name = ? WHERE vehicule_id = ?");

            if (vehicule.getType() != null)
                ps.setString(1, vehicule.getType().name());
            else
                ps.setString(1, null);
            ps.setString(2, vehicule.getName());
            ps.setInt(3, vehicule.getId());

            ps.executeUpdate();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean save(Vehicule vehicule) {
        if ((Integer) vehicule.getId() == null)
            return create(vehicule) == -1;
        else
            return update(vehicule);
    }
}

