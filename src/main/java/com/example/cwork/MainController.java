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


public class MainController {

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

    private ObservableList<Product> products = FXCollections.observableArrayList();
    private ObservableList<String> saleProducts = FXCollections.observableArrayList();
    private ObservableList<String> manufacturers = FXCollections.observableArrayList();

    // Список расходов
    private ObservableList<Expense> expenses = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        manufacturerColumn.setCellValueFactory(cellData -> cellData.getValue().manufacturerProperty());
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
        quantityColumn.setCellValueFactory(cellData -> cellData.getValue().quantityProperty());

        productTable.setItems(products);

        // Инициализация comboBox производителя
        manufacturers.addAll("KDV", "Красный октябрь", "Славянка", "Ротфронт", "Черноголовка");
        manufacturerComboBox.setItems(manufacturers);
        manufacturerComboBox.setItems(FXCollections.observableArrayList(manufacturers));

        // Добавляем прослушиватель для автоматического обновления списка товаров
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

    private void updateSaleProductOptions() {
        saleProducts.clear();
        for (Product p : products) {
            if (p.getQuantity() > 0) {
                saleProducts.add(p.getName());
            }
        }
        saleProductComboBox.setItems(saleProducts);
    }

    @FXML
    private void addProduct(ActionEvent event) {
        String productName = productNameField.getText();
        String selectedManufacturer = manufacturerComboBox.getSelectionModel().getSelectedItem();
        LocalDate arrivalDate = arrivalDatePicker.getValue();
        String quantityStr = quantityField.getText();

        if (!productName.isEmpty() && selectedManufacturer != null && arrivalDate != null && !quantityStr.isEmpty()) {
            int quantity = Integer.parseInt(quantityStr);

            String formattedDate = arrivalDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            Product newProduct = new Product(productName, selectedManufacturer, quantity, formattedDate);
            products.add(newProduct);
            productTable.refresh();
            clearFields();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Заполните все поля!");
            alert.showAndWait();
        }
    }

    @FXML
    private void sellProduct(ActionEvent event) {
        String selectedProduct = saleProductComboBox.getSelectionModel().getSelectedItem();
        int quantityToSell = Integer.parseInt(saleQuantityField.getText());

        if (selectedProduct != null && quantityToSell > 0) {
            Product productToSell = findProductByName(selectedProduct);
            if (productToSell != null && productToSell.getQuantity() >= quantityToSell) {
                productToSell.setQuantity(productToSell.getQuantity() - quantityToSell);
                if (productToSell.getQuantity() == 0) {
                    products.remove(productToSell);
                }

                // Фиксируем расход
                Expense expense = new Expense(productToSell, quantityToSell, LocalDateTime.now(), "Продажа товара");
                expenses.add(expense);

                productTable.refresh();
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

    private Product findProductByName(String name) {
        return products.stream().filter(p -> p.getName().equals(name)).findFirst().orElse(null);
    }

    private void clearSaleFields() {
        saleProductComboBox.getSelectionModel().clearSelection();
        saleQuantityField.clear();
    }

    private void clearFields() {
        productNameField.clear();
        manufacturerComboBox.getSelectionModel().clearSelection();
        arrivalDatePicker.setValue(null);
        quantityField.clear();
    }
}