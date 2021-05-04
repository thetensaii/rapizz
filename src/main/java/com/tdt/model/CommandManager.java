package com.tdt.model;

import com.tdt.core.Database;
import com.tdt.entity.Command;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CommandManager {
    static final String TABLE_NAME = "command";

    public static ArrayList<Command> getAll() {
        ArrayList<Command> commands = new ArrayList<Command>();

        ResultSet rs = Database.getInstance().query("SELECT * FROM " + TABLE_NAME);

        try {
            while (rs.next()) {
                commands.add(new Command(rs));
            }
        } catch (Exception e) {
            System.out.println("ERROR CommandManager: " + e);
        }

        return commands;
    }

    public static Command getById(int id) {
        try {
            PreparedStatement ps = Database.getInstance().createPreparedStatement("SELECT * FROM " + TABLE_NAME + " WHERE " + TABLE_NAME + "_id = ?");
            ps.setInt(1, id);
            ResultSet result = ps.executeQuery();
            if (result.next()) {
                return new Command(result);
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

    public static boolean create(Command command) {
        try {
            PreparedStatement ps = Database.getInstance().createPreparedStatement("INSERT INTO " + TABLE_NAME + "(price, fk_delivery_id, fk_client_id, fk_transaction_id, fk_pizza_id, type_pizza) VALUES (? ,?, ?, ?, ?, ?)");

            ps.setFloat(1, command.getPrice());
            if (command.getDelivery() != null)
                ps.setInt(2, command.getDelivery().getId());
            else
                ps.setObject(2, null);
            if (command.getClient() != null)
                ps.setInt(3, command.getClient().getId());
            else
                ps.setObject(3, null);
            if (command.getTransaction() != null)
                ps.setInt(4, command.getTransaction().getId());
            else
                ps.setObject(4, null);
            if (command.getPizza() != null)
                ps.setInt(5, command.getPizza().getId());
            else
                ps.setObject(2, null);
            if (command.getTypePizza() != null)
                ps.setString(6, command.getTypePizza().getName());
            else
                ps.setObject(6, null);

            ps.executeUpdate();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean update(Command command) {
        try {
            PreparedStatement ps = Database.getInstance().createPreparedStatement("UPDATE " + TABLE_NAME + " SET amount = ?,  fk_delivery_id = ?, fk_client_id = ?, fk_transaction_id = ?, fk_pizza_id = ?, type_pizza = ? WHERE " + TABLE_NAME + "_id = ?");

            ps.setFloat(1, command.getPrice());
            ps.setInt(2, command.getDelivery().getId());
            ps.setInt(3, command.getClient().getId());
            ps.setInt(4, command.getTransaction().getId());
            ps.setInt(5, command.getPizza().getId());
            ps.setString(6, command.getTypePizza().getName());

            ps.setInt(7, command.getId());
            ps.executeUpdate();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean save(Command command) {
        if ((Integer) command.getId() == null)
            return create(command);
        else
            return update(command);
    }
}
