package com.example.cwork;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


//Главный класс приложения, отвечающий за инициализацию и запуск графического интерфейса.
public class MainApp extends Application {

    @Override
    //Метод, вызываемый при старте приложения. Загружает FXML файл и отображает главное окно.
    public void start(Stage primaryStage) throws Exception {
        Parent root =  FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        primaryStage.setTitle("Учёт товаров");
        primaryStage.setScene(new Scene(root, 1300, 450));
        primaryStage.show();
    }

    //Запускает приложение
    public static void main(String[] args) {
        launch(args);
    }
}