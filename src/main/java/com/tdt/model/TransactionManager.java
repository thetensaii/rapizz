package com.tdt.model;

import com.tdt.core.Database;
import com.tdt.entity.Transaction;

import java.sql.*;
import java.util.ArrayList;

public class TransactionManager {
    static final String TABLE_NAME = "transaction";

    public static ArrayList<Transaction> getAll() {
        ArrayList<Transaction> transactions = new ArrayList<Transaction>();

        ResultSet rs = Database.getInstance().query("SELECT * FROM " + TABLE_NAME);

        try {
            while (rs.next()) {
                transactions.add(new Transaction(rs));
            }
        } catch (Exception e) {
            System.out.println("ERROR : " + e);
        }

        return transactions;
    }

    public static Transaction getById(int id) {
        try {
            PreparedStatement ps = Database.getInstance().createPreparedStatement("SELECT * FROM " + TABLE_NAME + " WHERE " + TABLE_NAME + "_id = ?");
            ps.setInt(1, id);
            ResultSet result = ps.executeQuery();
            if (result.next()) {
                return new Transaction(result);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static boolean deleteById(int id) {
        try {

            PreparedStatement ps = Database.getInstance().createPreparedStatement("DELETE * FROM " + TABLE_NAME + " WHERE " + TABLE_NAME + "_id = ?");
            ps.setInt(1, id);
            ResultSet result = ps.executeQuery();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static long create(Transaction transaction) {
        try {
            PreparedStatement ps = Database.getInstance().createPreparedStatement("INSERT INTO " + TABLE_NAME + "(amount, date, type, fk_client_id) VALUES (? ,?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

            ps.setFloat(1, transaction.getAmount());
            ps.setTimestamp(2, Timestamp.from(transaction.getDate().toInstant()));
            ps.setString(3, transaction.getType().toString());
            ps.setInt(4, transaction.getUser().getId());

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

    public static boolean update(Transaction transaction) {
        try {
            PreparedStatement ps = Database.getInstance().createPreparedStatement("UPDATE " + TABLE_NAME + " SET amount = ?,  date = ?, type = ?, fk_client_id = ? WHERE " + TABLE_NAME + "_id = ?");

            ps.setFloat(1, transaction.getAmount());
            ps.setTimestamp(2, Timestamp.from(transaction.getDate().toInstant()));
            ps.setString(3, transaction.getType().toString());
            ps.setInt(4, transaction.getUser().getId());
            ps.setInt(5, transaction.getId());
            ps.executeQuery();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean save(Transaction transaction) {
        if ((Integer) transaction.getId() == null)
            return create(transaction) != -1;
        else
            return update(transaction);
    }

}
