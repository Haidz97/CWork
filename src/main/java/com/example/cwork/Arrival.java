package com.example.cwork;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDateTime;

//Класс Arrival представляет собой запись о поступлении товара в магазин.

public class Arrival extends Document {
    //Название продукта, который поступил.
    private final StringProperty productName;
    //Название производителя продукта.
    private final StringProperty manufacturerName;
    //Количество единиц товара, которое поступило.
    private final StringProperty arrivalQuantity;

    //Конструктор класса Arrival.
    public Arrival(Product product, Manufacturer manufacturer, int quantity, LocalDateTime dateTime, String description) {
        // Вызываем конструктор родительского класса Document
        super(dateTime, description);
        // Устанавливаем значения свойств
        this.productName = new SimpleStringProperty(product.getName());
        this.manufacturerName = new SimpleStringProperty(manufacturer.getName());
        this.arrivalQuantity = new SimpleStringProperty(Integer.toString(quantity));
    }

    //Название продукта.
    public String getProductName() {
        return productName.get();
    }

    //Название продукта.
    public StringProperty productNameProperty() {
        return productName;
    }

    //Название производителя
    public String getManufacturerName() {
        return manufacturerName.get();
    }

    //Свойство названия производителя
    public StringProperty manufacturerNameProperty() {
        return manufacturerName;
    }

    //свойство названия производителя
    public String getArrivalQuantity() {
        return arrivalQuantity.get();
    }

    public StringProperty arrivalQuantityProperty() {
        return arrivalQuantity;
    }
}