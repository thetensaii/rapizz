package com.tdt.entity;

import com.tdt.core.Entity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class User extends Entity {
    private String firstname;
    private String lastname;
    private String password;
    private float amount;

    public User() {
    }

    public User(int id, String firstname, String lastname, String password, float amount) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.amount = amount;
    }

    public User(ResultSet rs) throws SQLException {
        this.id = rs.getInt("user_id");
        this.firstname = rs.getString("firstname");
        this.lastname = rs.getString("lastname");
        this.amount = rs.getFloat("amount");
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "ID : " + id + ", Nom : " + lastname + ", Prenom : " + firstname + ", Solde : " + amount + ", Password : " + password;
    }
}
