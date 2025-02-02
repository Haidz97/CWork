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


//Контроллер главного окна приложения, управляющий взаимодействием между пользователем и данными.
public class MainController {

    @FXML
    //Таблица для отображения списка товаров.
    private TableView<Product> productTable;
    @FXML
    //Столбец таблицы, содержащий названия товаров.
    private TableColumn<Product, String> nameColumn;
    @FXML
    //Столбец таблицы, содержащий производителей товаров.
    private TableColumn<Product, String> manufacturerColumn;
    @FXML
    //Столбец таблицы, содержащий даты поступления товаров.
    private TableColumn<Product, String> dateColumn;
    @FXML
    //Столбец таблицы, содержащий количество товаров.
    private TableColumn<Product, Number> quantityColumn;

    @FXML
    //Поле для ввода названия нового товара.
    private TextField productNameField;
    @FXML
    //Комбо-бокс для выбора производителя товара.
    private ComboBox<String> manufacturerComboBox;
    @FXML
    //Календарь для выбора даты поступления товара.
    private DatePicker arrivalDatePicker;
    @FXML
    //Поле для ввода количества товара.
    private TextField quantityField;

    @FXML
    //Комбо-бокс для выбора товара для продажи.
    private ComboBox<String> saleProductComboBox;
    @FXML
    //Поле для ввода количества товара для продажи.
    private TextField saleQuantityField;

    //Список всех товаров в магазине.
    private ObservableList<Product> products = FXCollections.observableArrayList();
    //Список товаров, доступных для продажи.
    private ObservableList<String> saleProducts = FXCollections.observableArrayList();
    //Список возможных производителей товаров.
    private ObservableList<String> manufacturers = FXCollections.observableArrayList();
    //Список расходов (продаж) товаров.
    private ObservableList<Expense> expenses = FXCollections.observableArrayList();

    //Метод, вызываемый автоматически после загрузки FXML файла. Настраивает таблицу и заполняет комбо-боксы.
    @FXML
    private void initialize() {
        // Настройка столбцов таблицы
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        manufacturerColumn.setCellValueFactory(cellData -> cellData.getValue().manufacturerProperty());
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
        quantityColumn.setCellValueFactory(cellData -> cellData.getValue().quantityProperty());

        productTable.setItems(products);    // Связывание таблицы с списком товаров

        // Инициализация списка производителей
        manufacturers.addAll("KDV", "Красный октябрь", "Славянка", "Ротфронт", "Черноголовка");
        manufacturerComboBox.setItems(manufacturers);
        manufacturerComboBox.setItems(FXCollections.observableArrayList(manufacturers));

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

    //Обновляет список товаров, доступных для продажи, основываясь на текущих остатках
    private void updateSaleProductOptions() {
        saleProducts.clear();
        for (Product p : products) {
            if (p.getQuantity() > 0) {
                saleProducts.add(p.getName());
            }
        }
        saleProductComboBox.setItems(saleProducts);
    }

    //Обработчик события нажатия кнопки "Добавить товар". Проверяет заполненность полей и добавляет новый товар в список.
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
        } else {               //Ошибка, если не все поля заполнены при поступлении
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Заполните все поля!");
            alert.showAndWait();
        }
    }

    //Обработчик события нажатия кнопки "Продать товар". Проверяет выбранный товар и количество, затем уменьшает остаток товара.
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

                // Фиксация расхода
                Expense expense = new Expense(productToSell, quantityToSell, LocalDateTime.now(), "Продажа товара");
                expenses.add(expense);

                productTable.refresh();
                clearSaleFields();
            } else {      //Выдаёт ошибку, если продать больше имеющегося товара
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Недостаточно товара на складе.");
                alert.showAndWait();
            }
        } else {    //Ошибка, если не все поля заполнены при продаже
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Заполните все поля!");
            alert.showAndWait();
        }
    }

    //Находит товар по названию в списке товаров.
    private Product findProductByName(String name) {
        return products.stream().filter(p -> p.getName().equals(name)).findFirst().orElse(null);
    }

    //Очищает поля для ввода данных о продаже товара.
    private void clearSaleFields() {
        saleProductComboBox.getSelectionModel().clearSelection();
        saleQuantityField.clear();
    }

    //Очищает все поля ввода после добавления нового товара.
    private void clearFields() {
        productNameField.clear();
        manufacturerComboBox.getSelectionModel().clearSelection();
        arrivalDatePicker.setValue(null);
        quantityField.clear();
    }
}