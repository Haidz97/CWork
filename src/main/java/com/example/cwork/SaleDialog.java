package com.example.cwork;

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

import java.time.LocalDate;

public class SaleDialog {

    public static Sale showDialog(Product product) {
        // Создаем диалоговое окно
        Dialog<Pair<String, Integer>> dialog = new Dialog<>();
        dialog.setTitle("Продажа товара");
        dialog.setHeaderText("Введите данные для продажи товара: " + product.getName());

        // Устанавливаем кнопки (OK и Cancel)
        ButtonType addButtonType = new ButtonType("Продать", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

        // Создаем поля для ввода данных
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField documentIdField = new TextField();
        documentIdField.setPromptText("Номер документа");
        TextField quantityField = new TextField();
        quantityField.setPromptText("Количество");

        grid.add(new Label("Номер документа:"), 0, 0);
        grid.add(documentIdField, 1, 0);
        grid.add(new Label("Количество:"), 0, 1);
        grid.add(quantityField, 1, 1);

        dialog.getDialogPane().setContent(grid);

        // Преобразуем результат в объект Sale
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                return new Pair<>(documentIdField.getText(), Integer.parseInt(quantityField.getText()));
            }
            return null;
        });

        // Показываем диалог и ждем результата
        Pair<String, Integer> result = dialog.showAndWait().orElse(null);
        if (result != null) {
            return new Sale(result.getKey(), LocalDate.now().toString(), product, result.getValue());
        }
        return null;
    }
}