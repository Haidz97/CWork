package com.example.cwork;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDateTime;

public class Expense extends Document {
    private final StringProperty productName;
    private final StringProperty expenseQuantity;

    public Expense(Product product, int quantity, LocalDateTime dateTime, String description) {
        super(dateTime, description);
        this.productName = new SimpleStringProperty(product.getName());
        this.expenseQuantity = new SimpleStringProperty(Integer.toString(quantity));
    }

    public String getProductName() {
        return productName.get();
    }

    public StringProperty productNameProperty() {
        return productName;
    }

    public String getExpenseQuantity() {
        return expenseQuantity.get();
    }

    public StringProperty expenseQuantityProperty() {
        return expenseQuantity;
    }
}
