package com.example.cwork;

import javafx.beans.property.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;

import java.time.LocalDate;

public class Product {
    private final StringProperty name;
    private final DoubleProperty price;
    private final IntegerProperty quantity;
    private final ObjectProperty<LocalDate> arrivalDate;
    private Manufacturer manufacturer;

    public Product(String name, double price, LocalDate arrivalDate, Manufacturer manufacturer, int quantity) {
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleDoubleProperty(price);
        this.arrivalDate = new SimpleObjectProperty<>(arrivalDate);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.manufacturer = manufacturer;
    }

    // Геттеры и сеттеры для всех полей
    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public double getPrice() {
        return price.get();
    }

    public void setPrice(double price) {
        this.price.set(price);
    }

    public DoubleProperty priceProperty() {
        return price;
    }

    public int getQuantity() {
        return quantity.get();
    }

    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
    }

    public IntegerProperty quantityProperty() {
        return quantity;
    }

    public LocalDate getArrivalDate() {
        return arrivalDate.get();
    }

    public void setArrivalDate(LocalDate arrivalDate) {
        this.arrivalDate.set(arrivalDate);
    }

    public ObjectProperty<LocalDate> arrivalDateProperty() {
        return arrivalDate;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    @Override
    public String toString() {
        return name.get() + " (" + manufacturer.getName() + ") - " + quantity.get() + " шт., " + price.get() + " руб. | Дата поступления: " + arrivalDate.get();
    }
}