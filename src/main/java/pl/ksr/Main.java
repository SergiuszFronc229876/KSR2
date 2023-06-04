package pl.ksr;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    private Parent root;

    public static void main(String[] args) {
        launch(Main.class, args);
    }

    @Override
    public void init() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/fxml/mainView.fxml"));
        root = fxmlLoader.load();
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Generator podsumowań lingwistycznych");
        Scene scene = new Scene(root, 1400, 750);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
