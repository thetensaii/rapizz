package com.tdt.entity;

import com.tdt.core.Entity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Vehicule extends Entity {
    private VehiculeType type;
    private String name;

    public Vehicule(int id, String name, VehiculeType type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public Vehicule(ResultSet rs) throws SQLException {
        this.id = rs.getInt("vehicule_id");
        this.name = rs.getString("name");
        try {
            this.type = VehiculeType.valueOf(rs.getString("type"));
        } catch (Exception e) {
        }
    }

    public VehiculeType getType() {
        return type;
    }

    public void setType(VehiculeType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}