package com.example.cwork;

//Класс Manufacturer представляет информацию о производителе товара.
public class Manufacturer {
    //Название производителя.
    private String name;


    //Конструктор класса Manufacturer.
    public Manufacturer(String name, String address) {
        this.name = name;
    }

    //Возвращает название производителя.
    public String getName() {
        return name;
    }

    //Устанавливает новое название производителя.
    public void setName(String name) {
        this.name = name;
    }
}