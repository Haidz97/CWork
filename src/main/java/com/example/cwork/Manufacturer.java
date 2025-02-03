package com.example.cwork;

public class Manufacturer {
    private String name;
    private String address;

    public Manufacturer(String name, String address) {
        this.name = name;
        this.address = address;
    }

    // Геттеры и сеттеры
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    @Override
    public String toString() {
        return name + " (" + address + ")";
    }
}