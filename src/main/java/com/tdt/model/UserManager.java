package com.tdt.model;

import com.tdt.core.Database;
import com.tdt.entity.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserManager {
    static final String TABLE_NAME = "user";

    public static List<User> getAll() {
        List<User> deliveries = new ArrayList<>();

        ResultSet rs = Database.getInstance().query("SELECT " + TABLE_NAME + "_id, lastname, firstname, amount FROM " + TABLE_NAME);

        try {
            while (rs.next()) {
                deliveries.add(new User(rs));
            }
        } catch (Exception e) {
            System.out.println("ERROR : " + e);
        }

        return deliveries;
    }

    public static User getById(int id) {
        try {
            PreparedStatement ps = Database.getInstance().createPreparedStatement("SELECT " + TABLE_NAME + "_id, lastname, firstname, amount FROM " + TABLE_NAME + " WHERE " + TABLE_NAME + "_id = ?");
            ps.setInt(1, id);
            ResultSet result = ps.executeQuery();
            if (result.next()) {
                return new User(result);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static boolean deleteById(int id) {
        try {

            PreparedStatement ps = Database.getInstance().createPreparedStatement("DELETE FROM " + TABLE_NAME + " WHERE " + TABLE_NAME + "_id = ?");
            ps.setInt(1, id);
            ps.executeUpdate();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    private static boolean create(User user) {
        try {
            PreparedStatement ps = Database.getInstance().createPreparedStatement("INSERT INTO " + TABLE_NAME + "(firstname, lastname, password, amount) VALUES (? ,?, ?, ?)");

            ps.setString(1, user.getFirstname());
            ps.setString(2, user.getLastname());
            ps.setString(3, user.getPassword());
            ps.setFloat(4, user.getAmount());

            ps.executeUpdate();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static boolean update(User user) {

        try {
            PreparedStatement ps;
            if (user.getPassword() == null) {
                ps = Database.getInstance().createPreparedStatement("UPDATE " + TABLE_NAME + " SET firstname = ?,  lastname = ?, amount = ? WHERE " + TABLE_NAME + "_id = ?");
                ps.setString(1, user.getFirstname());
                ps.setString(2, user.getLastname());
                ps.setFloat(3, user.getAmount());
                ps.setInt(4, user.getId());
            } else {
                ps = Database.getInstance().createPreparedStatement("UPDATE " + TABLE_NAME + " SET firstname = ?,  lastname = ?, password = ?, amount = ? WHERE " + TABLE_NAME + "_id = ?");

                ps.setString(1, user.getFirstname());
                ps.setString(2, user.getLastname());
                ps.setString(3, user.getPassword());
                ps.setFloat(4, user.getAmount());
                ps.setInt(5, user.getId());
            }

            ps.executeUpdate();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean save(User user) {
        if (user.getId() == 0)
            return create(user);
        else
            return update(user);
    }
}
