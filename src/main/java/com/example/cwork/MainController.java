package com.example.cwork;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.time.LocalDate;

public class MainController {
    @FXML
    private TableView<Product> productTable;
    @FXML
    private TableColumn<Product, String> nameColumn;
    @FXML
    private TableColumn<Product, Double> priceColumn;
    @FXML
    private TableColumn<Product, Integer> quantityColumn;
    @FXML
    private TableColumn<Product, LocalDate> arrivalDateColumn;

    @FXML
    private TextField nameField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField quantityField;
    @FXML
    private DatePicker arrivalDatePicker;

    private ObservableList<Product> products = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        // Настройка столбцов таблицы
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        quantityColumn.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());
        arrivalDateColumn.setCellValueFactory(cellData -> cellData.getValue().arrivalDateProperty());

        // Загрузка тестовых данных
        products.add(new Product("Товар 1", 100.0, LocalDate.now(), new Manufacturer("Производитель 1", "Адрес 1"), 10));
        products.add(new Product("Товар 2", 200.0, LocalDate.now(), new Manufacturer("Производитель 2", "Адрес 2"), 20));

        productTable.setItems(products);
    }

    @FXML
    private void handleAddProduct() {
        clearFields();
    }

    @FXML
    private void handleEditProduct() {
        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            nameField.setText(selectedProduct.getName());
            priceField.setText(Double.toString(selectedProduct.getPrice()));
            quantityField.setText(Integer.toString(selectedProduct.getQuantity()));
            arrivalDatePicker.setValue(selectedProduct.getArrivalDate());
        } else {
            showAlert("Ошибка", "Товар не выбран", "Пожалуйста, выберите товар для редактирования.");
        }
    }

    @FXML
    private void handleDeleteProduct() {
        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            products.remove(selectedProduct);
        } else {
            showAlert("Ошибка", "Товар не выбран", "Пожалуйста, выберите товар для удаления.");
        }
    }

    @FXML
    private void handleSaveProduct() {
        if (isInputValid()) {
            Product product = new Product(
                    nameField.getText(),
                    Double.parseDouble(priceField.getText()),
                    arrivalDatePicker.getValue(),
                    new Manufacturer("Производитель", "Адрес"),
                    Integer.parseInt(quantityField.getText())
            );
            products.add(product);
            productTable.refresh();
            clearFields();
        }
    }

    @FXML
    private void handleCancel() {
        clearFields();
    }

    private boolean isInputValid() {
        // Простая валидация
        if (nameField.getText() == null || nameField.getText().isEmpty()) {
            showAlert("Ошибка", "Название товара пустое", "Пожалуйста, введите название товара.");
            return false;
        }
        try {
            Double.parseDouble(priceField.getText());
            Integer.parseInt(quantityField.getText());
        } catch (NumberFormatException e) {
            showAlert("Ошибка", "Неверный формат", "Пожалуйста, введите правильные значения.");
            return false;
        }
        return true;
    }

    private void clearFields() {
        nameField.clear();
        priceField.clear();
        quantityField.clear();
        arrivalDatePicker.setValue(null);
    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
    @FXML
    private void handleAddReceipt() {
        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            Receipt receipt = ReceiptDialog.showDialog(selectedProduct);
            if (receipt != null) {
                receipt.processDocument(); // Обрабатываем документ
                productTable.refresh(); // Обновляем таблицу
            }
        } else {
            showAlert("Ошибка", "Товар не выбран", "Пожалуйста, выберите товар для поступления.");
        }
    }

    @FXML
    private void handleAddSale() {
        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            Sale sale = SaleDialog.showDialog(selectedProduct);
            if (sale != null) {
                sale.processDocument(); // Обрабатываем документ
                productTable.refresh(); // Обновляем таблицу
            }
        } else {
            showAlert("Ошибка", "Товар не выбран", "Пожалуйста, выберите товар для продажи.");
        }
    }
}