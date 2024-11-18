package com.example.sort1;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuickSortController<bool> {


    @FXML
    private Text massive_text;
    @FXML
    private Button newArray;
    @FXML
    private Button Close;
    @FXML
    private Button sortMassive;

    @FXML
    private Button editButton;
    @FXML
    private Text sortedArrayText;
    @FXML
    private TextField editArray;
    @FXML
    private TextField dimensionField;
    @FXML
    private Button deleteArray;

    @FXML
    private Button cleanDb;

    @FXML
    private Button decrementButton;
    @FXML
    private Button incrementButton;

//    // Объявляем переменную connection
//    private Connection connection;

    private DatabaseOperations dbOps = new DatabaseOperations();


    @FXML
    private TableView<Array> tableView;

    @FXML
    private TableColumn<Array, Integer> idColumn;
    @FXML
    private TableColumn<Array, String> arrayColumn;


    private int[] currentArray = null;
    private ArrayList<String> currentArray2 = new ArrayList<>();
    private int dimension;
    boolean dbExist = false;

    //Сохранение в БД при нажании на кнопку Сохранить
    @FXML
    private void handleSaveButton() throws SQLException {
        // Получение данных из текстовых полей
        String array = editArray.getText();
        if (dbExist == true) {
            int dbId =  tableView.getSelectionModel().getSelectedItem().getId();
            dbOps.updateArray(dbId,array);
            DatabaseConnection.closeConnection();
            dbExist = false;
        }
        dbOps.insertData(array);
        DatabaseConnection.closeConnection();
        loadData(); // Обновление таблицы
        cleanDb.setDisable(false);
    }


//    // Метод для закрытия соединения
//    public void closeConnection() {
//        try {
//            if (connection != null && !connection.isClosed()) {
//                connection.close();
//                System.out.println("Connection closed");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

    private void updateTextField() {
        String result = String.join(", ", currentArray2);
        massive_text.setText(result);
        editArray.setText(result);
    }

    public void saveChanges() {
        String[] input = editArray.getText().split(",");
        currentArray2.clear();
        for (String s : input) {
            currentArray2.add(s.trim()); // Добавляем строки без пробелов
        }
        System.out.println("Сохраненные строки: " + currentArray2);
    }

    private boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void loadData() throws SQLException {
        List<Array> arrays = dbOps.getAllArrays();
        DatabaseConnection.closeConnection();
        tableView.getItems().setAll(arrays);
        // Инициализация столбцов таблицы
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        arrayColumn.setCellValueFactory(new PropertyValueFactory<>("array"));

        idColumn.setSortType(TableColumn.SortType.ASCENDING);
        tableView.getSortOrder().add(idColumn);
        tableView.sort();
    }

    @FXML
    public void initialize() throws SQLException {

        loadData();
        dimensionField.setText("5");
        // Отслеживание нажатия на кнопку "Закрыть программу"
        Close.setOnAction(e -> {
            Stage stage = (Stage) Close.getScene().getWindow();
            stage.close();
            System.out.println("Applcation Closed!");
            DatabaseConnection.closeConnection();
        });

        // Отслеживание нажатия на кнопку Декремент размерности массива "-"
        decrementButton.setOnAction(e -> {
            dimension = Integer.parseInt(dimensionField.getText());
            dimension--;
            dimensionField.setText(String.valueOf(dimension));
        });

        // Отслеживание нажатия на кнопку Инкремент размерности массива "+"
        incrementButton.setOnAction(e -> {
            dimension = Integer.parseInt(dimensionField.getText());
            dimension++;
            dimensionField.setText(String.valueOf(dimension));
        });

        // Отслеживание нажатия на кнопку "Создать массив"
        newArray.setOnAction(e -> {
        dimension = Integer.parseInt(dimensionField.getText());
        sortMassive.setDisable(false);
        System.out.println(currentArray2);
        currentArray2 =  GenerateArray.massive(dimension);
        updateTextField();
        });

        editButton.setDisable(true); // Кнопка редактирования изначально неактивна
        deleteArray.setDisable(true);// Кнопка удаления изначально неактивна
        // Обработчик выбора строки
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            editButton.setDisable(newSelection == null);
            deleteArray.setDisable(newSelection == null);
        });

        //Обработчик нажатия на кнопку редактировать
        editButton.setOnAction(e -> {
            sortMassive.setDisable(false);
            cleanDb.setDisable(true);
            deleteArray.setDisable(true);
            currentArray2.clear();
            String[] input = tableView.getSelectionModel().getSelectedItem().getArray().split(",");
            for (String s : input) {
                currentArray2.add(s.trim()); // Добавляем строки без пробелов
            }
            System.out.println(currentArray2);
            editArray.setText(String.join(", ", currentArray2));
            dbExist = true;
                });

        //Обработчик нажатия на кнопку удалить
        deleteArray.setOnAction(e -> {
           int dbId = tableView.getSelectionModel().getSelectedItem().getId();
            try {
                dbOps.deleteArray(dbId);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            DatabaseConnection.closeConnection();
            try {
                loadData();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });

        //Обработчик нажатия на кнопку очистить БД
        cleanDb.setOnAction(e -> {
            dbOps.truncArrayDb();
            DatabaseConnection.closeConnection();
            try {
                loadData();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });

        // Отслеживание нажатия на кнопку "Отсортировать массив"
        sortMassive.setOnAction(e -> {
            saveChanges();
            if (currentArray2 != null) {
                 ArrayList<String> numericStrings = new ArrayList<>();
            ArrayList<String> nonNumericStrings = new ArrayList<>();
            System.out.println(currentArray2);

            // Разделение строк на числовые и нечисловые
            for (String str : currentArray2) {
                if (isNumeric(str)) {
                    numericStrings.add(str);
                } else {
                    nonNumericStrings.add(str);
                }
            }
            System.out.println(numericStrings);

            // Сортировка числовых строк
            QuickSortArray.quickSortArr(numericStrings, 0, numericStrings.size() - 1);

            // Объединение отсортированных числовых строк и нечисловых строк
            numericStrings.addAll(nonNumericStrings);

            // Обновление TextField editArray
            sortedArrayText.setText(String.join(", ", numericStrings));
            editArray.setText(String.join(", ", numericStrings));
            }


        });

    }
}