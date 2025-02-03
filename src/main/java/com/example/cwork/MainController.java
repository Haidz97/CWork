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
        manufacturerColumn.setCellValueFactory(cellData -> cellData.getValue().manufacturerProperty()); // Название производителя
        addressColumn.setCellValueFactory(cellData -> cellData.getValue().manufacturerAddressProperty()); // Адрес производителя
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
        quantityColumn.setCellValueFactory(cellData -> cellData.getValue().quantityProperty());

        productTable.setItems(products); // Связывание таблицы с списком товаров

        // Инициализация списка производителей
        manufacturers.addAll(
                new Manufacturer("KDV", "Ул. Пушкина 10"),
                new Manufacturer("Красный октябрь", "Ул. Карла маркса 1"),
                new Manufacturer("Славянка", "Ул. Ленина 66"),
                new Manufacturer("Ротфронт", "Ул Молодёжная 15"),
                new Manufacturer("Черноголовка", "Ул. Бущенко 9")
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
            if (!newValue.matches("\\d*")) { // Разрешаем только цифры
                quantityField.setText(oldValue);
            } else if (!newValue.isEmpty() && Integer.parseInt(newValue) <= 0) { // Проверка на отрицательные значения
                quantityField.setText(oldValue);
            }
        });

        // Аналогично для поля продажи
        saleQuantityField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) { // Разрешаем только цифры
                saleQuantityField.setText(oldValue);
            } else if (!newValue.isEmpty() && Integer.parseInt(newValue) <= 0) { // Проверка на отрицательные значения
                saleQuantityField.setText(oldValue);
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

            // Проверка на отрицательные значения
            if (quantity <= 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Количество товара должно быть положительным числом!");
                alert.showAndWait();
                return; // Прерываем выполнение метода
            }

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
        String quantityStr = saleQuantityField.getText();

        if (selectedProduct != null && !quantityStr.isEmpty()) {
            int quantityToSell = Integer.parseInt(quantityStr);

            // Проверка на отрицательные значения
            if (quantityToSell <= 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Количество товара для продажи должно быть положительным числом!");
                alert.showAndWait();
                return; // Прерываем выполнение метода
            }

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