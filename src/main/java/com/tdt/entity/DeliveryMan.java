package com.tdt.entity;

import com.tdt.core.Entity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DeliveryMan extends Entity {
    private String firstname;
    private String lastname;

    public DeliveryMan() {
    }

    public DeliveryMan(int id, String firstname, String lastname) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public DeliveryMan(ResultSet rs) throws SQLException {
        this.id = rs.getInt("delivery_man_id");
        this.firstname = rs.getString("firstname");
        this.lastname = rs.getString("lastname");
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
}
