package com.example.cwork;

public class Receipt extends Document {
    private Product product;
    private int quantity;

    public Receipt(String documentId, String date, Product product, int quantity) {
        super(documentId, date);
        this.product = product;
        this.quantity = quantity;
    }

    @Override
    public void processDocument() {
        product.setQuantity(product.getQuantity() + quantity);
        System.out.println("Поступление товара: " + product.getName() + ", количество: " + quantity);
    }
}