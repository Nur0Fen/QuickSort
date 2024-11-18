package com.example.sort1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class QuickSortApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException, SQLException {
        DatabaseConnection.createDatabase();
        Parent root = FXMLLoader.load(getClass().getResource("QuickSortApplication-view.fxml"));
        stage.setTitle("Тестовое название апликейшн");
        stage.setScene(new Scene(root, 747, 460));
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}