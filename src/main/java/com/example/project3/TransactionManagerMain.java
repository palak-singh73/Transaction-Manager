package com.example.project3;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Transaction Manager Main that launches and runs the TransactionManager application
 * @author Palak Singh, Daniel Guan
 */

public class TransactionManagerMain extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TransactionManagerMain.class.getResource("TransactionManagerView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 725, 550);
        stage.setTitle("Transaction Manager");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}