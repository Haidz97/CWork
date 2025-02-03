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


    // Геттер для адреса
    public String getAddress() {
        return address;
    }

    // Переопределяем метод toString для удобного вывода информации
    @Override
    public String toString() {
        return name + " (" + address + ")";
    }
}