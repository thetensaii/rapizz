package com.tdt.entity;

public enum PizzaType {
    ogresse("Ogresse", 0.66f),
    humaine("Humaine", 1),
    naine("Naine", 1.33f);

    public final String name;
    public final float price;

    PizzaType(String name, float price) {
        this.name = name;
        this.price = price;
    }

    public float getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }
}
