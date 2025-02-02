package com.example.cwork;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDateTime;

public class Sale extends Document {
    private final StringProperty productName;
    private final StringProperty saleQuantity;

    public Sale(Product product, int quantity, LocalDateTime dateTime, String description) {
        super(dateTime, description);
        this.productName = new SimpleStringProperty(product.getName());
        this.saleQuantity = new SimpleStringProperty(Integer.toString(quantity));
    }

    public String getProductName() {
        return productName.get();
    }

    public StringProperty productNameProperty() {
        return productName;
    }

    public String getSaleQuantity() {
        return saleQuantity.get();
    }

    public StringProperty saleQuantityProperty() {
        return saleQuantity;
    }
}