package com.tdt.entity;

import com.tdt.core.Entity;
import com.tdt.model.DeliveryManManager;
import com.tdt.model.VehiculeManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Delivery extends Entity {
    private Date startTime;
    private Date expectedDeliveryTime;
    private Date deliveryTime;

    private DeliveryMan deliveryMan;
    private Vehicule vehicule;

    public Delivery(int id, Date startTime, Date expectedDeliveryTime, Date deliveryTime, DeliveryMan deliveryMan, Vehicule vehicule) {
        this.id = id;
        this.startTime = startTime;
        this.expectedDeliveryTime = expectedDeliveryTime;
        this.deliveryTime = deliveryTime;
        this.deliveryMan = deliveryMan;
        this.vehicule = vehicule;
    }

    public Delivery(ResultSet rs) throws SQLException {
        this.id = rs.getInt("delivery_id");
        this.startTime = rs.getTimestamp("start_time");
        this.expectedDeliveryTime = rs.getTimestamp("expected_delivery_time");
        this.deliveryTime = rs.getTimestamp("delivery_time");
        this.deliveryMan = DeliveryManManager.getById(rs.getInt("fk_delivery_man_id"));
        this.vehicule = VehiculeManager.getById(rs.getInt("fk_vehicule_id"));
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getExpectedDeliveryTime() {
        return expectedDeliveryTime;
    }

    public void setExpectedDeliveryTime(Date expectedDeliveryTime) {
        this.expectedDeliveryTime = expectedDeliveryTime;
    }

    public Date getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Date deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public DeliveryMan getDeliveryMan() {
        return deliveryMan;
    }

    public void setDeliveryMan(DeliveryMan deliveryMan) {
        this.deliveryMan = deliveryMan;
    }

    public Vehicule getVehicule() {
        return vehicule;
    }

    public void setVehicule(Vehicule vehicule) {
        this.vehicule = vehicule;
    }


}
