package com.example.cwork;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

public class MainController {

    // Поля для ввода данных
    @FXML
    private TextField manufacturerAddressField;
    @FXML
    private TableView<Product> productTable;
    @FXML
    private TableColumn<Product, String> nameColumn;
    @FXML
    private TableColumn<Product, String> manufacturerColumn;
    @FXML
    private TableColumn<Product, String> dateColumn;
    @FXML
    private TableColumn<Product, Number> quantityColumn;
    @FXML
    private TableColumn<Product, String> addressColumn;

    @FXML
    private TextField productNameField;
    @FXML
    private ComboBox<String> manufacturerComboBox;
    @FXML
    private DatePicker arrivalDatePicker;
    @FXML
    private TextField quantityField;

    @FXML
    private ComboBox<String> saleProductComboBox;
    @FXML
    private TextField saleQuantityField;

    // Списки данных
    private ObservableList<Product> products = FXCollections.observableArrayList();
    private ObservableList<String> saleProducts = FXCollections.observableArrayList();
    private ObservableList<Manufacturer> manufacturers = FXCollections.observableArrayList();
    private ObservableList<Expense> expenses = FXCollections.observableArrayList();

    // Инициализация контроллера
    @FXML
    private void initialize() {
        // Настройка столбцов таблицы
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        manufacturerColumn.setCellValueFactory(cellData -> cellData.getValue().manufacturerProperty());
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
        quantityColumn.setCellValueFactory(cellData -> cellData.getValue().quantityProperty());
        addressColumn.setCellValueFactory(cellData -> cellData.getValue().manufacturerAddressProperty());

        productTable.setItems(products); // Связывание таблицы с списком товаров

        // Инициализация списка производителей
        manufacturers.addAll(
                new Manufacturer("KDV", "Адрес KDV"),
                new Manufacturer("Красный октябрь", "Адрес Красный октябрь"),
                new Manufacturer("Славянка", "Адрес Славянка"),
                new Manufacturer("Ротфронт", "Адрес Ротфронт"),
                new Manufacturer("Черноголовка", "Адрес Черноголовка")
        );

        // Заполнение ComboBox именами производителей
        manufacturerComboBox.setItems(FXCollections.observableArrayList(
                manufacturers.stream().map(Manufacturer::getName).collect(Collectors.toList())
        ));

        // Добавление слушателя изменений в списке товаров для обновления списка товаров для продажи
        products.addListener((ListChangeListener.Change<? extends Product> c) -> {
            updateSaleProductOptions();
        });

        // Ограничиваем ввод только числовыми значениями в поле количества
        quantityField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                Integer.parseInt(newValue);
            } catch (NumberFormatException e) {
                quantityField.setText(oldValue);
            }
        });
    }

    // Обновляет список товаров, доступных для продажи
    private void updateSaleProductOptions() {
        saleProducts.clear();
        for (Product p : products) {
            if (p.getQuantity() > 0) {
                saleProducts.add(p.getName());
            }
        }
        saleProductComboBox.setItems(saleProducts);
    }

    // Обработчик события нажатия кнопки "Добавить товар"
    @FXML
    private void addProduct(ActionEvent event) {
        String productName = productNameField.getText();
        String selectedManufacturerName = manufacturerComboBox.getSelectionModel().getSelectedItem();
        LocalDate arrivalDate = arrivalDatePicker.getValue();
        String quantityStr = quantityField.getText();

        if (!productName.isEmpty() && selectedManufacturerName != null && arrivalDate != null && !quantityStr.isEmpty()) {
            int quantity = Integer.parseInt(quantityStr);

            // Находим объект Manufacturer по имени
            Manufacturer selectedManufacturer = manufacturers.stream()
                    .filter(m -> m.getName().equals(selectedManufacturerName))
                    .findFirst()
                    .orElse(null);

            if (selectedManufacturer != null) {
                String formattedDate = arrivalDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                Product newProduct = new Product(
                        productName,
                        selectedManufacturer,
                        quantity,
                        formattedDate
                );
                products.add(newProduct);
                clearFields();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Производитель не найден!");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Заполните все поля!");
            alert.showAndWait();
        }
    }

    // Обработчик события нажатия кнопки "Продать товар"
    @FXML
    private void sellProduct(ActionEvent event) {
        String selectedProduct = saleProductComboBox.getSelectionModel().getSelectedItem();
        int quantityToSell = Integer.parseInt(saleQuantityField.getText());

        if (selectedProduct != null && quantityToSell > 0) {
            Product productToSell = findProductByName(selectedProduct);
            if (productToSell != null && productToSell.getQuantity() >= quantityToSell) {
                productToSell.setQuantity(productToSell.getQuantity() - quantityToSell);

                // Фиксация расхода
                Expense expense = new Expense(productToSell, quantityToSell, LocalDateTime.now(), "Продажа товара");
                expenses.add(expense);

                clearSaleFields();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Недостаточно товара на складе.");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Заполните все поля!");
            alert.showAndWait();
        }
    }

    // Находит товар по названию в списке товаров
    private Product findProductByName(String name) {
        return products.stream().filter(p -> p.getName().equals(name)).findFirst().orElse(null);
    }

    // Очищает поля для ввода данных о продаже товара
    private void clearSaleFields() {
        saleProductComboBox.getSelectionModel().clearSelection();
        saleQuantityField.clear();
    }

    // Очищает все поля ввода после добавления нового товара
    private void clearFields() {
        productNameField.clear();
        manufacturerComboBox.getSelectionModel().clearSelection();
        arrivalDatePicker.setValue(null);
        quantityField.clear();
    }
}