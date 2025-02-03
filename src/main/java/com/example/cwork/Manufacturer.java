package com.example.cwork;

//Класс Manufacturer представляет информацию о производителе товара.
public class Manufacturer {
    private String name;
    private String address; // Добавляем поле для адреса

    // Конструктор, принимающий имя и адрес
    public Manufacturer(String name, String address) {
        this.name = name;
        this.address = address;
    }

    // Геттер для имени
    public String getName() {
        return name;
    }

    // Сеттер для имени
    public void setName(String name) {
        this.name = name;
    }

    // Геттер для адреса
    public String getAddress() {
        return address;
    }

    // Сеттер для адреса
    public void setAddress(String address) {
        this.address = address;
    }

    // Переопределяем метод toString для удобного вывода информации
    @Override
    public String toString() {
        return name + " (" + address + ")";
    }
}