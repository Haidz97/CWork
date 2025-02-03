package com.example.cwork;

public class Sale extends Document {
    private Product product;
    private int quantity;

    public Sale(String documentId, String date, Product product, int quantity) {
        super(documentId, date);
        this.product = product;
        this.quantity = quantity;
    }

    @Override
    public void processDocument() {
        if (product.getQuantity() >= quantity) {
            product.setQuantity(product.getQuantity() - quantity);
            System.out.println("Реализация товара: " + product.getName() + ", количество: " + quantity);
        } else {
            System.out.println("Недостаточно товара на складе!");
        }
    }
}