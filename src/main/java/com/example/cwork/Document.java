package com.example.cwork;

import java.time.LocalDateTime;

//Абстрактный класс Document служит основой для всех документов, связанных с операциями учета товаров в магазине.
public abstract class Document {
    //Дата и время создания документа.
    protected LocalDateTime dateTime;
    //Описание документа.
    protected String description;

    //Конструктор класса Document.
    //dateTime    дата и время создания документа
    //description описание документа
    public Document(LocalDateTime dateTime, String description) {
        this.dateTime = dateTime;
        this.description = description;
    }

    //Возвращает дату и время создания документа.
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    //Устанавливает новую дату и время для документа.
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    //Возвращает описание документа.
    public String getDescription() {
        return description;
    }

    //Устанавливает новое описание для документа.
    public void setDescription(String description) {
        this.description = description;
    }
}