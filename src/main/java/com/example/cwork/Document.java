package com.example.cwork;

public abstract class Document {
    private String documentId;
    private String date;

    public Document(String documentId, String date) {
        this.documentId = documentId;
        this.date = date;
    }

    // Геттеры и сеттеры
    public String getDocumentId() { return documentId; }
    public void setDocumentId(String documentId) { this.documentId = documentId; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public abstract void processDocument();
}