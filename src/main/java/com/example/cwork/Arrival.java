package com.example.cwork;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDateTime;

public class Arrival extends Document {
    private final StringProperty productName;
    private final StringProperty manufacturerName;
    private final StringProperty arrivalQuantity;

    public Arrival(Product product, Manufacturer manufacturer, int quantity, LocalDateTime dateTime, String description) {
        super(dateTime, description);
        this.productName = new SimpleStringProperty(product.getName());
        this.manufacturerName = new SimpleStringProperty(manufacturer.getName());
        this.arrivalQuantity = new SimpleStringProperty(Integer.toString(quantity));
    }

    public String getProductName() {
        return productName.get();
    }

    public StringProperty productNameProperty() {
        return productName;
    }

    public String getManufacturerName() {
        return manufacturerName.get();
    }

    public StringProperty manufacturerNameProperty() {
        return manufacturerName;
    }

    public String getArrivalQuantity() {
        return arrivalQuantity.get();
    }

    public StringProperty arrivalQuantityProperty() {
        return arrivalQuantity;
    }
}