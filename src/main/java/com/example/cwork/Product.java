package com.example.cwork;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Product {
    private final StringProperty name;
    private final Manufacturer manufacturer; // Используем объект Manufacturer
    private final IntegerProperty quantity;
    private final StringProperty date;

    // Конструктор, принимающий Manufacturer
    public Product(String name, Manufacturer manufacturer, int quantity, String date) {
        this.name = new SimpleStringProperty(name);
        this.manufacturer = manufacturer;
        this.quantity = new SimpleIntegerProperty(quantity);
        this.date = new SimpleStringProperty(date);
    }

    // Геттеры и свойства
    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public String getManufacturerName() {
        return manufacturer.getName();
    }

    public String getManufacturerAddress() {
        return manufacturer.getAddress();
    }

    public StringProperty manufacturerProperty() {
        return new SimpleStringProperty(manufacturer.getName());
    }

    public StringProperty manufacturerAddressProperty() {
        return new SimpleStringProperty(manufacturer.getAddress());
    }

    public int getQuantity() {
        return quantity.get();
    }

    public IntegerProperty quantityProperty() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
    }

    public String getDate() {
        return date.get();
    }

    public StringProperty dateProperty() {
        return date;
    }
}