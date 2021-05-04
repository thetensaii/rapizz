package com.tdt.entity;

import com.tdt.core.Entity;
import com.tdt.model.UserManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Transaction extends Entity {
    private float amount;
    private Date date;
    private User user;
    private TransactionType type;

    public Transaction(int id, float amount, Date date, TransactionType type, User user) {
        this.id = id;
        this.amount = amount;
        this.date = date;
        this.type = type;
        this.user = user;
    }

    public Transaction(ResultSet rs) throws SQLException {
        this.id = rs.getInt("transaction_id");
        this.amount = rs.getFloat("amount");
        this.date = rs.getTimestamp("date");
        this.type = TransactionType.valueOf(rs.getString("type"));
        this.user = UserManager.getById(rs.getInt("fk_client_id"));
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
