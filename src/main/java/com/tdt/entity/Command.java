package com.tdt.entity;

import com.tdt.core.Entity;
import com.tdt.model.DeliveryManager;
import com.tdt.model.PizzaManager;
import com.tdt.model.TransactionManager;
import com.tdt.model.UserManager;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Command extends Entity {
    private float price;
    private Delivery delivery;
    private User client;
    private Transaction transaction;
    private Pizza pizza;
    private PizzaType typePizza;

    public Command(int id, float price, Delivery delivery, User client, Transaction transaction, Pizza pizza, PizzaType typePizza) {
        this.id = id;
        this.price = price;
        this.delivery = delivery;
        this.client = client;
        this.transaction = transaction;
        this.pizza = pizza;
        this.typePizza = typePizza;
    }

    public Command(ResultSet rs) throws SQLException {
        this.id = rs.getInt("command_id");
        this.price = rs.getFloat("price");
        this.delivery = DeliveryManager.getById(rs.getInt("fk_delivery_id"));
        this.client = UserManager.getById(rs.getInt("fk_client_id"));
        this.transaction = TransactionManager.getById(rs.getInt("fk_transaction_id"));
        this.pizza = PizzaManager.getById(rs.getInt("fk_pizza_id"));
        this.typePizza = PizzaType.valueOf(rs.getString("type_pizza"));
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Delivery getDelivery() {
        return delivery;
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }

    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public Pizza getPizza() {
        return pizza;
    }

    public void setPizza(Pizza pizza) {
        this.pizza = pizza;
    }

    public PizzaType getTypePizza() {
        return typePizza;
    }

    public void setTypePizza(PizzaType typePizza) {
        this.typePizza = typePizza;
    }
}
