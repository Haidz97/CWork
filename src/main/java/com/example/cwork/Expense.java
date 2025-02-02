package com.example.cwork;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDateTime;

//Класс Expense представляет собой запись о расходе товара из магазина.
public class Expense extends Document {
    //Название продукта, который был продан или списан.
    private final StringProperty productName;
    //Количество единиц товара, которое было потрачено.
    private final StringProperty expenseQuantity;

    //Конструктор класса Expense.
    //product       продукт, который был потрачен
    //quantity      количество единиц товара
    //dateTime      дата и время расхода
    //description   описание расхода
    public Expense(Product product, int quantity, LocalDateTime dateTime, String description) {
        super(dateTime, description); // Вызываем конструктор родительского класса Document
        // Устанавливаем значения свойств
        this.productName = new SimpleStringProperty(product.getName());
        this.expenseQuantity = new SimpleStringProperty(Integer.toString(quantity));
    }

    //Возвращает название продукта.
    public String getProductName() {
        return productName.get();
    }

    //Возвращает свойство названия продукта.
    public StringProperty productNameProperty() {
        return productName;
    }

    //Возвращает количество единиц товара, которое было потрачено.
    public String getExpenseQuantity() {
        return expenseQuantity.get();
    }

    //Возвращает свойство количества единиц товара.
    public StringProperty expenseQuantityProperty() {
        return expenseQuantity;
    }
}
